/*
 * AuthenticatedMoneyExchangeController.java
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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.form.MoneyExchange;

@Controller
public class AuthenticatedMoneyExchangeController extends AbstractController<Authenticated, MoneyExchange> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedMoneyExchangePerformService exchangeService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("perform", this.exchangeService);
	}

}
