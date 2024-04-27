
package acme.entities.sponsorShip;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotBlank
	private Date				registrationTime;

	@Temporal(TemporalType.TIMESTAMP)
	@NotBlank
	private Date				date;

	@PositiveOrZero
	private int					quantity;

	@PositiveOrZero
	private double				tax;

	@URL
	private String				link;

	// Derived attributes -------------------------------------------------------------


	public double totalAmount() {
		return this.quantity + this.tax / 100 * this.quantity;
	}

}
