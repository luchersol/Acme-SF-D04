
package acme.entities.risk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.accounts.Administrator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Risk extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "R-[0-9]{3}")
	private String				reference;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				identificationDate;

	@NotNull
	@Positive
	private Double				impact;

	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
	@DecimalMax(value = "1.0", inclusive = true)
	private Double				probability;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@URL
	private String				link;


	@Transient
	public Double getValue() {
		return this.probability * this.impact;
	}


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Administrator administrator;

}
