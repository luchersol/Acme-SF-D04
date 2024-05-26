/*
 * DeveloperTrainingModuleCreateService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.developer.trainingModule;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.Assert;
import acme.client.helpers.MomentHelper;
import acme.client.views.SelectChoices;
import acme.components.AbstractAntiSpamService;
import acme.entities.project.Project;
import acme.entities.training.DifficultyLevel;
import acme.entities.training.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleCreateService extends AbstractAntiSpamService<Developer, TrainingModule> {

	@Autowired
	private DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		TrainingModule object;
		Developer developer;

		developer = this.repository.findOneDeveloperById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new TrainingModule();
		object.setDraftMode(true);
		object.setDeveloper(developer);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId;
		Project project;
		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		Date moment;
		moment = MomentHelper.getCurrentMoment();

		// Restar un segundo para que sea menor que el momento  de actualizar
		Calendar cal = Calendar.getInstance();
		cal.setTime(moment);
		cal.add(Calendar.SECOND, -1);
		moment = cal.getTime();

		object.setCreationMoment(moment);
		object.setUpdateMoment(null);
		super.bind(object, "code", "details", "difficultyLevel", "link", "estimatedTotalTime");
		object.setProject(project);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		Project project;
		// Validate code
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;
			existing = this.repository.findOneTrainingByCode(object.getCode());
			super.state(existing == null, "code", "developer.training-module.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("project")) {
			Boolean isDraftMode = this.repository.ProjectIsDraftMode(object.getProject().getId());
			Assert.state(!isDraftMode, "Access is not authorised");
		}
		if (!super.getBuffer().getErrors().hasErrors("link") && !object.getLink().isEmpty()) {
			// Validate link length
			int linkLength = object.getLink().length();
			super.state(linkLength >= 7 && linkLength <= 255, "link", "developer.training-module.form.error.link.size");
		}
		super.validateSpam(object);

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choicesProject;
		SelectChoices choicesDifficulty;
		Dataset dataset;

		projects = this.repository.findAllProjectPublish();
		choicesDifficulty = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "estimatedTotalTime", "draftMode");
		choicesProject = SelectChoices.from(projects, "code", object.getProject());
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);
		dataset.put("difficultyLevel", choicesDifficulty.getSelected().getKey());
		dataset.put("difficultyLevels", choicesDifficulty);

		super.getResponse().addData(dataset);
	}

}
