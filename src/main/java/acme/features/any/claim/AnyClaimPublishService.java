
package acme.features.any.claim;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.components.AbstractAntiSpamService;
import acme.entities.claim.Claim;

@Service
public class AnyClaimPublishService extends AbstractAntiSpamService<Any, Claim> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyClaimRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Claim object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Claim();
		object.setInstantiationMoment(moment);
		object.setCode("");
		object.setHeading("");
		object.setDescription("");
		object.setDepartament("");
		object.setEmail("");
		object.setLink("");

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Claim object) {
		assert object != null;

		super.bind(object, "code", "heading", "description", "departament", "email", "link");
	}

	@Override
	public void validate(final Claim object) {
		assert object != null;

		boolean state;

		if (!super.getBuffer().getErrors().hasErrors("confirmation")) {
			state = super.getRequest().getData("confirmation", boolean.class);
			super.state(state, "confirmation", "javax.validation.constraints.AssertTrue.message");
		}
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			state = !this.repository.existsByCode(object.getCode());
			super.state(state, "code", "any.claim.form.error.duplicated-code");
		}
		super.validateSpam(object);

	}

	@Override
	public void perform(final Claim object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "heading", "description", "instantiationMoment", //
			"departament", "email", "link");
		dataset.put("confirmation", false);

		super.getResponse().addData(dataset);
	}

}
