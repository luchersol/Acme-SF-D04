
package acme.features.any.codeAudit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Repository
public interface AnyCodeAuditRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select a from Auditor a where a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select ca from CodeAudit ca where ca.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("select ca from CodeAudit ca where ca.draftMode = false")
	List<CodeAudit> findPublishedCodeAudit();

	@Query("select ar from AuditRecord ar where ar.codeAudit.id = :codeAuditId and ar.draftMode = false")
	List<AuditRecord> findPublishedAuditRecordsOfCodeAudit(Integer codeAuditId);

	@Query("select ar from AuditRecord ar where ar.id = :id")
	AuditRecord findAuditRecordById(int id);

	@Query("select p from Project p where p.draftMode = false")
	List<Project> findPublishedProjects();

}
