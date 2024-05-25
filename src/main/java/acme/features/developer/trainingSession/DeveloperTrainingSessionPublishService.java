
package acme.features.developer.trainingSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
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
