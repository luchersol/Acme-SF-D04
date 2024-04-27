
package acme.features.administrator.risk;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.risk.Risk;

@Service
public class AdministratorRiskCreateService extends AbstractService<Administrator, Risk> {

	@Autowired
	private AdministratorRiskRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Risk object;

		Administrator administrator;
		administrator = this.repository.findOneAdministratorById(super.getRequest().getPrincipal().getActiveRoleId());

		object = new Risk();
		object.setAdministrator(administrator);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Risk object) {
		assert object != null;

		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setIdentificationDate(moment);
		super.bind(object, "reference", "impact", "probability", "description", "link");
	}

	@Override
	public void validate(final Risk object) {
		assert object != null;

		// Validate reference
		if (!super.getBuffer().getErrors().hasErrors("reference")) {
			Risk existing;
			existing = this.repository.findOneRiskByReference(object.getReference());
			super.state(existing == null, "reference", "administrator.risk.form.error.duplicated");
		}
	}

	@Override
	public void perform(final Risk object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "reference", "identificationDate", "impact", "probability", "description", "link");

		super.getResponse().addData(dataset);
	}

}
