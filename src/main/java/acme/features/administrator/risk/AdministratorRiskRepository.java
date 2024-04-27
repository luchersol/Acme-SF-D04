
package acme.features.administrator.risk;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.Administrator;
import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.risk.Risk;

@Repository
public interface AdministratorRiskRepository extends AbstractRepository {

	@Query("SELECT r FROM Risk r WHERE r.id = :id")
	Risk findOneRiskById(int id);

	@Query("SELECT a FROM Administrator a WHERE a.id = :id")
	Administrator findOneAdministratorById(int id);

	@Query("SELECT r FROM Risk r WHERE r.reference = :reference")
	Risk findOneRiskByReference(String reference);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findOneProjectById(int id);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllProjectPublish();

	@Query("SELECT r FROM Risk r WHERE r.administrator.userAccount.id = :administratorId")
	Collection<Risk> findRisksByAdministratorId(int administratorId);

}
