
package acme.form;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Auditor extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Double						totalStaticCodeAudits;
	Double						averageAuditRecordsForStaticCodeAudits;
	Double						deviationAuditRecordsForStaticCodeAudits;
	Double						minimumAuditRecordsForStaticCodeAudits;
	Double						maximumAuditRecordsForStaticCodeAudits;

	Double						averagePeriodOfAuditRecordsForStaticCodeAudits;
	Double						deviationPeriodOfAuditRecordsForStaticCodeAudits;
	Double						minimumPeriodOfAuditRecordsForStaticCodeAudits;
	Double						maximumPeriodOfAuditRecordsForStaticCodeAudits;

	Double						totalDynamicCodeAudits;
	Double						averageAuditRecordsForDynamicCodeAudits;
	Double						deviationAuditRecordsForDynamicCodeAudits;
	Double						minimumAuditRecordsForDynamicCodeAudits;
	Double						maximumAuditRecordsForDynamicCodeAudits;

	Double						averagePeriodOfAuditRecordsForDynamicCodeAudits;
	Double						deviationPeriodOfAuditRecordsForDynamicCodeAudits;
	Double						minimumPeriodOfAuditRecordsForDynamicCodeAudits;
	Double						maximumPeriodOfAuditRecordsForDynamicCodeAudits;

}
