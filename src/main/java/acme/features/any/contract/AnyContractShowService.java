
package acme.features.any.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.MoneyExchangeService;
import acme.entities.contract.Contract;

@Service
public class AnyContractShowService extends AbstractService<Any, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyContractRepository	repository;

	@Autowired
	private MoneyExchangeService	moneyExchange;

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
		Money moneyExchange;

		dataset = super.unbind(contract, "code", "instantiationMoment", "providerName", "customerName", "goal", "budget", "draftMode");
		dataset.put("project", contract.getProject().getTitle());
		moneyExchange = this.moneyExchange.computeMoneyExchange(contract.getBudget());
		dataset.put("moneyExchange", moneyExchange);

		super.getResponse().addData(dataset);
	}
}
