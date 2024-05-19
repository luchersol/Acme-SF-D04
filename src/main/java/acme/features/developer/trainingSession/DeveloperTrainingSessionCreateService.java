/*
 * EmployerDutyCreateService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

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
public class DeveloperTrainingSessionCreateService extends AbstractAntiSpamService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule trainingModule;

		masterId = super.getRequest().getData("masterId", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);
		status = trainingModule != null && trainingModule.getDraftMode() && super.getRequest().getPrincipal().hasRole(trainingModule.getDeveloper());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;
		int masterId;
		TrainingModule trainingModule;

		masterId = super.getRequest().getData("masterId", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);

		object = new TrainingSession();
		object.setCode("");
		object.setTimeStart(null);
		object.setTimeEnd(null);
		object.setLocation("");
		object.setInstructor("");
		object.setContactEmail("");
		object.setLink("");
		object.setTrainingModule(trainingModule);
		object.setDraftMode(true);
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
		// Validate code
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;
			existing = this.repository.findOneTrainingSessionByCode(object.getCode());
			super.state(existing == null, "code", "developer.training-session.form.error.duplicated");
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

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "timeStart", "timeEnd", "location", "instructor", "contactEmail", "link");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));
		dataset.put("draftMode", object.getTrainingModule().getDraftMode());

		super.getResponse().addData(dataset);
	}

}
