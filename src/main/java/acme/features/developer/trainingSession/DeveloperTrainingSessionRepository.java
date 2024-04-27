/*
 * EmployerDutyRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.training.TrainingModule;
import acme.entities.training.TrainingSession;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select ts.trainingModule from TrainingSession ts where ts.id = :id")
	TrainingModule findOneTrainingModuleByTrainingSessionId(int id);

	@Query("select ts from TrainingSession ts where ts.id = :id")
	TrainingSession findOneTrainingSessionById(int id);

	@Query("SELECT s FROM TrainingSession s WHERE s.code = :code")
	TrainingSession findOneTrainingSessionByCode(String code);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :masterId")
	Collection<TrainingSession> findManyTrainingSessionsByMasterId(int masterId);

}
