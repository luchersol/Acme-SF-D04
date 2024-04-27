
package acme.features.client.clientForm;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.form.ClientForm;
import acme.roles.Client;

@Service
public class ClientClientFormShowService extends AbstractService<Client, ClientForm> {

	@Autowired
	private ClientClientFormRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ClientForm clientForm = new ClientForm();
		int clientId;

		clientId = this.getRequest().getPrincipal().getAccountId();

		Function<Collection<Object[]>, List<Money>> transformToListMoney = collect -> collect.stream().map(obj -> {
			Money money = new Money();
			money.setCurrency((String) obj[0]);
			money.setAmount((Double) obj[1]);
			return money;
		}).toList();

		Integer countCompletenessBelow25 = this.repository.countCompletenessBelow25(clientId);
		Integer countCompletenessBetween25And50 = this.repository.countCompletenessBetween25And50(clientId);
		Integer countCompletenessBetween50And75 = this.repository.countCompletenessBetween50And75(clientId);
		Integer countCompletenessAbove75 = this.repository.countCompletenessAbove75(clientId);
		List<Money> averageBudgetOfContracts = transformToListMoney.apply(this.repository.averageBudgetOfContracts(clientId));
		List<Money> deviationEstimatedCostProjects = transformToListMoney.apply(this.repository.deviationBudgetOfContracts(clientId));
		List<Money> minimumEstimatedCostProjects = transformToListMoney.apply(this.repository.minimumBudgetOfContracts(clientId));
		List<Money> maximumEstimatedCostProjects = transformToListMoney.apply(this.repository.maximumBudgetOfContracts(clientId));

		clientForm.setCompletenessBelow25(countCompletenessBelow25);
		clientForm.setCompletenessBetween25and50(countCompletenessBetween25And50);
		clientForm.setCompletenessBetween50and75(countCompletenessBetween50And75);
		clientForm.setCompletenessAbove75(countCompletenessAbove75);
		clientForm.setAverageBudgetOfContracts(averageBudgetOfContracts);
		clientForm.setDeviationBudgetOfContracts(deviationEstimatedCostProjects);
		clientForm.setMinimumBudgetOfContracts(minimumEstimatedCostProjects);
		clientForm.setMaximumBudgetOfContracts(maximumEstimatedCostProjects);

		super.getBuffer().addData(clientForm);

	}

	@Override
	public void unbind(final ClientForm clientForm) {
		Dataset dataset;

		dataset = super.unbind(clientForm, //
			"completenessBelow25", "completenessBetween25and50", // 
			"completenessBetween50and75", "completenessAbove75", //
			"averageBudgetOfContracts", "deviationBudgetOfContracts",//
			"minimumBudgetOfContracts", "maximumBudgetOfContracts");

		super.getResponse().addData(dataset);
	}

}
