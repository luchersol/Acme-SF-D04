
package acme.features.sponsor.dashboard;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.form.SponsorForm;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorForm> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		SponsorForm dashboard = new SponsorForm();
		int id;

		id = this.getRequest().getPrincipal().getActiveRoleId();

		Function<Collection<Object[]>, List<Money>> transformToListMoney = collect -> collect.stream().map(obj -> {
			Money money = new Money();
			money.setCurrency((String) obj[0]);
			money.setAmount((Double) obj[1]);
			return money;
		}).collect(Collectors.toList());

		Integer totalInvoicesWithTaxLessThanOrEqualTo21 = this.repository.totalInvoicesWithTaxLessThanOrEqualTo21(id);
		Integer totalSponsorshipWithLink = this.repository.totalSponsorshipWithLink(id);

		List<Money> averageAmountOfTheSponsorships = transformToListMoney.apply(this.repository.averageAmountOfTheSponsorships(id));
		List<Money> deviationAmountOfTheSponsorships = transformToListMoney.apply(this.repository.deviationAmountOfTheSponsorships(id));
		List<Money> minimumAmountOfTheSponsorships = transformToListMoney.apply(this.repository.minimumAmountOfTheSponsorships(id));
		List<Money> maximumAmountOfTheSponsorships = transformToListMoney.apply(this.repository.maximumAmountOfTheSponsorships(id));

		List<Money> averageQuantityOfTheInvoices = transformToListMoney.apply(this.repository.averageQuantityOfTheInvoices(id));
		List<Money> deviationQuantityOfTheInvoices = transformToListMoney.apply(this.repository.deviationQuantityOfTheInvoices(id));
		List<Money> minimumQuantityOfTheInvoices = transformToListMoney.apply(this.repository.minimumQuantityOfTheInvoices(id));
		List<Money> maximumQuantityOfTheInvoices = transformToListMoney.apply(this.repository.maximumQuantityOfTheInvoices(id));

		dashboard.setTotalInvoicesWithTaxLessThanOrEqualTo21(totalInvoicesWithTaxLessThanOrEqualTo21);
		dashboard.setTotalSponsorshipWithLink(totalSponsorshipWithLink);

		dashboard.setAverageAmountOfTheSponsorships(averageAmountOfTheSponsorships);
		dashboard.setDeviationAmountOfTheSponsorships(deviationAmountOfTheSponsorships);
		dashboard.setMinimumAmountOfTheSponsorships(minimumAmountOfTheSponsorships);
		dashboard.setMaximumAmountOfTheSponsorships(maximumAmountOfTheSponsorships);

		dashboard.setAverageQuantityOfTheInvoices(averageQuantityOfTheInvoices);
		dashboard.setDeviationQuantityOfTheInvoices(deviationQuantityOfTheInvoices);
		dashboard.setMinimumQuantityOfTheInvoices(minimumQuantityOfTheInvoices);
		dashboard.setMaximumQuantityOfTheInvoices(maximumQuantityOfTheInvoices);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorForm object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalInvoicesWithTaxLessThanOrEqualTo21", "totalSponsorshipWithLink", "averageAmountOfTheSponsorships", "deviationAmountOfTheSponsorships", "minimumAmountOfTheSponsorships", "maximumAmountOfTheSponsorships",
			"averageQuantityOfTheInvoices", "deviationQuantityOfTheInvoices", "minimumQuantityOfTheInvoices", "maximumQuantityOfTheInvoices");

		super.getResponse().addData(dataset);
	}
}
