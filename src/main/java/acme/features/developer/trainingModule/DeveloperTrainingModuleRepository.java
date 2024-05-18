/*
 * DeveloperTrainingModuleRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.training.TrainingModule;
import acme.entities.training.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("SELECT t FROM TrainingModule t WHERE t.id = :id")
	TrainingModule findOneTrainingById(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :masterId")
	Collection<TrainingSession> findManyTrainingSessionsByMasterId(int masterId);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllProjectPublish();

	@Query("SELECT CASE WHEN COUNT(t) > 0 THEN false ELSE true END FROM TrainingSession t WHERE t.trainingModule.id = :id AND t.draftMode = true")
	boolean areAllTrainingSessionsPublished(int id);

	@Query("select d from Developer d where d.id = :id")
	Developer findOneDeveloperById(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("SELECT t FROM TrainingModule t WHERE t.code = :code")
	TrainingModule findOneTrainingByCode(String code);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findOneProjectById(int id);

	@Query("SELECT t FROM TrainingModule t WHERE t.developer.userAccount.id = :developerId")
	Collection<TrainingModule> findTrainingsByDeveloperId(int developerId);

	@Query("SELECT t FROM TrainingModule t where t.developer.userAccount.id = :developerId and t.draftMode = false")
	Collection<TrainingModule> findPublishedTrainings(int developerId);

	@Query("select count(p) > 0 from TrainingModule p where p.code = :code and p.id != :id")
	Boolean existsOtherByCodeAndId(String code, int id);

}
