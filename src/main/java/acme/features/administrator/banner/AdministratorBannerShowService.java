
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerShowService extends AbstractService<Administrator, Banner> {

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
		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "instanciationOrUpdateMoment", "displayStart", "displayEnd", "image", "slogan", "link");
		super.getResponse().addData(dataset);
	}

}
