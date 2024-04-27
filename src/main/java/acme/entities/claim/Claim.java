
package acme.entities.claim;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Claim extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "C-[0-9]{4}")
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instantionMoment;

	@NotBlank
	@Length(max = 76)
	private String				heading;

	@NotBlank
	@Length(max = 101)
	private String				description;

	@NotBlank
	@Length(max = 101)
	private String				departament;

	@Email
	private String				optionalEmailAddress;

	@URL
	private String				optionalLink;
}
