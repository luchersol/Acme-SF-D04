
package acme.features.sponsor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(i) FROM Invoice i WHERE i.sponsor.id = :id AND i.tax <= 21.0")
	Integer totalInvoicesWithTaxLessThanOrEqualTo21(int id);

	@Query("SELECT COUNT(s) FROM Sponsorship s WHERE s.sponsor.id = :id AND s.link IS NOT NULL")
	Integer totalSponsorshipWithLink(int id);

	// --------------------

	@Query("SELECT s.amount.currency, avg(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id GROUP BY s.amount.currency")
	Collection<Object[]> averageAmountOfTheSponsorships(int id);

	@Query("SELECT s.amount.currency, stddev(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id GROUP BY s.amount.currency")
	Collection<Object[]> deviationAmountOfTheSponsorships(int id);

	@Query("SELECT s.amount.currency, min(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id GROUP BY s.amount.currency")
	Collection<Object[]> minimumAmountOfTheSponsorships(int id);

	@Query("SELECT s.amount.currency, max(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id GROUP BY s.amount.currency")
	Collection<Object[]> maximumAmountOfTheSponsorships(int id);

	// --------------------

	@Query("SELECT i.quantity.currency, avg(i.quantity.amount) FROM Invoice i WHERE i.sponsor.id = :id GROUP BY i.quantity.currency")
	Collection<Object[]> averageQuantityOfTheInvoices(int id);

	@Query("SELECT i.quantity.currency, stddev(i.quantity.amount) FROM Invoice i WHERE i.sponsor.id = :id GROUP BY i.quantity.currency")
	Collection<Object[]> deviationQuantityOfTheInvoices(int id);

	@Query("SELECT i.quantity.currency, min(i.quantity.amount) FROM Invoice i WHERE i.sponsor.id = :id GROUP BY i.quantity.currency")
	Collection<Object[]> minimumQuantityOfTheInvoices(int id);

	@Query("SELECT i.quantity.currency, max(i.quantity.amount) FROM Invoice i WHERE i.sponsor.id = :id GROUP BY i.quantity.currency")
	Collection<Object[]> maximumQuantityOfTheInvoices(int id);
}
