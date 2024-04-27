
package acme.features.any.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audits.CodeAudit;

@Service
public class AnyCodeAuditListAllService extends AbstractService<Any, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;
		objects = this.repository.findPublishedCodeAudit();
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "execution", "type", "mark");
		super.getResponse().addData(dataset);
	}

}
