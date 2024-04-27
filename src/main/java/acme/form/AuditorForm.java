
package acme.form;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorForm extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						totalStaticCodeAudits;
	Integer						totalDynamicCodeAudits;

	Double						averageAuditRecordsInCodeAudits;
	Double						deviationAuditRecordsInCodeAudits;
	Integer						minimumAuditRecordsInCodeAudits;
	Integer						maximumAuditRecordsInCodeAudits;

	Double						averagePeriodOfAuditRecordsInCodeAudits;
	Double						deviationPeriodOfAuditRecordsInCodeAudits;
	Double						minimumPeriodOfAuditRecordsInCodeAudits;
	Double						maximumPeriodOfAuditRecordsInCodeAudits;

}
