
package acme.features.auditor.auditRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.audits.AuditRecord;
import acme.roles.Auditor;

@Controller
public class AuditorAuditRecordController extends AbstractController<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordCreateService		createService;

	@Autowired
	private AuditorAuditRecordDeleteService		deleteService;

	@Autowired
	private AuditorAuditRecordPublishService	publishService;

	@Autowired
	private AuditorAuditRecordShowService		showService;

	@Autowired
	private AuditorAuditRecordUpdateService		updateService;

	@Autowired
	private AuditorAuditRecordListService		listService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("list", this.listService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
