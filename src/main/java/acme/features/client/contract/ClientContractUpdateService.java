
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.views.SelectChoices;
import acme.components.AbstractAntiSpamService;
import acme.components.MoneyExchangeService;
import acme.entities.contract.Contract;
import acme.entities.project.Project;
import acme.roles.Client;

@Service
public class ClientContractUpdateService extends AbstractAntiSpamService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository	repository;

	@Autowired
	private MoneyExchangeService		moneyExchange;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Contract contract;
		Client client;

		id = super.getRequest().getData("id", int.class);

		contract = this.repository.findOneContractById(id);
		client = contract == null ? null : contract.getClient();
		status = contract != null && contract.getDraftMode() && super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Client client;
		Contract contract;
		int clientId;

		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		client = this.repository.findOneClientById(clientId);

		contract = new Contract();
		contract.setDraftMode(true);
		contract.setClient(client);

		super.getBuffer().addData(contract);
	}

	@Override
	public void bind(final Contract contract) {
		assert contract != null;

		super.bind(contract, "code", "instantiationMoment", "project", "providerName", "customerName", "goal", "budget");
	}

	@Override
	public void validate(final Contract contract) {
		assert contract != null;

		boolean state;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.repository.findOneContractByCode(contract.getCode());
			super.state(existing == null || existing.getId() == contract.getId(), "code", "client.contract.form.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			state = contract.getBudget().getAmount() >= 0;
			super.state(state, "budget", "client.contract.form.error.budget");
		}

		super.validateSpam(contract);
	}

	@Override
	public void perform(final Contract contract) {
		assert contract != null;

		this.repository.save(contract);
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

		dataset = super.unbind(contract, "code", "instantiationMoment", "providerName", "customerName", "goal", "budget");
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);
		moneyExchange = this.moneyExchange.computeMoneyExchange(contract.getBudget());
		dataset.put("moneyExchange", moneyExchange);

		super.getResponse().addData(dataset);
	}

}
