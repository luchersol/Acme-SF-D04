
package acme.features.client.contract;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.contract.Contract;
import acme.roles.Client;

@Controller
public class ClientContractController extends AbstractController<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractListMineService	listMineService;

	@Autowired
	private ClientContractShowService		showService;

	@Autowired
	private ClientContractCreateService		createService;

	@Autowired
	private ClientContractUpdateService		updateService;

	@Autowired
	private ClientContractDeleteService		deleteService;

	@Autowired
	private ClientContractPublishService	publishService;

	// Constructors ---------------------------------------------------------


	@PostConstruct
	protected void initialslise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("publish", "perform", this.publishService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
	}

}
