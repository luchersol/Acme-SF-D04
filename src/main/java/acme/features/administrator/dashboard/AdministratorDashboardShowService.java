
package acme.features.administrator.dashboard;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.objective.PriorityObjective;
import acme.form.AdministratorForm;

@Service
public class AdministratorDashboardShowService extends AbstractService<Administrator, AdministratorForm> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AdministratorForm dashboard = new AdministratorForm();

		List<Integer> numbersOfClaims = new ArrayList<>();
		PriorityObjective highPriority = PriorityObjective.HIGH;

		Integer totalNumberAdministrator = this.repository.totalNumberAdministrator();
		Integer totalNumberAuditor = this.repository.totalNumberAuditor();
		Integer totalNumberClient = this.repository.totalNumberClient();
		Integer totalNumberDeveloper = this.repository.totalNumberDeveloper();
		Integer totalNumberManager = this.repository.totalNumberManager();
		Integer totalNumberSponsor = this.repository.totalNumberSponsor();

		Integer totalNumberNotices = this.repository.totalNumberNotices();
		Integer totalNumberNoticesWithEmailAndLink = this.repository.totalNumberNoticesWithEmailAndLink();
		Integer totalNumberObjectives = this.repository.totalNumberObjectives();
		Integer totalNumberCriticalObjetives = this.repository.totalNumberCriticalObjetives(highPriority);

		double ratioNoticesWithEmailAndLink = Math.divideExact(totalNumberNoticesWithEmailAndLink, totalNumberNotices);
		double ratioCriticalObjetives = Math.divideExact(totalNumberCriticalObjetives, totalNumberObjectives);
		double ratioNotCriticalObjetives = 1.0 - ratioCriticalObjetives;

		Double averageValueRisk = this.repository.averageValueRisk();
		Double deviationValueRisk = this.repository.deviationValueRisk();
		Double minimumValueRisk = this.repository.minimumValueRisk();
		Double maximumValueRisk = this.repository.maximumValueRisk();

		for (int week = 0; week < 10; week++) {
			Date highDate = MomentHelper.deltaFromCurrentMoment(-(week + 1), ChronoUnit.WEEKS);
			Date lowDate = MomentHelper.deltaFromCurrentMoment(-week, ChronoUnit.WEEKS);
			numbersOfClaims.add(this.repository.numberClaimsFromOneDateToOther(lowDate, highDate));
		}
		numbersOfClaims.add(this.repository.numberClaimsForMoreThan10Weeks(MomentHelper.deltaFromCurrentMoment(-10, ChronoUnit.WEEKS)));

		Double averageNumberClaimsLast10Weeks = numbersOfClaims.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
		Double maximumNumberClaimsLast10Weeks = numbersOfClaims.stream().mapToDouble(Integer::doubleValue).max().orElse(0.0);
		Double minimumNumberClaimsLast10Weeks = numbersOfClaims.stream().mapToDouble(Integer::doubleValue).min().orElse(0.0);

		Double sumaDiferenciasCuadrado = numbersOfClaims.stream().mapToDouble(num -> Math.pow(num - averageNumberClaimsLast10Weeks, 2)).sum();
		Double deviationNumberClaimsLast10Weeks = Math.sqrt(sumaDiferenciasCuadrado / numbersOfClaims.size());

		dashboard.setTotalNumberAdministrator(totalNumberAdministrator);
		dashboard.setTotalNumberAuditor(totalNumberAuditor);
		dashboard.setTotalNumberClient(totalNumberClient);
		dashboard.setTotalNumberDeveloper(totalNumberDeveloper);
		dashboard.setTotalNumberManager(totalNumberManager);
		dashboard.setTotalNumberSponsor(totalNumberSponsor);
		dashboard.setRatioNoticesWithEmailAndLink(ratioNoticesWithEmailAndLink);
		dashboard.setRatioCriticalObjetives(ratioCriticalObjetives);
		dashboard.setRatioNotCriticalObjetives(ratioNotCriticalObjetives);
		dashboard.setAverageValueRisk(averageValueRisk);
		dashboard.setDeviationValueRisk(deviationValueRisk);
		dashboard.setMinimumValueRisk(minimumValueRisk);
		dashboard.setMaximumValueRisk(maximumValueRisk);

		dashboard.setAverageNumberClaimsLast10Weeks(averageNumberClaimsLast10Weeks);
		dashboard.setMaximumNumberClaimsLast10Weeks(maximumNumberClaimsLast10Weeks);
		dashboard.setMinimumNumberClaimsLast10Weeks(minimumNumberClaimsLast10Weeks);
		dashboard.setDeviationNumberClaimsLast10Weeks(deviationNumberClaimsLast10Weeks);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AdministratorForm object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalNumberAdministrator", "totalNumberClient", "totalNumberDeveloper", "totalNumberManager", "totalNumberSponsor", "totalNumberAuditor", "ratioNoticesWithEmailAndLink", "ratioCriticalObjetives",
			"ratioNotCriticalObjetives", "averageValueRisk", "deviationValueRisk", "minimumValueRisk", "maximumValueRisk", "averageNumberClaimsLast10Weeks", "deviationNumberClaimsLast10Weeks", "minimumNumberClaimsLast10Weeks",
			"maximumNumberClaimsLast10Weeks");

		super.getResponse().addData(dataset);
	}
}
