
package acme.features.client.progressLogs;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.contract.ProgressLog;
import acme.roles.Client;

@Controller
public class ClientProgressLogController extends AbstractController<Client, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogListService	listService;

	@Autowired
	private ClientProgressLogShowService	showService;

	@Autowired
	private ClientProgressLogCreateService	createService;

	@Autowired
	private ClientProgressLogUpdateService	updateService;

	@Autowired
	private ClientProgressLogDeleteService	deleteService;

	@Autowired
	private ClientProgressLogPublishService	publishService;

	// Constructors ---------------------------------------------------------


	@PostConstruct
	protected void initialslise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("publish", "update", this.publishService);
	}

}
