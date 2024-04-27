
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.project.ProjectUserStory;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryCreateService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	ManagerProjectUserStoryRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ProjectUserStory object;

		object = new ProjectUserStory();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUserStory object) {

		super.bind(object, "project", "userStory");
	}

	@Override
	public void validate(final ProjectUserStory object) {
		Boolean state;

		if (!super.getBuffer().getErrors().hasErrors("*"))
			if (object.getProject() != null && object.getUserStory() != null) {
				state = this.repository.findRelationByProjectIdAndUserStoryId(object.getProject().getId(), object.getUserStory().getId()).isEmpty();
				super.state(state, "*", "manager.relation.form.error.exist-relation");
			}
	}

	@Override
	public void perform(final ProjectUserStory object) {

		this.repository.save(object);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		Dataset dataset;
		Collection<Project> projects;
		Collection<UserStory> userStories;
		SelectChoices projectChoices, userStoryChoices;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		projects = this.repository.findNotPublishedProjectsByManagerId(managerId);
		userStories = this.repository.findUserStoriesByManagerId(managerId);

		projectChoices = SelectChoices.from(projects, "code", object.getProject());
		userStoryChoices = SelectChoices.from(userStories, "title", object.getUserStory());

		dataset = super.unbind(object, "project", "userStory");
		dataset.put("projects", projectChoices);
		dataset.put("userStories", userStoryChoices);

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
