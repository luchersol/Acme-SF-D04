/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.project;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.features.authenticated.moneyExchange.MoneyExchangeService;
import acme.roles.Manager;

@Service
public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository	repository;

	@Autowired
	private MoneyExchangeService		moneyExchange;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Project object;
		Manager manager;

		id = super.getRequest().getData("id", int.class);

		object = this.repository.findOneProjectById(id);
		manager = object == null ? null : object.getManager();
		status = object != null && object.getDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Manager manager;
		Project project;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		manager = this.repository.findManagerById(managerId);

		project = new Project();
		project.setDraftMode(true);
		project.setManager(manager);

		super.getBuffer().addData(project);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractProject", "indication", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		boolean state;//PRO-0009 213

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			state = !this.repository.existsOtherByCodeAndId(object.getCode(), object.getId());
			super.state(state, "code", "manager.project.form.error.duplicated-code");
		}
		if (!super.getBuffer().getErrors().hasErrors("cost")) {
			state = object.getCost().getAmount() >= 0;
			super.state(state, "cost", "manager.project.form.error.negative-cost");
		}
		if (!super.getBuffer().getErrors().hasErrors("cost")) {
			state = Arrays.asList(this.repository.findAcceptedCurrencies().split(",")).contains(object.getCost().getCurrency());
			super.state(state, "cost", "manager.project.form.error.invalid-currency");
		}
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		Dataset dataset;
		Money moneyExchange;

		dataset = super.unbind(object, "code", "title", "abstractProject", "indication", "cost", "link", "draftMode");
		moneyExchange = this.moneyExchange.computeMoneyExchange(object.getCost());
		dataset.put("moneyExchange", moneyExchange);

		super.getResponse().addData(dataset);
	}

}
