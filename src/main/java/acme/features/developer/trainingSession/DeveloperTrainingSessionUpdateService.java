/*
 * EmployerDutyUpdateService.java
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.components.AbstractAntiSpamService;
import acme.entities.training.TrainingModule;
import acme.entities.training.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionUpdateService extends AbstractAntiSpamService<Developer, TrainingSession> {

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
		// Validate code
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Boolean state = !this.repository.existsOtherByCodeAndId(object.getCode(), object.getId());
			super.state(state, "code", "developer.training-session.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("timeStart")) {
			Date minDate = new GregorianCalendar(2000, Calendar.JANUARY, 1, 0, 0, 0).getTime();
			Date maxDate = new GregorianCalendar(2200, Calendar.DECEMBER, 31, 23, 59, 59).getTime();
			Date timeStart = object.getTimeStart();

			super.state(MomentHelper.isAfterOrEqual(timeStart, minDate), "timeStart", "developer.training-session.form.error.timeStart.min");
			super.state(MomentHelper.isBeforeOrEqual(timeStart, maxDate), "timeStart", "developer.training-session.form.error.timeStart.max");

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

		if (!super.getBuffer().getErrors().hasErrors("timeEnd")) {
			// Validate timeEnd
			Date minDate = new GregorianCalendar(2000, Calendar.JANUARY, 1, 0, 0, 0).getTime();
			Date maxDate = new GregorianCalendar(2200, Calendar.DECEMBER, 31, 23, 59, 59).getTime();
			Date timeEnd = object.getTimeEnd();
			super.state(MomentHelper.isAfterOrEqual(timeEnd, minDate), "timeEnd", "developer.training-session.form.error.timeEnd.min");
			super.state(MomentHelper.isBeforeOrEqual(timeEnd, maxDate), "timeEnd", "developer.training-session.form.error.timeEnd.max");

		}
		if (!super.getBuffer().getErrors().hasErrors("link") && !object.getLink().isEmpty()) {
			// Validate link length
			int linkLength = object.getLink().length();
			super.state(linkLength >= 7 && linkLength <= 255, "link", "developer.training-session.form.error.link.size");
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
		dataset.put("masterId", object.getTrainingModule().getId());
		dataset.put("draftMode", object.getTrainingModule().getDraftMode());

		super.getResponse().addData(dataset);
	}

}
