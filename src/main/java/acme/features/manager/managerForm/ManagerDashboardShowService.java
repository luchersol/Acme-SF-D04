/*
 * AdministratorDashboardShowService.java
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
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.form.ManagerForm;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerForm> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ManagerForm dashboard = new ManagerForm();
		int managerId;
		managerId = this.getRequest().getPrincipal().getActiveRoleId();

		Function<Collection<Object[]>, List<Money>> transformToListMoney = collect -> collect.stream().map(obj -> {
			Money money = new Money();
			money.setCurrency((String) obj[0]);
			money.setAmount((Double) obj[1]);
			return money;
		}).toList();

		Integer totalNumberProjectMust = this.repository.totalNumberProjectMust(managerId);
		Integer totalNumberProjectShould = this.repository.totalNumberProjectShould(managerId);
		Integer totalNumberProjectCould = this.repository.totalNumberProjectCould(managerId);
		Integer totalNumberProjectWont = this.repository.totalNumberProjectWont(managerId);
		Double averageEstimatedCostUserStories = this.repository.averageEstimatedCostUserStories(managerId);
		Double deviationEstimatedCostUserStories = this.repository.deviationEstimatedCostUserStories(managerId);
		Double minimumEstimatedCostUserStories = this.repository.minimumEstimatedCostUserStories(managerId);
		Double maximumEstimatedCostUserStories = this.repository.maximumEstimatedCostUserStories(managerId);
		List<Money> averageEstimatedCostProjects = transformToListMoney.apply(this.repository.averageEstimatedCostProjects(managerId));
		List<Money> deviationEstimatedCostProjects = transformToListMoney.apply(this.repository.deviationEstimatedCostProjects(managerId));
		List<Money> minimumEstimatedCostProjects = transformToListMoney.apply(this.repository.minimumEstimatedCostProjects(managerId));
		List<Money> maximumEstimatedCostProjects = transformToListMoney.apply(this.repository.maximumEstimatedCostProjects(managerId));

		dashboard.setTotalNumberProjectMust(totalNumberProjectMust);
		dashboard.setTotalNumberProjectShould(totalNumberProjectShould);
		dashboard.setTotalNumberProjectCould(totalNumberProjectCould);
		dashboard.setTotalNumberProjectWont(totalNumberProjectWont);
		dashboard.setAverageEstimatedCostUserStories(averageEstimatedCostUserStories);
		dashboard.setDeviationEstimatedCostUserStories(deviationEstimatedCostUserStories);
		dashboard.setMinimumEstimatedCostUserStories(minimumEstimatedCostUserStories);
		dashboard.setMaximumEstimatedCostUserStories(maximumEstimatedCostUserStories);
		dashboard.setAverageEstimatedCostProjects(averageEstimatedCostProjects);
		dashboard.setDeviationEstimatedCostProjects(deviationEstimatedCostProjects);
		dashboard.setMinimumEstimatedCostProjects(minimumEstimatedCostProjects);
		dashboard.setMaximumEstimatedCostProjects(maximumEstimatedCostProjects);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerForm object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberProjectMust", "totalNumberProjectShould", //
			"totalNumberProjectCould", "totalNumberProjectWont", //
			"averageEstimatedCostUserStories", "deviationEstimatedCostUserStories", // 
			"minimumEstimatedCostUserStories", "maximumEstimatedCostUserStories", // 
			"averageEstimatedCostProjects", "deviationEstimatedCostProjects", //
			"minimumEstimatedCostProjects", "maximumEstimatedCostProjects");

		super.getResponse().addData(dataset);
	}

}
