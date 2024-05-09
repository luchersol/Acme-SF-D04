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

package acme.features.authenticated.moneyExchange;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.moneyExchangeConfiguration.MoneyRate;
import acme.entities.systemConfiguration.SystemConfiguration;

@Repository
public interface MoneyExchangeRepository extends AbstractRepository {

	@Query("select sys.systemCurrency from SystemConfiguration sys")
	String findSystemCurrency();

	@Query("select sys.acceptedCurrencies from SystemConfiguration sys")
	String findAcceptedCurrencies();

	@Query("select sys from SystemConfiguration sys")
	SystemConfiguration findSystemConfiguration();

	@Query("select mr from MoneyRate mr")
	List<MoneyRate> findAllMoneyRate();
}
