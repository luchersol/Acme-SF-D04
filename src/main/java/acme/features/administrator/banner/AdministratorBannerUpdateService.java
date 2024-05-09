
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.components.AbstractAntiSpamService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerUpdateService extends AbstractAntiSpamService<Administrator, Banner> {

	@Autowired
	private AdministratorBannerRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Banner banner;
		masterId = super.getRequest().getData("id", int.class);
		banner = this.repository.findBannerById(masterId);
		status = banner != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findBannerById(id);
		object.setInstanciationOrUpdateMoment(MomentHelper.getCurrentMoment());
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;
		super.bind(object, "displayStart", "displayEnd", "image", "slogan", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("displayStart")) {
			boolean notNull = object.getDisplayStart() != null && object.getInstanciationOrUpdateMoment() != null;
			Boolean timeConcordance = notNull && MomentHelper.isAfter(object.getDisplayStart(), object.getInstanciationOrUpdateMoment());
			super.state(timeConcordance, "displayStart", "administrator.banner.form.error.badDisplayStartConcordance");
		}

		if (!super.getBuffer().getErrors().hasErrors("displayEnd")) {
			boolean notNull = object.getDisplayEnd() != null && object.getDisplayStart() != null;
			Boolean timeConcordance = notNull && MomentHelper.isAfter(object.getDisplayEnd(), object.getDisplayStart());
			super.state(timeConcordance, "displayEnd", "administrator.banner.form.error.badTimeConcordance");
		}

		if (!super.getBuffer().getErrors().hasErrors("displayEnd")) {
			boolean notNull = object.getDisplayEnd() != null && object.getDisplayStart() != null;
			Boolean goodDuration = notNull && MomentHelper.isLongEnough(object.getDisplayEnd(), object.getDisplayStart(), 1, ChronoUnit.WEEKS);
			super.state(goodDuration, "displayEnd", "administrator.banner.form.error.notEnoughDuration");
		}
		super.validateSpam(object);
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "instanciationOrUpdateMoment", "displayStart", "displayEnd", "image", "slogan", "link");
		super.getResponse().addData(dataset);
	}

}
