
package acme.features.client.contract;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.project.Project;
import acme.roles.Client;

@Service
public class ClientContractCreateService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Contract contract;
		Client client;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		client = this.repository.findOneClientById(super.getRequest().getPrincipal().getActiveRoleId());

		contract = new Contract();
		contract.setInstantiationMoment(moment);
		contract.setDraftMode(true);
		contract.setClient(client);

		super.getBuffer().addData(contract);

	}

	@Override
	public void bind(final Contract contract) {
		assert contract != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(contract, "code", "instantiationMoment", "project", "providerName", "customerName", "goal", "budget");
		contract.setProject(project);
	}

	@Override
	public void validate(final Contract contract) {
		assert contract != null;

		boolean state;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.repository.findOneContractByCode(contract.getCode());
			super.state(existing == null, "code", "client.contract.form.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			state = contract.getBudget().getAmount() >= 0;
			super.state(state, "budget", "client.contract.form.error.budget");
		}

	}

	@Override
	public void perform(final Contract contract) {
		assert contract != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		contract.setInstantiationMoment(moment);

		this.repository.save(contract);
	}

	@Override
	public void unbind(final Contract contract) {
		assert contract != null;

		Collection<Project> projectAllPublish;
		SelectChoices choices;
		Dataset dataset;

		projectAllPublish = this.repository.findAllProjectsPublish();

		choices = SelectChoices.from(projectAllPublish, "title", contract.getProject());

		dataset = super.unbind(contract, "code", "instantiationMoment", "project", "providerName", "customerName", "goal", "budget", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);

	}

}
