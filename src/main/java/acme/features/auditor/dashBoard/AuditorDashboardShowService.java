
package acme.features.auditor.dashBoard;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditType;
import acme.form.AuditorForm;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorForm> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditorForm dashboard;

		int auditorId;
		auditorId = this.getRequest().getPrincipal().getActiveRoleId();

		Integer totalStaticCodeAudits;
		Integer totalDynamicCodeAudits;

		Double averageAuditRecordsInCodeAudits;
		Double deviationAuditRecordsInCodeAudits;
		Integer minimumAuditRecordsInCodeAudits;
		Integer maximumAuditRecordsInCodeAudits;

		Double averagePeriodOfAuditRecordsInCodeAudits;
		Double deviationPeriodOfAuditRecordsInCodeAudits;
		Double minimumPeriodOfAuditRecordsInCodeAudits;
		Double maximumPeriodOfAuditRecordsInCodeAudits;

		totalStaticCodeAudits = this.repository.totalStaticOrDynamicCodeAudits(auditorId, AuditType.STATIC);

		totalDynamicCodeAudits = this.repository.totalStaticOrDynamicCodeAudits(auditorId, AuditType.DYNAMIC);

		averageAuditRecordsInCodeAudits = this.repository.averageAuditRecordsInCodeAudits(auditorId);

		deviationAuditRecordsInCodeAudits = this.repository.deviationAuditRecordsInCodeAudits(auditorId);

		minimumAuditRecordsInCodeAudits = this.repository.minAuditRecordsInCodeAudits(auditorId);

		maximumAuditRecordsInCodeAudits = this.repository.maxAuditRecordsInCodeAudits(auditorId);

		List<Object[]> dates = this.repository.findStartAndEndDates(auditorId);
		List<Double> periodsList = dates.stream().mapToDouble(date -> {
			Timestamp end = (Timestamp) date[0];
			Timestamp start = (Timestamp) date[1];
			return (end.getTime() - start.getTime()) / 3600000.0;
		}).boxed().toList();

		averagePeriodOfAuditRecordsInCodeAudits = periodsList.stream().mapToDouble(x -> x).average().orElse(Double.NaN);

		double mean = averagePeriodOfAuditRecordsInCodeAudits != Double.NaN ? averagePeriodOfAuditRecordsInCodeAudits : 0.;
		double variance = periodsList.stream().mapToDouble(x -> x).map(val -> Math.pow(val - mean, 2)).average().orElse(Double.NaN);

		Double dev = variance != Double.NaN ? Math.sqrt(variance) : null;

		deviationPeriodOfAuditRecordsInCodeAudits = dev;

		minimumPeriodOfAuditRecordsInCodeAudits = periodsList.stream().mapToDouble(x -> x).min().orElse(Double.NaN);

		maximumPeriodOfAuditRecordsInCodeAudits = periodsList.stream().mapToDouble(x -> x).max().orElse(Double.NaN);

		dashboard = new AuditorForm();

		dashboard.setTotalStaticCodeAudits(totalStaticCodeAudits);
		dashboard.setTotalDynamicCodeAudits(totalDynamicCodeAudits);

		dashboard.setAverageAuditRecordsInCodeAudits(averageAuditRecordsInCodeAudits);
		dashboard.setDeviationAuditRecordsInCodeAudits(deviationAuditRecordsInCodeAudits);
		dashboard.setMaximumAuditRecordsInCodeAudits(maximumAuditRecordsInCodeAudits);
		dashboard.setMinimumAuditRecordsInCodeAudits(minimumAuditRecordsInCodeAudits);

		dashboard.setAveragePeriodOfAuditRecordsInCodeAudits(averagePeriodOfAuditRecordsInCodeAudits);
		dashboard.setDeviationPeriodOfAuditRecordsInCodeAudits(deviationPeriodOfAuditRecordsInCodeAudits);
		dashboard.setMaximumPeriodOfAuditRecordsInCodeAudits(maximumPeriodOfAuditRecordsInCodeAudits);
		dashboard.setMinimumPeriodOfAuditRecordsInCodeAudits(minimumPeriodOfAuditRecordsInCodeAudits);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AuditorForm object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalStaticCodeAudits", "totalDynamicCodeAudits", // 
			"averageAuditRecordsInCodeAudits", "deviationAuditRecordsInCodeAudits", //
			"minimumAuditRecordsInCodeAudits", "maximumAuditRecordsInCodeAudits", //
			"averagePeriodOfAuditRecordsInCodeAudits", "deviationPeriodOfAuditRecordsInCodeAudits", //
			"minimumPeriodOfAuditRecordsInCodeAudits", "maximumPeriodOfAuditRecordsInCodeAudits");

		super.getResponse().addData(dataset);
	}

}
