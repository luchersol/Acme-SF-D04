
package acme.features.authenticated.risk;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.risk.Risk;

@Repository
public interface AuthenticatedRiskRepository extends AbstractRepository {

	@Query("select r from Risk r where r.id = :id")
	Risk findOneRiskById(int id);

	@Query("select a from Risk a")
	Collection<Risk> findAllRisks();

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllProjectsPublish();

}
