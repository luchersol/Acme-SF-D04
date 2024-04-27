
package acme.form;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerForm extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalNumberProjectMust;
	Integer						totalNumberProjectShould;
	Integer						totalNumberProjectCould;
	Integer						totalNumberProjectWont;

	Double						averageEstimatedCostUserStories;
	Double						deviationEstimatedCostUserStories;
	Double						minimumEstimatedCostUserStories;
	Double						maximumEstimatedCostUserStories;

	Double						averageEstimatedCostProjects;
	Double						deviationEstimatedCostProjects;
	Double						minimumEstimatedCostProjects;
	Double						maximumEstimatedCostProjects;

}
