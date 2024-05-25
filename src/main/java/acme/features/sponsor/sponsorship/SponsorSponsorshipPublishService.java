
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.MoneyExchangeService;
import acme.entities.project.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.sponsorship.TypeOfSponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	@Autowired
	private MoneyExchangeService			moneyExchange;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsor sponsor;
		Sponsorship sponsorship;

		masterId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "startDate", "endDate", "email", "link", "type", "amount");
		object.setProject(project);
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		super.state(this.repository.allInvoicesPublishedBySponsorshipId(object.getId()), "*", "sponsor.sponsorship.form.error.publish-invoices");
		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(this.repository.existsOtherByCodeAndId(object.getCode(), object.getId()), "code", "sponsor.sponsorship.form.error.duplicated");

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(MomentHelper.isBefore(object.getStartDate(), object.getMoment()), "startDate", "sponsor.sponsorship.form.error.too-close-moment");

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			Date maximumDeadline;

			super.state(MomentHelper.isBefore(object.getEndDate(), object.getMoment()), "endDate", "sponsor.sponsorship.form.error.too-close-moment");

			maximumDeadline = object.getStartDate() == null ? null : MomentHelper.deltaFromMoment(object.getStartDate(), 1, ChronoUnit.MONTHS);
			super.state(object.getStartDate() == null || MomentHelper.isAfter(object.getEndDate(), maximumDeadline), "endDate", "sponsor.sponsorship.form.error.too-close-start");
		}

	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		Double invoicesAmounts;
		Money finalMoney;
		String systemCurrency;

		invoicesAmounts = this.repository.findManyInvoicesBySponsorshipId(object.getId()).stream() //
			.mapToDouble(i -> i.totalAmount().getAmount() / this.repository.findMoneyRateByMoneyCurrency(i.totalAmount().getCurrency())) //
			.sum();

		systemCurrency = this.repository.findSystemConfiguration().getSystemCurrency();

		finalMoney = new Money();
		finalMoney.setAmount(Math.round(invoicesAmounts * this.repository.findMoneyRateByMoneyCurrency(systemCurrency) * 100.0) / 100.0);
		finalMoney.setCurrency(this.repository.findSystemConfiguration().getSystemCurrency());

		object.setAmount(finalMoney);
		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		SelectChoices choicesType;
		Dataset dataset;
		Money moneyExchange;

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());
		choicesType = SelectChoices.from(TypeOfSponsorship.class, object.getType());

		dataset = super.unbind(object, "code", "startDate", "endDate", "email", "link", "type", "draftMode", "amount");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("type", choicesType.getSelected().getKey());
		dataset.put("types", choicesType);

		moneyExchange = this.moneyExchange.computeMoneyExchange(object.getAmount());
		dataset.put("moneyExchange", moneyExchange);

		super.getResponse().addData(dataset);
	}

}
