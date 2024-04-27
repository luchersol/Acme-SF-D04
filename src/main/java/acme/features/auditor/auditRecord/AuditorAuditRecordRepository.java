
package acme.features.auditor.auditRecord;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select ar.mark from AuditRecord ar where ar.codeAudit.id = :id AND ar.draftMode = false GROUP BY ar.mark ORDER BY COUNT(ar.mark) DESC, ar.mark")
	List<Mark> findCodeAuditMark(int id);

	@Query("select ar from AuditRecord ar where ar.id = :id")
	AuditRecord findAuditRecordById(int id);

	@Query("select ar from AuditRecord ar where ar.code = :code")
	AuditRecord findAuditRecordByCode(String code);

	@Query("select ca from CodeAudit ca")
	Collection<CodeAudit> findCodeAudits();

	@Query("select ca from CodeAudit ca where ca.auditor.id = :auditorId")
	Collection<CodeAudit> findCodeAuditByAuditor(int auditorId);

	@Query("select a from Auditor a where id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select ca from CodeAudit ca where ca.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("select ar from AuditRecord ar where ar.codeAudit.id = :id")
	Collection<AuditRecord> findAuditRecordsOfCodeAudit(int id);

}
