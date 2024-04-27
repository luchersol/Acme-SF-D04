
package acme.roles;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import acme.datatypes.SponsorType;
import acme.entities.sponsorShip.Invoice;
import acme.entities.sponsorShip.SponsorShips;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsor extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@PositiveOrZero
	private double				tax;

	@URL
	private String				link;

	private SponsorType			amountOfSponsorships;

	private SponsorType			quantityOfInvoices;

	// Relationships ----------------------------------------------------------

	@ManyToMany
	private Invoice				invoices;

	@ManyToMany
	private SponsorShips		sponsorships;

}
