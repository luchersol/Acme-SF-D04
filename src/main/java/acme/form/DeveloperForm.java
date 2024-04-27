
package acme.form;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperForm extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						totalTrainingModulesWithUpdateMoment;
	Integer						totalTrainingSessionsWithLink;

	Double						averageTimeOfTraining;
	Double						deviationTimeOfTraining;
	Double						minimumTimeOfTraining;
	Double						maximumTimeOfTraining;

}
