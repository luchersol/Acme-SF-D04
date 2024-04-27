
package acme.features.administrator.banner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Administrator;
import acme.entities.banner.Banner;

@Controller
public class AdministratorBannerController extends AbstractController<Administrator, Banner> {

	@Autowired
	private AdministratorBannerCreateService	createService;

	@Autowired
	private AdministratorBannerDeleteService	deleteService;

	@Autowired
	private AdministratorBannerListAllService	listService;

	@Autowired
	private AdministratorBannerShowService		showService;

	@Autowired
	private AdministratorBannerUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("list", this.listService);
	}

}
