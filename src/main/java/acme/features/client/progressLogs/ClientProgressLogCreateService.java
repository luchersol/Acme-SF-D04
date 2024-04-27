
package acme.features.client.progressLogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogCreateService extends AbstractService<Client, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;

		masterId = super.getRequest().getData("masterId", int.class);
		contract = this.repository.findOneContractById(masterId);
		status = contract != null && contract.getDraftMode() && super.getRequest().getPrincipal().hasRole(contract.getClient());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLog progressLog;
		int masterId;
		Contract contract;

		masterId = super.getRequest().getData("masterId", int.class);
		contract = this.repository.findOneContractById(masterId);

		progressLog = new ProgressLog();
		progressLog.setRecordId("");
		progressLog.setCompleteness(0.00);
		progressLog.setComment("");
		progressLog.setRegistrationMoment(null);
		progressLog.setResponsiblePerson("");
		progressLog.setContract(contract);
		progressLog.setDraftMode(true);
		super.getBuffer().addData(progressLog);
	}

	@Override
	public void bind(final ProgressLog progressLog) {
		assert progressLog != null;

		super.bind(progressLog, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");
	}

	@Override
	public void validate(final ProgressLog progressLog) {
		assert progressLog != null;

		boolean state;

		if (!super.getBuffer().getErrors().hasErrors("recordId")) {
			ProgressLog existing;

			existing = this.repository.findOneProgressLogByRecordId(progressLog.getRecordId());
			super.state(existing == null || existing.getId() == progressLog.getId(), "recordId", "client.progress-log.form.error.code");
		}
	}

	@Override
	public void perform(final ProgressLog progressLog) {
		assert progressLog != null;

		this.repository.save(progressLog);
	}

	@Override
	public void unbind(final ProgressLog progressLog) {
		assert progressLog != null;

		Dataset dataset;

		dataset = super.unbind(progressLog, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson", "draftMode");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);
	}

}
