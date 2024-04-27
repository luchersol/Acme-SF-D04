
package acme.features.any.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.training.TrainingModule;
import acme.roles.Developer;

@Repository
public interface AnyTrainingModuleRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select t from TrainingModule t where t.draftMode = false")
	Collection<TrainingModule> findManyTrainingModulesByAvailability();

	@Query("select d from Developer d")
	Collection<Developer> findAllDevelopers();

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllProjectPublish();

}
