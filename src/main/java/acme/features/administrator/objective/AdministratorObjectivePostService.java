
package acme.features.administrator.objective;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.objective.Objective;
import acme.entities.objective.PriorityObjective;

@Service
public class AdministratorObjectivePostService extends AbstractService<Administrator, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Objective objective;
		Date moment;
		Administrator administrator;

		moment = MomentHelper.getCurrentMoment();

		administrator = this.repository.findOneAdministratorById(super.getRequest().getPrincipal().getActiveRoleId());
		objective = new Objective();
		objective.setInstantiationMoment(moment);
		objective.setAdministrator(administrator);

		super.getBuffer().addData(objective);
	}

	@Override
	public void bind(final Objective objective) {
		assert objective != null;

		super.bind(objective, "title", "description", "priority", "status", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final Objective objective) {
		assert objective != null;

		boolean state;

		if (!super.getBuffer().getErrors().hasErrors("startDate") && objective.getStartDate() != null) {
			state = MomentHelper.isAfter(objective.getStartDate(), objective.getInstantiationMoment());

			super.state(state, "startDate", "administrator.objective.form.error.startDate");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate") && objective.getStartDate() != null && objective.getEndDate() != null) {
			state = MomentHelper.isAfter(objective.getEndDate(), objective.getStartDate());

			super.state(state, "endDate", "administrator.objective.form.error.endDate");
		}

		if (!super.getBuffer().getErrors().hasErrors("confirmation")) {
			state = super.getRequest().getData("confirmation", boolean.class);

			super.state(state, "confirmation", "javax.validation.constraints.AssertTrue.message");
		}

	}

	@Override
	public void perform(final Objective objective) {
		assert objective != null;

		this.repository.save(objective);
	}

	@Override
	public void unbind(final Objective objective) {
		assert objective != null;

		SelectChoices choicesPriority;
		Dataset dataset;

		choicesPriority = SelectChoices.from(PriorityObjective.class, objective.getPriority());
		dataset = super.unbind(objective, "instantiationMoment", "title", "description", "priority", "status", "startDate", "endDate", "link");
		dataset.put("confirmation", false);
		dataset.put("priority", choicesPriority.getSelected().getKey());
		dataset.put("prioritys", choicesPriority);

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
