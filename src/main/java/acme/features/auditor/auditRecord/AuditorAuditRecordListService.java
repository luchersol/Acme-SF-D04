
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;

		masterId = super.getRequest().getData("masterId", int.class);
		codeAudit = this.repository.findCodeAuditById(masterId);
		status = codeAudit != null && super.getRequest().getPrincipal().hasRole(codeAudit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditRecord> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findAuditRecordsOfCodeAudit(masterId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "startDate", "endDate", "mark", "draftMode");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));
		String published = !object.getDraftMode() ? "✓" : "x";
		dataset.put("published", published);
		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<AuditRecord> objects) {
		assert objects != null;
		Boolean showCreate;
		CodeAudit codeAudit;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		codeAudit = this.repository.findCodeAuditById(masterId);
		showCreate = codeAudit.getDraftMode() && super.getRequest().getPrincipal().getActiveRoleId() == codeAudit.getAuditor().getId();
		masterId = super.getRequest().getData("masterId", int.class);

		super.getResponse().addGlobal("masterId", masterId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
