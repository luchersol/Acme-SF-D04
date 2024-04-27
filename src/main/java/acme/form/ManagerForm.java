
package acme.form;

import java.util.List;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
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

	List<Money>					averageEstimatedCostProjects;
	List<Money>					deviationEstimatedCostProjects;
	List<Money>					minimumEstimatedCostProjects;
	List<Money>					maximumEstimatedCostProjects;

}
