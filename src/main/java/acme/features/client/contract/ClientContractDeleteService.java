
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLogs;
import acme.entities.project.Project;
import acme.roles.Client;

@Service
public class ClientContractDeleteService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;
		Client client;

		masterId = this.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractById(masterId);
		client = contract == null ? null : contract.getClient();
		status = contract != null && contract.getDraftMode() && //
			super.getRequest().getPrincipal().hasRole(client);

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
	public void bind(final Contract contract) {
		assert contract != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("id", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(contract, "code", "instantiationMoment", "providerName", "customerName", "goal", "budget");
		contract.setProject(project);
	}

	@Override
	public void validate(final Contract contract) {
		assert contract != null;
	}

	@Override
	public void perform(final Contract contract) {
		assert contract != null;

		Collection<ProgressLogs> progressLogs;

		progressLogs = this.repository.findManyProgressLogsId(contract.getId());
		this.repository.deleteAll(progressLogs);
		this.repository.delete(contract);

	}

	@Override
	public void unbind(final Contract contract) {
		assert contract != null;

		Collection<Project> projectAllPublish;
		SelectChoices choices;
		Dataset dataset;

		projectAllPublish = this.repository.findAllProjectsPublish();

		choices = SelectChoices.from(projectAllPublish, "title", contract.getProject());

		dataset = super.unbind(contract, "code", "instantiationMoment", "providerName", "customerName", "goal", "budget", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getBuffer().addData(dataset);

	}
}
