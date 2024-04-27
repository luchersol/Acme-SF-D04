/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.managerForm;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(DISTINCT pu.project) FROM ProjectUserStory pu WHERE pu.project.manager.id = :managerId AND pu.userStory.priority = acme.entities.project.PriorityUserStory.MUST")
	Integer totalNumberProjectMust(int managerId);

	@Query("SELECT COUNT(DISTINCT pu.project) FROM ProjectUserStory pu WHERE pu.project.manager.id = :managerId AND pu.userStory.priority = acme.entities.project.PriorityUserStory.SHOULD")
	Integer totalNumberProjectShould(int managerId);

	@Query("SELECT COUNT(DISTINCT pu.project) FROM ProjectUserStory pu WHERE pu.project.manager.id = :managerId AND pu.userStory.priority = acme.entities.project.PriorityUserStory.COULD")
	Integer totalNumberProjectCould(int managerId);

	@Query("SELECT COUNT(DISTINCT pu.project) FROM ProjectUserStory pu WHERE pu.project.manager.id = :managerId AND pu.userStory.priority = acme.entities.project.PriorityUserStory.WONT")
	Integer totalNumberProjectWont(int managerId);

	@Query("SELECT avg(us.estimatedCost) FROM UserStory us WHERE us.manager.id = :managerId")
	Double averageEstimatedCostUserStories(int managerId);

	@Query("SELECT stddev(us.estimatedCost) FROM UserStory us WHERE us.manager.id = :managerId")
	Double deviationEstimatedCostUserStories(int managerId);

	@Query("SELECT min(us.estimatedCost) FROM UserStory us WHERE us.manager.id = :managerId")
	Double minimumEstimatedCostUserStories(int managerId);

	@Query("SELECT max(us.estimatedCost) FROM UserStory us WHERE us.manager.id = :managerId")
	Double maximumEstimatedCostUserStories(int managerId);

	@Query("SELECT p.cost.currency, avg(p.cost.amount) FROM Project p WHERE p.manager.id = :managerId GROUP BY p.cost.currency")
	Collection<Object[]> averageEstimatedCostProjects(int managerId);

	@Query("SELECT p.cost.currency, stddev(p.cost.amount) FROM Project p WHERE p.manager.id = :managerId GROUP BY p.cost.currency")
	Collection<Object[]> deviationEstimatedCostProjects(int managerId);

	@Query("SELECT p.cost.currency, min(p.cost.amount) FROM Project p WHERE p.manager.id = :managerId GROUP BY p.cost.currency")
	Collection<Object[]> minimumEstimatedCostProjects(int managerId);

	@Query("SELECT p.cost.currency, max(p.cost.amount) FROM Project p WHERE p.manager.id = :managerId GROUP BY p.cost.currency")
	Collection<Object[]> maximumEstimatedCostProjects(int managerId);

}
