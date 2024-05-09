
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.MoneyExchangeService;
import acme.entities.project.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.sponsorship.TypeOfSponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	@Autowired
	private MoneyExchangeService			moneyExchange;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Sponsor sponsor;
		Sponsorship sponsorship;

		id = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(id);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(sponsor);

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
	public void unbind(final Sponsorship object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;
		SelectChoices choicesType;
		Money moneyExchange;

		projects = this.repository.findAllProjects();

		choices = SelectChoices.from(projects, "code", object.getProject());
		choicesType = SelectChoices.from(TypeOfSponsorship.class, object.getType());

		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "email", "link", "draftMode", "type", "amount");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("type", choicesType.getSelected().getKey());
		dataset.put("types", choicesType);

		moneyExchange = this.moneyExchange.computeMoneyExchange(object.getAmount());
		dataset.put("moneyExchange", moneyExchange);

		super.getResponse().addData(dataset);
	}
}
