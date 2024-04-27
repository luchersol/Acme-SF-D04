
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLogs;
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

	@Query("select pl from ProgressLogs pl where pl.contract.id = :clientId")
	Collection<ProgressLogs> findManyProgressLogsId(int clientId);

	@Query("select c from Contract c where c.code = :code")
	Contract findOneContractByCode(String code);
}
