
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.MoneyExchangeService;
import acme.entities.sponsorship.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceShowService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository	repository;

	@Autowired
	private MoneyExchangeService		moneyExchange;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int incoiceId;
		Invoice invoice;
		Sponsor sponsor;

		incoiceId = super.getRequest().getData("id", int.class);

		invoice = this.repository.findOneInvoiceById(incoiceId);
		sponsor = invoice == null ? null : invoice.getSponsor();
		status = invoice != null && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;
		Money moneyExchange;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode");
		moneyExchange = this.moneyExchange.computeMoneyExchange(object.getQuantity());
		dataset.put("moneyExchange", moneyExchange);

		super.getResponse().addData(dataset);
	}
}
