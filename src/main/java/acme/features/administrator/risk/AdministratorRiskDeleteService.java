
package acme.features.administrator.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risk.Risk;

@Service
public class AdministratorRiskDeleteService extends AbstractService<Administrator, Risk> {

	@Autowired
	private AdministratorRiskRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int riskId;
		Risk risk;
		Administrator administrator;

		riskId = super.getRequest().getData("id", int.class);
		risk = this.repository.findOneRiskById(riskId);
		administrator = risk == null ? null : risk.getAdministrator();
		status = risk != null && super.getRequest().getPrincipal().hasRole(administrator);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Risk object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneRiskById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Risk object) {
		assert object != null;

		super.bind(object, "reference", "identificationDate", "impact", "probability", "description", "link");
	}

	@Override
	public void validate(final Risk object) {
		assert object != null;
	}

	@Override
	public void perform(final Risk object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "reference", "identificationDate", "impact", "probability", "description", "link");

		super.getResponse().addData(dataset);
	}

}
