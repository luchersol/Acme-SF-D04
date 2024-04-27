
package acme.features.administrator.dashboard;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.claim.Claim;
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

		Date date = MomentHelper.deltaFromCurrentMoment(-10, ChronoUnit.DAYS);
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

		double ratioNoticesWithEmailAndLink = totalNumberNoticesWithEmailAndLink / totalNumberNotices;
		double ratioCriticalObjetives = totalNumberCriticalObjetives / totalNumberObjectives;
		double ratioNotCriticalObjetives = 1.0 - ratioCriticalObjetives;

		Double averageValueRisk = this.repository.averageValueRisk();
		Double deviationValueRisk = this.repository.deviationValueRisk();
		Double minimumValueRisk = this.repository.minimumValueRisk();
		Double maximumValueRisk = this.repository.maximumValueRisk();

		Collection<Claim> claims = this.repository.averageNumberClaimsLast10Weeks(date); // TODO

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

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AdministratorForm object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalNumberAdministrator", "totalNumberClient", "totalNumberDeveloper", "totalNumberManager", "totalNumberSponsor", "totalNumberAuditor", "ratioNoticesWithEmailAndLink", "ratioCriticalObjetives",
			"ratioNotCriticalObjetives", "averageValueRisk", "deviationValueRisk", "minimumValueRisk", "maximumValueRisk");

		super.getResponse().addData(dataset);
	}
}
