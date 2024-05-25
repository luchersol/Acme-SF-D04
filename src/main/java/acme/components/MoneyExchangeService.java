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

package acme.components;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import acme.client.data.datatypes.Money;
import acme.client.helpers.MomentHelper;
import acme.entities.moneyExchange.MoneyRate;
import acme.entities.systemConfiguration.SystemConfiguration;

@Service
public class MoneyExchangeService {

	// AbstractService interface ----------------------------------------------

	@Autowired
	MoneyExchangeRepository repository;


	public Money computeMoneyExchange(final Money source) {
		if (source == null)
			return null;

		RestTemplate api;
		SystemConfiguration sys;
		Money result = null;
		ExchangeRate record;
		String sourceCurrency, targetCurrency;
		Double sourceAmount, targetAmount;
		Map<String, Double> rates;
		Date updateMoment;
		Date moment;
		Long timeToUpdate;
		boolean sameDay;

		sys = this.repository.findSystemConfiguration();
		sourceAmount = source.getAmount();
		sourceCurrency = source.getCurrency();
		targetCurrency = sys.getSystemCurrency();
		updateMoment = sys.getUpdateMoment();
		timeToUpdate = (long) sys.getTimeToUpdate();
		moment = new Date();
		sameDay = MomentHelper.isLongEnough(moment, updateMoment, timeToUpdate, ChronoUnit.DAYS);

		if (sourceCurrency.equals(targetCurrency))
			return null;

		try {
			if (true)
				rates = this.repository.findAllMoneyRate(sourceCurrency, targetCurrency).stream().collect(Collectors.toMap(MoneyRate::getCurrency, MoneyRate::getRate));
			else {
				List<MoneyRate> ratesSave = this.repository.findAllMoneyRate();
				rates = ratesSave.stream().collect(Collectors.toMap(MoneyRate::getCurrency, MoneyRate::getRate));

				api = new RestTemplate();

				record = api.getForObject( //				
					"https://api.apilayer.com/fixer/latest?apikey={0}", //
					ExchangeRate.class, //
					"yeHndIIPA3fsXEMNyYTP30GmQmXWSVjs");
				assert record != null;

				record.getRates().forEach(rates::put);

				for (MoneyRate rate : ratesSave) {
					String currency = rate.getCurrency();
					Double newRate = rates.get(currency);
					rate.setRate(newRate);
				}

				sys.setUpdateMoment(new Date((long) (record.getTimestamp() * 1000L)));

				this.repository.saveAll(ratesSave);
				this.repository.save(sys);

				MomentHelper.sleep(1000);
			}

			Double rate1, rate2;
			rate1 = rates.get(sourceCurrency);
			rate2 = rates.get(targetCurrency);
			if (rate1 != null && rate2 != null) {
				result = new Money();
				targetAmount = rate2 * sourceAmount / rate1;
				result.setAmount(targetAmount);
				result.setCurrency(targetCurrency);
			}

		} catch (final Throwable oops) {
			result = null;
		}

		return result;
	}

}
