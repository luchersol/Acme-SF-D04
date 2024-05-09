
package acme.features.client.contract;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.project.Project;
import acme.features.authenticated.moneyExchange.MoneyExchangeService;
import acme.roles.Client;

@Service
public class ClientContractShowService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository	repository;

	@Autowired
	private MoneyExchangeService		moneyExchange;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;
		Client client;
		Date currentMoment;

		masterId = super.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractById(masterId);
		client = contract == null ? null : contract.getClient();
		currentMoment = MomentHelper.getCurrentMoment();
		status = super.getRequest().getPrincipal().hasRole(client) && contract != null && MomentHelper.isAfter(contract.getInstantiationMoment(), currentMoment);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract contract;
		int id;

		id = super.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractById(id);

		super.getBuffer().addData(contract);
	}

	@Override
	public void unbind(final Contract contract) {
		assert contract != null;

		Collection<Project> projectAllPublish;
		SelectChoices choicesProject;
		Dataset dataset;
		Money moneyExchange;

		projectAllPublish = this.repository.findAllProjectsPublish();

		choicesProject = SelectChoices.from(projectAllPublish, "title", contract.getProject());

		dataset = super.unbind(contract, "code", "instantiationMoment", "providerName", "customerName", "goal", "budget", "draftMode");
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);
		moneyExchange = this.moneyExchange.computeMoneyExchange(contract.getBudget());
		dataset.put("moneyExchange", moneyExchange);

		super.getResponse().addData(dataset);
	}
}
