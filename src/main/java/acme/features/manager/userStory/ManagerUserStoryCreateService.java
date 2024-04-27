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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.PriorityUserStory;
import acme.entities.project.Project;
import acme.entities.project.ProjectUserStory;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryCreateService extends AbstractService<Manager, UserStory> {

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
		Manager manager;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		manager = this.repository.findManagerById(managerId);

		object = new UserStory();
		object.setDraftMode(true);
		object.setManager(manager);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "link", "priority");
	}

	@Override
	public void validate(final UserStory object) {

	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		ProjectUserStory relation;
		Project project;
		UserStory userStory;
		int projectId;

		projectId = this.getRequest().getData("masterId", int.class);
		project = this.repository.findOneProjectById(projectId);
		userStory = this.repository.save(object);

		relation = new ProjectUserStory();
		relation.setProject(project);
		relation.setUserStory(userStory);
		this.repository.save(relation);

	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choices;
		int masterId;
		masterId = this.getRequest().getData("masterId", int.class);

		choices = SelectChoices.from(PriorityUserStory.class, object.getPriority());
		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "link", "priority");
		dataset.put("priorities", choices);
		dataset.put("masterId", masterId);

		super.getResponse().addData(dataset);
	}
}
