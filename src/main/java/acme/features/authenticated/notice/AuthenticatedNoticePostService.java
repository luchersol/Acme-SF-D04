
package acme.features.authenticated.notice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.DefaultUserIdentity;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.entities.notice.Notice;

@Service
public class AuthenticatedNoticePostService extends AbstractService<Authenticated, Notice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedNoticeRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Notice object;
		Date moment;

		String username;
		DefaultUserIdentity fullname;
		String author;

		moment = MomentHelper.getCurrentMoment();

		username = super.getRequest().getPrincipal().getUsername();
		fullname = this.repository.findOneUserAccountByUsername(username).getIdentity();

		author = username + " - " + fullname.getSurname() + ", " + fullname.getName();

		object = new Notice();
		object.setInstantiationMoment(moment);
		object.setAuthor(author);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Notice object) {
		assert object != null;

		super.bind(object, "title", "message", "link", "email");
	}

	@Override
	public void validate(final Notice object) {
		assert object != null;

		boolean state;

		if (!super.getBuffer().getErrors().hasErrors("confirmation")) {
			state = super.getRequest().getData("confirmation", boolean.class);

			super.state(state, "confirmation", "javax.validation.constraints.AssertTrue.message");
		}

	}
	
	@Override
	public void perform(final Notice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "title", "author", "message", "link", "email");
		dataset.put("confirmation", false);

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
