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

package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.project.ProjectUserStory;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryRelationService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = this.getRequest().getData("id", int.class);
		object = this.repository.findOneUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {

	}

	@Override
	public void validate(final UserStory object) {

	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;
		int projectId;
		Project project;

		projectId = this.getRequest().getData("project", Project.class).getId();
		project = this.repository.findOneProjectById(projectId);

		ProjectUserStory relation = new ProjectUserStory();
		relation.setProject(project);
		relation.setUserStory(object);

		this.repository.save(relation);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		Collection<Project> projects;
		SelectChoices choices;
		int managerId;

		managerId = this.getRequest().getPrincipal().getActiveRoleId();
		projects = this.repository.findAllProjectsByManagerId(managerId);

		choices = SelectChoices.from(projects, "code", null);
		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "link", "priority", "draftMode");
		dataset.put("projects", choices);
		dataset.put("project", null);

		super.getResponse().addData(dataset);
	}
}
