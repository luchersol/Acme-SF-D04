
package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select ar.mark from AuditRecord ar where ar.codeAudit.id = :id AND ar.draftMode = false GROUP BY ar.mark ORDER BY COUNT(ar.mark) DESC LIMIT 1")
	Mark findCodeAuditMark(int id);

	@Query("select ca from CodeAudit ca where ca.code = :code")
	CodeAudit findCodeAuditWithCode(String code);

	@Query("select p from Project p where p.id = :id and p.draftMode = false")
	Project findOnePublishedProjectById(int id);

	@Query("select p from Project p where p.draftMode = false")
	List<Project> findPublishedProjects();

	@Query("select a from Auditor a where a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select ca from CodeAudit ca where ca.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("select ca from CodeAudit ca where ca.auditor.id = :auditorId")
	Collection<CodeAudit> findCodeAuditByAuditor(int auditorId);

	@Query("select ar from AuditRecord ar where ar.codeAudit.id = :codeAuditId")
	List<AuditRecord> findAuditRecordsOfCodeAudit(Integer codeAuditId);

	@Query("select ar from AuditRecord ar where ar.id = :id")
	AuditRecord findAuditRecordById(int id);

	@Query("select count(ar) from AuditRecord ar where ar.codeAudit.id = :codeAuditId and ar.draftMode = true")
	Integer countNotPublishedAuditRecordsOfCodeAudit(int codeAuditId);

	@Query("select min(ar.startDate) from AuditRecord ar where ar.codeAudit.id = :codeAuditId")
	Date findMaximumValidExecutionDate(int codeAuditId);

	@Query("select p.draftMode from Project p where p.id = :projectId")
	Boolean projectIsDraftMode(int projectId);

}
