/*
 * AdministratorDashboardRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLog;
import acme.entities.project.Project;
import acme.entities.project.ProjectUserStory;
import acme.entities.sponsorship.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.training.TrainingModule;
import acme.entities.training.TrainingSession;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select sys.acceptedCurrencies from SystemConfiguration sys")
	String findAcceptedCurrencies();

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select m from Manager m where m.id = :id")
	Manager findManagerById(int id);

	@Query("select s from Sponsorship s where s.project.id = :projectId")
	Collection<Sponsorship> findSponsoshipsByProjectId(int projectId);

	@Query("select i from Invoice i where i.sponsorship.project.id = :projectId")
	Collection<Invoice> findInvoicesByProjectId(int projectId);

	@Query("select tm from TrainingModule tm where tm.project.id = :projectId")
	Collection<TrainingModule> findTraningModulesByProjectId(int projectId);

	@Query("select ts from TrainingSession ts where ts.trainingModule.project.id = :projectId")
	Collection<TrainingSession> findTraningSessionsByProjectId(int projectId);

	@Query("select ca from CodeAudit ca where ca.project.id = :projectId")
	Collection<CodeAudit> findCodeAuditsByProjectId(int projectId);

	@Query("select ar from AuditRecord ar where ar.codeAudit.project.id = :projectId")
	Collection<AuditRecord> findAuditRecordsByProjectId(int projectId);

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findContractsByProjectId(int projectId);

	@Query("select pl from ProgressLog pl where pl.contract.project.id = :projectId")
	Collection<ProgressLog> findProgressLogsByProjectId(int projectId);

	@Query("select pu from ProjectUserStory pu where pu.project.id = :projectId")
	Collection<ProjectUserStory> findRelationsUserStoriesWithProjectByProjectId(int projectId);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("select p from Project p where p.manager.id = :managerId and p.draftMode = false")
	Collection<Project> findPublishedProjectsByManagerId(int managerId);

	@Query("select count(pu.project) = 0 from ProjectUserStory pu where pu.userStory.draftMode = true and pu.project.id = :projectId")
	Boolean allUserStoriesPublishedByProjecId(int projectId);

	@Query("select count(pu.project) > 0 from ProjectUserStory pu where pu.project.id = :projectId")
	Boolean anyUserStoryByProjectId(int projectId);

	@Query("select count(p) > 0 from Project p where p.code = :code")
	Boolean existsByCode(String code);

	@Query("select count(p) > 0 from Project p where p.code = :code and p.id != :id")
	Boolean existsOtherByCodeAndId(String code, int id);

}
