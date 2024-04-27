
package acme.features.authenticated.notice;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.notice.Notice;

@Controller
public class AuthenticatedNoticeController extends AbstractController<Authenticated, Notice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedNoticeListService	listService;

	@Autowired
	private AuthenticatedNoticeShowService	showService;

	@Autowired
	private AuthenticatedNoticePostService	postService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);

		super.addCustomCommand("post", "create", this.postService);

	}
}
