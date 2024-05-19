
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.components.AbstractAntiSpamService;
import acme.entities.training.TrainingModule;
import acme.entities.training.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionPublishService extends AbstractAntiSpamService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int trainingSessionId;
		TrainingModule trainingModule;
		Developer developer;

		trainingSessionId = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleByTrainingSessionId(trainingSessionId);
		developer = trainingModule == null ? null : trainingModule.getDeveloper();
		status = trainingModule != null && trainingModule.getDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingSessionById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "timeStart", "timeEnd", "location", "instructor", "contactEmail", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;
		Long moment = object.getTrainingModule().getCreationMoment().getTime();
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean state = !this.repository.existsOtherByCodeAndId(object.getCode(), object.getId());
			super.state(state, "code", "developer.training-session.form.error.duplicated");
		}
		// Validate time period
		if (!super.getBuffer().getErrors().hasErrors("timeStart")) {
			long amount = 7;
			ChronoUnit unit = ChronoUnit.DAYS;
			long offset = amount * unit.getDuration().toMillis();
			Date oneWeekAhead = new Date(moment + offset);
			boolean isOneWeekAhead = MomentHelper.isAfterOrEqual(object.getTimeStart(), oneWeekAhead);
			super.state(isOneWeekAhead, "timeStart", "developer.training-session.form.error.notOneWeekAhead");
		}
		if (!super.getBuffer().getErrors().hasErrors("timeStart") && !super.getBuffer().getErrors().hasErrors("timeEnd")) {
			long amount = 7;
			ChronoUnit unit = ChronoUnit.DAYS;
			long offset = amount * unit.getDuration().toMillis();
			Date oneWeekPeriod = new Date(object.getTimeStart().getTime() + offset);
			boolean isOneWeekLong = MomentHelper.isAfterOrEqual(object.getTimeEnd(), oneWeekPeriod);
			super.state(isOneWeekLong, "timeEnd", "developer.training-session.form.error.notOneWeekLong");
		}
		super.validateSpam(object);
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "timeStart", "timeEnd", "location", "instructor", "contactEmail", "link");
		dataset.put("masterId", object.getTrainingModule().getId());
		dataset.put("draftMode", object.getTrainingModule().getDraftMode());

		super.getResponse().addData(dataset);
	}

}
