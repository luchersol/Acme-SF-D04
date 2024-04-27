
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.AuditType;
import acme.entities.audits.CodeAudit;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;
		Auditor auditor;
		masterId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findCodeAuditById(masterId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = super.getRequest().getPrincipal().hasRole(auditor) && codeAudit != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCodeAuditById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		SelectChoices types;

		types = SelectChoices.from(AuditType.class, object.getType());

		Dataset dataset;
		projects = this.repository.findPublishedProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "execution", "correctiveActions", "link", "draftMode", "mark");

		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		dataset.put("type", types.getSelected().getKey());
		dataset.put("types", types);

		super.getResponse().addData(dataset);
	}

}
