
package acme.form;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Developer extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Double						totalTrainingModulesWithUpdateMoment;
	Double						totalTrainingSessionsWithLink;

	Double						averageTimeOfTrainingModules;
	Double						deviationTimeOfTrainingModules;
	Double						minimumTimeOfTrainingModules;
	Double						maximumTimeOfTrainingModules;

}
