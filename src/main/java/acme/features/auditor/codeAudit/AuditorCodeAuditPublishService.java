
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.AuditType;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int codeAuditId;
		CodeAudit codeAudit;
		Auditor auditor;
		codeAuditId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findCodeAuditById(codeAuditId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = codeAudit != null && codeAudit.getDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);
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
	public void bind(final CodeAudit object) {
		assert object != null;

		int projectId;
		Project project;
		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOnePublishedProjectById(projectId);
		super.bind(object, "code", "execution", "type", "correctiveActions", "mark", "link");
		object.setProject(project);

	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
		CodeAudit ca = this.repository.findCodeAuditWithCode(object.getCode());

		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(ca.getId() == object.getId(), "code", "auditor.codeAudit.form.error.duplicated");

		if (!super.getBuffer().getErrors().hasErrors("mark")) {
			Mark mark = this.repository.findCodeAuditMark(object.getId()).get(0);
			Boolean isPosibleMark = mark != null && mark.toString() != "F_MINUS" && mark.toString() != "F";
			super.state(isPosibleMark, "mark", "auditor.codeAudit.form.error.invalidMarkForPublish");
			object.setMark(mark);
		}

	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		object.setDraftMode(false);
		Collection<AuditRecord> ars = this.repository.findNonPublishedAuditRecordsOfCodeAudit(object.getId());
		Mark mark = this.repository.findCodeAuditMark(object.getId()).get(0);
		object.setMark(mark);
		this.repository.deleteAll(ars);
		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		SelectChoices types;
		types = SelectChoices.from(AuditType.class, object.getType());

		projects = this.repository.findPublishedProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "execution", "mark", "correctiveActions", "link", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("type", types.getSelected().getKey());
		dataset.put("types", types);

		super.getResponse().addData(dataset);
	}

}
