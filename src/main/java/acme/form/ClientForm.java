
package acme.form;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ClientForm extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Double						totalProgressLogs;

	Double						completenessBelow25;
	Double						completenessBetween25and50;
	Double						completenessBetween50and75;
	Double						completenessAbove75;
	Double						averageBudgetOfContracts;
	Double						deviationBudgetOfContracts;
	Double						minimumBudgetOfContracts;
	Double						maximumBudgetOfContracts;

}
