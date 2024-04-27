
package acme.features.developer.developerForm;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.form.DeveloperForm;
import acme.roles.Developer;

@Controller
public class DeveloperDeveloperFormController extends AbstractController<Developer, DeveloperForm> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperDeveloperFormShowService showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}

}
