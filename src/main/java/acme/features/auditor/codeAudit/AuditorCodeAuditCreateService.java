
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.views.SelectChoices;
import acme.components.AbstractAntiSpamService;
import acme.entities.audits.AuditType;
import acme.entities.audits.CodeAudit;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditCreateService extends AbstractAntiSpamService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CodeAudit object;
		Auditor auditor;
		auditor = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new CodeAudit();
		object.setDraftMode(true);
		object.setAuditor(auditor);
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
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit ca = this.repository.findCodeAuditWithCode(object.getCode());
			super.state(ca == null, "code", "auditor.codeAudit.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("project")) {
			Boolean isDraftMode = this.repository.projectIsDraftMode(object.getProject().getId());
			super.state(isDraftMode != null && !isDraftMode, "project", "auditor.codeAudit.form.error.notPublishedProject");
		}

		super.validateSpam(object);
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		SelectChoices types;
		Dataset dataset;
		projects = this.repository.findPublishedProjects();

		choices = SelectChoices.from(projects, "code", object.getProject());
		types = SelectChoices.from(AuditType.class, object.getType());

		dataset = super.unbind(object, "code", "execution", "correctiveActions", "link", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("type", types.getSelected().getKey());
		dataset.put("types", types);
		super.getResponse().addData(dataset);
	}

}
