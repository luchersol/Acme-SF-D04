
package acme.form;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;

public class SponsorForm extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						totalInvoicesWithTaxLessThanOrEqualTo21;

	Integer						totalSponsorshipWithLink;

	Money						averageAmountOfTheSponsorships;
	Money						deviationAmountOfTheSponsorships;
	Money						minimumAmountOfTheSponsorships;
	Money						maximumAmountOfTheSponsorships;

	Double						averageQuantityOfTheInvoices;
	Double						deviationQuantityOfTheInvoices;
	Double						minimumQuantityOfTheInvoices;
	Double						maximumQuantityOfTheInvoices;
}
