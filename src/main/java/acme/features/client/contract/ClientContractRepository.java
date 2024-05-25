
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLog;
import acme.entities.project.Project;
import acme.roles.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Client c where c.userAccount.id = :id")
	Client findOneClientByUserAccountId(int id);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllProjectsPublish();

	@Query("SELECT c FROM Contract c WHERE c.id = :id")
	Contract findOneContractById(int id);

	@Query("SELECT c FROM Contract c WHERE c.client.userAccount.id = :clientId")
	Collection<Contract> findContractByClientId(int clientId);

	@Query("select c from Client c where c.id = :id")
	Client findOneClientById(int id);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select pl from ProgressLog pl where pl.contract.id = :clientId")
	Collection<ProgressLog> findManyProgressLogsId(int clientId);

	@Query("select c from Contract c where c.code = :code")
	Contract findOneContractByCode(String code);

	@Query("select count(c) > 0 from Contract c where c.code = :code and c.id != :id")
	Boolean existsOtherByCodeAndId(String code, int id);

	@Query("select pl from ProgressLog pl where pl.contract.id = :masterId")
	Collection<ProgressLog> findManyProgressLogByMasterId(int masterId);

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findManyContractById(int projectId);

	@Query("SELECT CASE WHEN COUNT(pl) > 0 THEN false ELSE true END FROM ProgressLog pl WHERE pl.contract.id = :id AND pl.draftMode = true")
	boolean areAllProgressLogPublished(int id);

	@Query("select c.budget from Contract c where c.project.id = :id AND c.draftMode = false")
	Collection<Money> areAllBudgetContractExcedCostProject(int id);

	@Query("select count(c) > 0 from Contract c where c.code = :code")
	Boolean existsByCode(String code);

	@Query("select sys.acceptedCurrencies from SystemConfiguration sys")
	String findAcceptedCurrencies();

	@Query("SELECT count(p) > 0 FROM Project p WHERE p.id = :id and p.draftMode = true")
	Boolean ProjectIsDraftMode(int id);
}
