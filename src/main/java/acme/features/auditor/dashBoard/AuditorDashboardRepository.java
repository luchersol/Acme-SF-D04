
package acme.features.auditor.dashBoard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditType;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select count(ca) from CodeAudit ca where ca.type = :at and ca.auditor.id = :id and ca.draftMode = false")
	Integer totalStaticOrDynamicCodeAudits(int id, AuditType at);

	@Query("select avg(select count(ar) from AuditRecord ar where ar.codeAudit.id = ca.id and ar.draftMode = false ) from CodeAudit ca where ca.auditor.id = :id and ca.draftMode = false")
	Double averageAuditRecordsInCodeAudits(int id);

	@Query("select stddev(ar) from AuditRecord ar where ar.codeAudit.auditor.id = :id and ar.codeAudit.draftMode = false and ar.draftMode = false")
	Double deviationAuditRecordsInCodeAudits(int id);

	@Query("select max(select count(ar) from AuditRecord ar where ar.codeAudit.id = ca.id and ar.draftMode = false ) from CodeAudit ca where ca.auditor.id = :id and ca.draftMode = false")
	Integer maxAuditRecordsInCodeAudits(int id);

	@Query("select min(select count(ar) from AuditRecord ar where ar.codeAudit.id = ca.id and ar.draftMode = false ) from CodeAudit ca where ca.auditor.id = :id and ca.draftMode = false")
	Integer minAuditRecordsInCodeAudits(int id);

	//

	@Query("select ar.endDate, ar.startDate from AuditRecord ar where ar.codeAudit.auditor.id = :id and ar.codeAudit.draftMode = false and ar.draftMode = false")
	List<Object[]> findStartAndEndDates(int id);
}
