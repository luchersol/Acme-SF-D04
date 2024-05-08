/*
 * AuthenticatedMoneyExchangePerformService.java
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

import java.util.Date;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import acme.client.data.accounts.Authenticated;
import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.StringHelper;
import acme.client.services.AbstractService;
import acme.components.ExchangeRate;
import acme.form.MoneyExchange;

@Service
public class AuthenticatedMoneyExchangePerformService extends AbstractService<Authenticated, MoneyExchange> {

	// AbstractService interface ----------------------------------------------

	@Autowired
	AuthenticatedMoneyExchangeRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		MoneyExchange object;

		object = new MoneyExchange();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final MoneyExchange object) {
		assert object != null;

		super.bind(object, "source", "target");
	}

	@Override
	public void validate(final MoneyExchange object) {
		assert object != null;

		boolean state;

		if (!super.getBuffer().getErrors().hasErrors("invalid-currency")) {
			state = Arrays.asList(this.repository.findAcceptedCurrencies().split(",")).contains(object.getSource().getCurrency());
			super.state(state, "invalid-currency", "authenticated.money-exchange.form.error.invalid-currency");
		}
		if (!super.getBuffer().getErrors().hasErrors("invalid-currency")) {
			state = !this.repository.findSystemCurrency().equals(object.getSource().getCurrency());
			super.state(state, "invalid-currency", "authenticated.money-exchange.form.error.not-system-currency");
		}
		if (!super.getBuffer().getErrors().hasErrors("cost")) {
			state = object.getSource().getAmount() > 0;
			super.state(state, "cost", "authenticated.money-exchange.form.error.cost");
		}

	}

	@Override
	public void perform(final MoneyExchange object) {
		assert object != null;

		Money source, target;
		String targetCurrency;
		Date date;
		MoneyExchange exchange;

		targetCurrency = this.repository.findSystemCurrency();
		source = super.getRequest().getData("source", Money.class);
		exchange = this.computeMoneyExchange(source, targetCurrency);
		super.state(exchange != null, "*", "authenticated.money-exchange.form.error.api-error");
		if (exchange == null) {
			object.setTarget(null);
			object.setDate(null);
		} else {
			target = exchange.getTarget();
			object.setTarget(target);
			date = exchange.getDate();
			object.setDate(date);
		}
	}

	@Override
	public void unbind(final MoneyExchange object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "source", "date", "target");

		super.getResponse().addData(dataset);
	}

	// Ancillary methods ------------------------------------------------------

	public MoneyExchange computeMoneyExchange(final Money source, final String targetCurrency) {
		assert source != null;
		assert !StringHelper.isBlank(targetCurrency);

		MoneyExchange result;
		RestTemplate api;
		ExchangeRate record;
		String sourceCurrency;
		Double sourceAmount, targetAmount;
		Money target;
		Date moment;

		try {
			api = new RestTemplate();

			sourceAmount = source.getAmount();
			sourceCurrency = source.getCurrency();

			record = api.getForObject( //				
				"https://api.apilayer.com/fixer/lastest?base={0}", //
				ExchangeRate.class, //
				sourceAmount, //
				sourceCurrency, //
				targetCurrency, //
				"yeHndIIPA3fsXEMNyYTP30GmQmXWSVjs");

			assert record != null;
			// targetAmount = record.getRates();

			target = new Money();
			// target.setAmount(targetAmount);
			target.setCurrency(targetCurrency);

			moment = new Date((long) (record.getTimestamp() * 1000L));

			result = new MoneyExchange();
			result.setSource(source);
			result.setDate(moment);
			result.setTarget(target);

			MomentHelper.sleep(1000); // HINT: need to pause the requests to the API a bit down to prevent DOS attacks
		} catch (final Throwable oops) {
			result = null;
		}

		return result;
	}

}
