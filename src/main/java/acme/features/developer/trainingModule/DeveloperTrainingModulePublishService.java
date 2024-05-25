/*
 * AdministratorDashboardShowService.java
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

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.views.SelectChoices;
import acme.components.AbstractAntiSpamService;
import acme.entities.project.Project;
import acme.entities.training.DifficultyLevel;
import acme.entities.training.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractAntiSpamService<Developer, TrainingModule> {

	@Autowired
	private DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int moduleId;
		TrainingModule module;
		Developer developer;

		moduleId = super.getRequest().getData("id", int.class);
		module = this.repository.findOneTrainingById(moduleId);
		developer = module == null ? null : module.getDeveloper();
		status = module != null && module.getDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingById(id);
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setUpdateMoment(moment);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		super.state(!this.repository.findManyTrainingSessionsByMasterId(object.getId()).isEmpty(), "code", "developer.training-module.form.error.publish");
		boolean allSessionsInDraftMode = this.repository.areAllTrainingSessionsPublished(object.getId());
		super.state(allSessionsInDraftMode, "code", "developer.training-module.form.error.allpublish");

		super.validateSpam(object);
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setUpdateMoment(moment);
		object.setDraftMode(false);
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
		dataset.put("project", object.getProject());
		dataset.put("projects", choicesProject);
		dataset.put("difficultyLevel", choicesDifficulty.getSelected().getKey());
		dataset.put("difficultyLevels", choicesDifficulty);

		super.getResponse().addData(dataset);
	}

}
