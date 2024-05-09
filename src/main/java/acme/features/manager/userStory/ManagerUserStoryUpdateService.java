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
import acme.client.views.SelectChoices;
import acme.components.AbstractAntiSpamService;
import acme.entities.project.PriorityUserStory;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryUpdateService extends AbstractAntiSpamService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		UserStory userStory;
		Manager manager;

		id = super.getRequest().getData("id", int.class);

		userStory = this.repository.findOneUserStoryById(id);
		manager = userStory == null ? null : userStory.getManager();
		status = userStory != null && userStory.getDraftMode() && this.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		UserStory userStory;

		id = super.getRequest().getData("id", int.class);
		userStory = this.repository.findOneUserStoryById(id);

		super.getBuffer().addData(userStory);
	}

	@Override
	public void bind(final UserStory object) {

		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "link", "priority");

	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
		super.validateSpam(object);
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choices;

		choices = SelectChoices.from(PriorityUserStory.class, object.getPriority());
		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "link", "priority", "draftMode");
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);
	}

}
