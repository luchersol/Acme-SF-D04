
package acme.entities.sponsorship;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.roles.Sponsor;
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
	@NotNull
	private Date				registrationTime;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				dueDate;

	@NotNull
	private Money				quantity;

	@PositiveOrZero
	@NotNull
	private Double				tax;

	@URL
	private String				link;

	// Relationships -------------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsorship			sponsorship;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsor				sponsor;

	private boolean				draftMode;

	// Derived attributes -------------------------------------------------------------


	public Money totalAmount() {
		double finalAmount = this.quantity.getAmount() + this.tax / 100 * this.quantity.getAmount();
		Money finalMoney = new Money();
		finalMoney.setAmount(Math.round(finalAmount * 100.0) / 100.0);
		finalMoney.setCurrency(this.quantity.getCurrency());
		return finalMoney;
	}

}
