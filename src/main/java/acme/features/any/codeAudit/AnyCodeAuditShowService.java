
package acme.features.any.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.AuditType;
import acme.entities.audits.CodeAudit;
import acme.entities.project.Project;

@Service
public class AnyCodeAuditShowService extends AbstractService<Any, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;
		masterId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findCodeAuditById(masterId);
		status = codeAudit != null && codeAudit.getDraftMode() == false;
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
		dataset = super.unbind(object, "code", "execution", "correctiveActions", "link", "mark");

		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("types", types);
		dataset.put("type", types.getSelected().getKey());

		super.getResponse().addData(dataset);
	}

}
