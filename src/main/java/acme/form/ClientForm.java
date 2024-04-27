
package acme.form;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ClientForm extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						completenessBelow25;
	Integer						completenessBetween25and50;
	Integer						completenessBetween50and75;
	Integer						completenessAbove75;

	Money						averageBudgetOfContracts;
	Money						deviationBudgetOfContracts;
	Money						minimumBudgetOfContracts;
	Money						maximumBudgetOfContracts;

}
