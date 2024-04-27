
package acme.features.any.trainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.training.TrainingModule;

@Service
public class AnyTrainingModuleListService extends AbstractService<Any, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<TrainingModule> objects;
		objects = this.repository.findManyTrainingModulesByAvailability();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "code", "creationMoment", "details");
		payload = String.format(//
			"%s; %s; %s; %s", //
			object.getDifficultyLevel(), //
			object.getLink(), //
			object.getEstimatedTotalTime(), //
			object.getDeveloper().getIdentity().getFullName());
		dataset.put("payload", payload);

		super.getResponse().addData(dataset);
	}

}
