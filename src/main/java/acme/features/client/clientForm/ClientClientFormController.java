
package acme.features.client.clientForm;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.form.ClientForm;
import acme.roles.Client;

@Controller
public class ClientClientFormController extends AbstractController<Client, ClientForm> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientClientFormShowService showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}

}
