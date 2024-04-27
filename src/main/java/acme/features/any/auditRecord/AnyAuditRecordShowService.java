
package acme.features.any.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;

@Service
public class AnyAuditRecordShowService extends AbstractService<Any, AuditRecord> {

	@Autowired
	private AnyAuditRecordRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		AuditRecord auditRecord;
		masterId = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findAuditRecordById(masterId);
		status = auditRecord != null && auditRecord.getDraftMode() == false;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditRecordById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final AuditRecord object) {

		assert object != null;

		Collection<CodeAudit> codeAudits;
		SelectChoices choices;
		SelectChoices marks;

		marks = SelectChoices.from(Mark.class, object.getMark());

		Dataset dataset;
		codeAudits = this.repository.findCodeAudits();
		choices = SelectChoices.from(codeAudits, "code", object.getCodeAudit());
		dataset = super.unbind(object, "code", "draftMode", "startDate", "endDate", "link", "mark");

		dataset.put("codeAudit", choices.getSelected().getKey());
		dataset.put("codeAudits", choices);

		dataset.put("mark", marks.getSelected().getKey());
		dataset.put("marks", marks);

		super.getResponse().addData(dataset);

	}

}
