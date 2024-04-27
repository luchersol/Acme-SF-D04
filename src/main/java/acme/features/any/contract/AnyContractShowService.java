
package acme.features.any.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.project.Project;

@Service
public class AnyContractShowService extends AbstractService<Any, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;

		masterId = super.getRequest().getData("id", int.class);
		contract = this.repository.findContractById(masterId);
		status = contract != null && contract.getDraftMode() == false;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract contract;
		int id;

		id = super.getRequest().getData("id", int.class);
		contract = this.repository.findContractById(id);

		super.getBuffer().addData(contract);
	}

	@Override
	public void unbind(final Contract contract) {
		assert contract != null;
		assert contract.getProject() != null;
		
		Dataset dataset;

		dataset = super.unbind(contract, "code", "instantiationMoment", "providerName", "customerName", "goal", "budget", "draftMode");
		dataset.put("project", contract.getProject().getTitle());

		super.getResponse().addData(dataset);
	}
}
