
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

	private Integer				totalNumberProjectMust;
	private Integer				totalNumberProjectShould;
	private Integer				totalNumberProjectCould;
	private Integer				totalNumberProjectWont;

	private Double				averageEstimatedCostUserStories;
	private Double				deviationEstimatedCostUserStories;
	private Double				minimumEstimatedCostUserStories;
	private Double				maximumEstimatedCostUserStories;

	private List<Money>			averageEstimatedCostProjects;
	private List<Money>			deviationEstimatedCostProjects;
	private List<Money>			minimumEstimatedCostProjects;
	private List<Money>			maximumEstimatedCostProjects;

}
