
package acme.features.developer.developerForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.form.DeveloperForm;
import acme.roles.Developer;

@Service
public class DeveloperDeveloperFormShowService extends AbstractService<Developer, DeveloperForm> {

	@Autowired
	private DeveloperDeveloperFormRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		DeveloperForm developerForm = new DeveloperForm();
		int developerId;
		developerId = this.getRequest().getPrincipal().getAccountId();
		developerForm.setTotalTrainingModulesWithUpdateMoment(this.repository.totalNumberOfTrainingModulesWithUpdateMoment(developerId));
		developerForm.setTotalTrainingSessionsWithLink(this.repository.totalNumberOfTrainingSessionsWithLink(developerId));
		developerForm.setAverageTimeOfTraining(this.repository.averageTimeOfTrainingModules(developerId));
		developerForm.setDeviationTimeOfTraining(this.repository.standardDeviationTimeOfTrainingModules(developerId));
		developerForm.setMinimumTimeOfTraining(this.repository.minTimeOfTrainingModules(developerId));
		developerForm.setMaximumTimeOfTraining(this.repository.maxTimeOfTrainingModules(developerId));

		super.getBuffer().addData(developerForm);
	}

	@Override
	public void unbind(final DeveloperForm object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalTrainingModulesWithUpdateMoment", "totalTrainingSessionsWithLink", // 
			"averageTimeOfTraining", "deviationTimeOfTraining", //
			"minimumTimeOfTraining", "maximumTimeOfTraining");

		super.getResponse().addData(dataset);
	}

}
