
package acme.features.any.progressLog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLog;

@Repository
public interface AnyProgressLogRepository extends AbstractRepository {

	@Query("select pl from ProgressLog pl where pl.id = :id")
	ProgressLog findProgressLogById(int id);

	@Query("select c from Contract c")
	Collection<Contract> findContracts();

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);

	@Query("select pl from ProgressLog pl where pl.contract.id = :id and pl.draftMode = false")
	Collection<ProgressLog> findPublishedProgressLogsOfContract(int id);
}
