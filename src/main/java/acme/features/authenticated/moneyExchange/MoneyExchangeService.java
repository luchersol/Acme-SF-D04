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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import acme.client.data.datatypes.Money;
import acme.client.helpers.MomentHelper;
import acme.components.ExchangeRate;
import acme.entities.moneyExchangeConfiguration.MoneyRate;
import acme.entities.systemConfiguration.SystemConfiguration;

@Service
public class MoneyExchangeService {

	// AbstractService interface ----------------------------------------------

	@Autowired
	MoneyExchangeRepository repository;


	public Money computeMoneyExchange(final Money source) {
		assert source != null;

		RestTemplate api;
		SystemConfiguration sys;
		Money result = null;
		ExchangeRate record;
		String sourceCurrency, targetCurrency;
		Double sourceAmount, targetAmount;
		List<MoneyRate> rates;
		Date updateMoment;
		LocalDate moment, updateMomentLocalDate;
		boolean sameDay;

		sys = this.repository.findSystemConfiguration();
		sourceAmount = source.getAmount();
		sourceCurrency = source.getCurrency();
		targetCurrency = sys.getSystemCurrency();
		updateMoment = sys.getUpdateMoment();
		moment = LocalDate.now();
		updateMomentLocalDate = updateMoment == null ? null : updateMoment.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		sameDay = moment.equals(updateMomentLocalDate);

		if (sourceCurrency.equals(targetCurrency))
			return null;

		try {
			if (sameDay)
				rates = this.repository.findAllMoneyRate();
			else {
				api = new RestTemplate();

				record = api.getForObject( //				
					"https://api.apilayer.com/fixer/latest?apikey={0}", //
					ExchangeRate.class, //
					"yeHndIIPA3fsXEMNyYTP30GmQmXWSVjs");
				assert record != null;

				rates = record.getRates().entrySet().stream().map(entry -> {
					MoneyRate mr = new MoneyRate();
					mr.setCurrency(entry.getKey());
					mr.setRate(entry.getValue());
					return mr;
				}).toList();

				sys.setUpdateMoment(new Date((long) (record.getTimestamp() * 1000L)));

				this.repository.saveAll(rates);
				this.repository.save(sys);
			}

			MoneyRate rate1, rate2;
			rate1 = rates.stream().filter(i -> i.getCurrency().equals(sourceCurrency)).findFirst().orElse(null);
			rate2 = rates.stream().filter(i -> i.getCurrency().equals(targetCurrency)).findFirst().orElse(null);
			if (rate1 != null && rate2 != null) {
				result = new Money();
				targetAmount = rate2.getRate() * sourceAmount / rate1.getRate();
				result.setAmount(targetAmount);
				result.setCurrency(targetCurrency);
			}

			MomentHelper.sleep(1000);
		} catch (final Throwable oops) {
			result = null;
		}

		return result;
	}

}
