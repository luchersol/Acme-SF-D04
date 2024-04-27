
package acme.features.developer.developerForm;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DeveloperDeveloperFormRepository extends AbstractRepository {

	@Query("select count(m) from TrainingModule m where m.developer.userAccount.id = :id and m.updateMoment is not null")
	Integer totalNumberOfTrainingModulesWithUpdateMoment(int id);

	@Query("select count(s) from TrainingSession s join s.trainingModule m where m.developer.userAccount.id = :developerId and s.link is not null and trim(s.link) != ''")
	Integer totalNumberOfTrainingSessionsWithLink(int developerId);

	@Query("select avg(m.estimatedTotalTime) from TrainingModule m where m.developer.userAccount.id  = :id")
	Double averageTimeOfTrainingModules(int id);

	@Query("select stddev(m.estimatedTotalTime) from TrainingModule m where m.developer.userAccount.id  = :id")
	Double standardDeviationTimeOfTrainingModules(int id);

	@Query("select min(m.estimatedTotalTime) from TrainingModule m where m.developer.userAccount.id  = :id")
	Double minTimeOfTrainingModules(int id);

	@Query("select max(m.estimatedTotalTime) from TrainingModule m where m.developer.userAccount.id = :id")
	Double maxTimeOfTrainingModules(int id);

}
