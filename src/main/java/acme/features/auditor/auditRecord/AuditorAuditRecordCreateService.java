
package acme.features.auditor.auditRecord;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.views.SelectChoices;
import acme.components.AbstractAntiSpamService;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractAntiSpamService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;
		masterId = super.getRequest().getData("masterId", int.class);
		codeAudit = this.repository.findCodeAuditById(masterId);
		status = codeAudit != null && codeAudit.getDraftMode() && super.getRequest().getPrincipal().hasRole(codeAudit.getAuditor());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int masterId;
		CodeAudit codeAudit;
		masterId = super.getRequest().getData("masterId", int.class);
		codeAudit = this.repository.findCodeAuditById(masterId);
		AuditRecord object;
		object = new AuditRecord();
		object.setCodeAudit(codeAudit);
		object.setDraftMode(true);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;
		int masterId;
		CodeAudit codeAudit;
		masterId = super.getRequest().getData("masterId", int.class);
		codeAudit = this.repository.findCodeAuditById(masterId);
		super.bind(object, "code", "startDate", "endDate", "link", "mark");
		object.setCodeAudit(codeAudit);
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord ar = this.repository.findAuditRecordByCode(object.getCode());
			super.state(ar == null, "code", "auditor.auditRecord.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			boolean notNull = object.getCodeAudit().getExecution() != null;
			Boolean timeConcordance = notNull && MomentHelper.isAfter(object.getStartDate(), object.getCodeAudit().getExecution());
			super.state(timeConcordance, "startDate", "auditor.auditRecord.form.error.badStartDate");
		}
		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			boolean notNull = object.getStartDate() != null;
			Boolean timeConcordance = notNull && MomentHelper.isAfter(object.getEndDate(), object.getStartDate());
			super.state(timeConcordance, "endDate", "auditor.auditRecord.form.error.badTimeConcordance");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			boolean notNull = object.getStartDate() != null;
			Boolean goodDuration = notNull && MomentHelper.isLongEnough(object.getEndDate(), object.getStartDate(), 1, ChronoUnit.HOURS);
			super.state(goodDuration, "endDate", "auditor.auditRecord.form.error.notEnoughDuration");
		}

		if (!super.getBuffer().getErrors().hasErrors("codeAudit")) {
			CodeAudit ca = object.getCodeAudit();
			Auditor a = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
			boolean codeAuditIsYours = ca.getAuditor().getId() == a.getId();
			super.state(codeAuditIsYours && ca.getDraftMode(), "codeAudit", "auditor.auditRecord.form.error.codeAudit");
		}
		super.validateSpam(object);

	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {

		assert object != null;

		SelectChoices marks;

		marks = SelectChoices.from(Mark.class, object.getMark());

		Dataset dataset;

		dataset = super.unbind(object, "code", "draftMode", "startDate", "endDate", "link", "mark");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		dataset.put("codeAudit", object.getCodeAudit());

		dataset.put("mark", marks.getSelected().getKey());
		dataset.put("marks", marks);

		super.getResponse().addData(dataset);

	}

}
