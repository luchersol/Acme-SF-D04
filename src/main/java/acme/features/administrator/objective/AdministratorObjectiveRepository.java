
package acme.features.administrator.objective;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.Administrator;
import acme.client.repositories.AbstractRepository;
import acme.entities.objective.Objective;

@Repository
public interface AdministratorObjectiveRepository extends AbstractRepository {

	@Query("select o from Objective o")
	Objective findALlObjectives();

	@Query("select o from Objective o where o.id = :id")
	Objective findOneObjectiveById(int id);

	@Query("select a from Administrator a where a.id = :id")
	Administrator findOneAdministratorById(int id);
}
