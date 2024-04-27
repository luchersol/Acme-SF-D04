
package acme.features.administrator.dashboard;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.objective.PriorityObjective;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(x) FROM Administrator x")
	Integer totalNumberAdministrator();

	@Query("SELECT COUNT(x) FROM Auditor x")
	Integer totalNumberAuditor();

	@Query("SELECT COUNT(x) FROM Client x")
	Integer totalNumberClient();

	@Query("SELECT COUNT(x) FROM Developer x")
	Integer totalNumberDeveloper();

	@Query("SELECT COUNT(x) FROM Manager x")
	Integer totalNumberManager();

	@Query("SELECT COUNT(x) FROM Sponsor x")
	Integer totalNumberSponsor();

	// --------------------

	@Query("SELECT COUNT(x) FROM Notice x")
	Integer totalNumberNotices();

	@Query("SELECT COUNT(x) FROM Notice x WHERE x.email IS NOT NULL AND x.link IS NOT NULL")
	Integer totalNumberNoticesWithEmailAndLink();

	@Query("SELECT COUNT(x) FROM Objective x")
	Integer totalNumberObjectives();

	//@Query("SELECT COUNT(x) FROM Objective x WHERE x.priority = acme.entities.objective.PriorityObjective.HIGH")
	@Query("SELECT COUNT(x) FROM Objective x WHERE x.priority = :priority")
	Integer totalNumberCriticalObjetives(PriorityObjective priority);

	// --------------------

	@Query("SELECT avg(s.probability * s.impact) FROM Risk s")
	Double averageValueRisk();

	@Query("SELECT stddev(s.probability * s.impact) FROM Risk s")
	Double deviationValueRisk();

	@Query("SELECT min(s.probability * s.impact) FROM Risk s")
	Double minimumValueRisk();

	@Query("SELECT max(s.probability * s.impact) FROM Risk s")
	Double maximumValueRisk();

	// --------------------

	@Query("SELECT COUNT(c) FROM Claim c WHERE c.instantiationMoment > :highDate AND c.instantiationMoment <= :lowDate")
	Integer numberClaimsFromOneDateToOther(Date lowDate, Date highDate);

	@Query("SELECT COUNT(c) FROM Claim c WHERE c.instantiationMoment <= :date")
	Integer numberClaimsForMoreThan10Weeks(Date date);
}
