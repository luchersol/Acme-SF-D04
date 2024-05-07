
package acme.features.authenticated.risk;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risk.Risk;

@Service
public class AuthenticatedRiskListService extends AbstractService<Authenticated, Risk> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedRiskRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Risk> objects;
		objects = this.repository.findAllRisks();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "reference", "identificationDate", "description");
		payload = String.format(//
			"%s; %s; %s", //
			object.getImpact(), //
			object.getProbability(), //
			object.getLink());
		dataset.put("payload", payload);

		super.getResponse().addData(dataset);
	}

}
