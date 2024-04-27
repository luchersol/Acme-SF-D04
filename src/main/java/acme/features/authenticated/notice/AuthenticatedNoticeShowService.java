
package acme.features.authenticated.notice;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.notice.Notice;

@Service
public class AuthenticatedNoticeShowService extends AbstractService<Authenticated, Notice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedNoticeRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Notice notice;
		Date deadline;

		id = super.getRequest().getData("id", int.class);
		notice = this.repository.findOneNoticeById(id);
		deadline = MomentHelper.deltaFromCurrentMoment(-30, ChronoUnit.DAYS);
		status = MomentHelper.isAfter(notice.getInstantiationMoment(), deadline);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Notice object;
		int id;
		id = super.getRequest().getData("id", int.class);

		object = this.repository.findOneNoticeById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "message", "link", "email", "author", "instantiationMoment");

		super.getResponse().addData(dataset);
	}
}
