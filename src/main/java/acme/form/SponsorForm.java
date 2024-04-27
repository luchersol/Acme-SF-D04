
package acme.form;

import acme.client.data.AbstractForm;

public class SponsorForm extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Double						totalInvoicesWithTaxLessThanOrEqualTo21;

	Double						totalSponsorshipWithLink;

	Double						averageAmountOfTheSponsorships;
	Double						deviationAmountOfTheSponsorships;
	Double						minimumAmountOfTheSponsorships;
	Double						maximumAmountOfTheSponsorships;

	Double						averageQuantityOfTheInvoices;
	Double						deviationQuantityOfTheInvoices;
	Double						minimumQuantityOfTheInvoices;
	Double						maximumQuantityOfTheInvoices;
}
