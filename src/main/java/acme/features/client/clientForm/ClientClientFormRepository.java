
package acme.features.client.clientForm;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ClientClientFormRepository extends AbstractRepository {

	@Query("SELECT c.budget.currency, AVG(c.budget.amount) FROM Contract c WHERE c.client.userAccount.id = :id GROUP BY c.budget.currency")
	Collection<Object[]> averageBudgetOfContracts(int id);

	@Query("SELECT c.budget.currency, STDDEV(c.budget.amount) FROM Contract c WHERE c.client.userAccount.id = :id GROUP BY c.budget.currency")
	Collection<Object[]> deviationBudgetOfContracts(int id);

	@Query("SELECT c.budget.currency, MIN(c.budget.amount) FROM Contract c WHERE c.client.userAccount.id = :id GROUP BY c.budget.currency")
	Collection<Object[]> minimumBudgetOfContracts(int id);

	@Query("SELECT c.budget.currency, MAX(c.budget.amount) FROM Contract c WHERE c.client.userAccount.id = :id GROUP BY c.budget.currency")
	Collection<Object[]> maximumBudgetOfContracts(int id);

	@Query("select count(p) from ProgressLog p join p.contract c where c.client.userAccount.id = :id and p.completeness < 25")
	Integer countCompletenessBelow25(int id);

	@Query("select count(p) from ProgressLog p join p.contract c where c.client.userAccount.id = :id and p.completeness >= 25 and p.completeness < 50")
	Integer countCompletenessBetween25And50(int id);

	@Query("select count(p) from ProgressLog p join p.contract c where c.client.userAccount.id = :id and p.completeness >= 50 and p.completeness < 75")
	Integer countCompletenessBetween50And75(int id);

	@Query("select count(p) from ProgressLog p join p.contract c where c.client.userAccount.id = :id and p.completeness >= 75")
	Integer countCompletenessAbove75(int id);

}
