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

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Project object;
		Manager manager;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		manager = this.repository.findManagerById(managerId);

		object = new Project();
		object.setCode("");
		object.setTitle("");
		object.setAbstractProject("");
		object.setIndication(false);
		object.setCost(null);
		object.setLink("");
		object.setDraftMode(true);
		object.setManager(manager);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractProject", "indication", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		boolean state;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			state = !this.repository.existsByCode(object.getCode());
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

		dataset = super.unbind(object, "code", "title", "abstractProject", "indication", "cost", "link");

		super.getResponse().addData(dataset);

	}

}
