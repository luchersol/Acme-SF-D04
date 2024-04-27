
package acme.features.any.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.ProgressLog;

@Service
public class AnyProgressLogShowService extends AbstractService<Any, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProgressLogRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		ProgressLog progressLog;

		masterId = super.getRequest().getData("id", int.class);
		progressLog = this.repository.findProgressLogById(masterId);
		status = progressLog != null && !progressLog.isDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLog progressLog;
		int id;

		id = super.getRequest().getData("id", int.class);
		progressLog = this.repository.findProgressLogById(id);

		super.getBuffer().addData(progressLog);
	}

	@Override
	public void unbind(final ProgressLog progressLogs) {
		assert progressLogs != null;

		Dataset dataset;

		dataset = super.unbind(progressLogs, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson", "draftMode");
		dataset.put("masterId", progressLogs.getId());

		super.getResponse().addData(dataset);
	}

}
