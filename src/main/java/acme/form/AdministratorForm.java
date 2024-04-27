
package acme.form;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorForm extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						totalNumberAdministrator;
	Integer						totalNumberAuditor;
	Integer						totalNumberClient;
	Integer						totalNumberConsumer;
	Integer						totalNumberDeveloper;
	Integer						totalNumberManager;
	Integer						totalNumberProvider;

	Double						ratioNoticesWithEmailAndLink;
	Double						ratioCriticalObjetives;
	Double						ratioNotCriticalObjetives;

	Double						averageValueRisk;
	Double						deviationValueRisk;
	Double						minimumValueRisk;
	Double						maximumValueRisk;

	Double						averageNumberClaimsLast10Weeks;
	Double						deviationNumberClaimsLast10Weeks;
	Double						minimumNumberClaimsLast10Weeks;
	Double						maximumNumberClaimsLast10Weeks;

}
