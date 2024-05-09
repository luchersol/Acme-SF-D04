/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLog;
import acme.entities.project.Project;
import acme.entities.project.ProjectUserStory;
import acme.entities.project.UserStory;
import acme.entities.sponsorship.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.training.TrainingModule;
import acme.entities.training.TrainingSession;
import acme.features.authenticated.moneyExchange.MoneyExchangeService;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository	repository;

	@Autowired
	private MoneyExchangeService		moneyExchange;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Project object;
		Manager manager;

		id = this.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);
		manager = object == null ? null : object.getManager();
		status = object != null && object.getDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project project = new Project();

		super.getBuffer().addData(project);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractProject", "indication", "cost", "link", "draftMode");
	}

	@Override
	public void validate(final Project object) {
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		int projectId = object.getId();
		// Borrar Sponsorship(Invoice), TrainingModule(TrainingSession), Risk, CodeAudit(AuditRecord), Contract (ProgressLog), UserStory

		Collection<Invoice> invoices = this.repository.findInvoicesByProjectId(projectId);
		Collection<Sponsorship> sponsorships = this.repository.findSponsoshipsByProjectId(projectId);
		Collection<TrainingSession> trainingSessions = this.repository.findTraningSessionsByProjectId(projectId);
		Collection<TrainingModule> trainingModules = this.repository.findTraningModulesByProjectId(projectId);
		Collection<AuditRecord> auditRecords = this.repository.findAuditRecordsByProjectId(projectId);
		Collection<CodeAudit> codeAudits = this.repository.findCodeAuditsByProjectId(projectId);
		Collection<ProgressLog> progressLogs = this.repository.findProgressLogsByProjectId(projectId);
		Collection<Contract> contracts = this.repository.findContractsByProjectId(projectId);
		Collection<ProjectUserStory> projectUserStories = this.repository.findRelationsUserStoriesWithProjectByProjectId(projectId);
		Collection<UserStory> userStories = projectUserStories.stream().map(ProjectUserStory::getUserStory).toList();

		this.repository.deleteAll(invoices);
		this.repository.deleteAll(sponsorships);
		this.repository.deleteAll(trainingSessions);
		this.repository.deleteAll(trainingModules);
		this.repository.deleteAll(auditRecords);
		this.repository.deleteAll(codeAudits);
		this.repository.deleteAll(progressLogs);
		this.repository.deleteAll(contracts);
		this.repository.deleteAll(projectUserStories);
		this.repository.deleteAll(userStories);
		this.repository.delete(object);

	}

	@Override
	public void unbind(final Project object) {
		Dataset dataset;
		Money moneyExchange;

		dataset = super.unbind(object, "code", "title", "abstractProject", "indication", "cost", "link", "draftMode");
		moneyExchange = this.moneyExchange.computeMoneyExchange(object.getCost());
		dataset.put("moneyExchange", moneyExchange);

		super.getResponse().addData(dataset);

	}

}
