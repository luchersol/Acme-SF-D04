
package acme.form;

import java.util.List;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorForm extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalInvoicesWithTaxLessThanOrEqualTo21;

	Integer						totalSponsorshipWithLink;

	List<Money>					averageAmountOfTheSponsorships;
	List<Money>					deviationAmountOfTheSponsorships;
	List<Money>					minimumAmountOfTheSponsorships;
	List<Money>					maximumAmountOfTheSponsorships;

	List<Money>					averageQuantityOfTheInvoices;
	List<Money>					deviationQuantityOfTheInvoices;
	List<Money>					minimumQuantityOfTheInvoices;
	List<Money>					maximumQuantityOfTheInvoices;
}
