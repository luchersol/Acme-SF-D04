
package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.views.SelectChoices;
import acme.components.AbstractAntiSpamService;
import acme.entities.audits.AuditType;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditUpdateService extends AbstractAntiSpamService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;
		Auditor auditor;

		masterId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findCodeAuditById(masterId);
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
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit ca = this.repository.findCodeAuditWithCode(object.getCode());
			Boolean repeatedCode = ca == null || object.getId() == ca.getId();
			super.state(repeatedCode, "code", "auditor.codeAudit.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("execution")) {
			Date maximumDate = this.repository.findMaximumValidExecutionDate(object.getId());
			Boolean validExecution = maximumDate == null || MomentHelper.isAfter(maximumDate, object.getExecution());
			super.state(validExecution, "execution", "auditor.codeAudit.form.error.badExecution");
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
		Mark mark = this.repository.findCodeAuditMark(object.getId());
		object.setMark(mark);

		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;
		SelectChoices types;
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
