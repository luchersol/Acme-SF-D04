
package acme.entities.sponsorShip;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.datatypes.TypeOfSponsorship;
import acme.entities.project.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SponsorShips extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotBlank
	private Date				moment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotBlank
	private Date				startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotBlank
	private Date				endDate;

	@PositiveOrZero
	private int					amount;

	private TypeOfSponsorship	typeOfSponsorship;

	@Email
	private String				contactEmail;

	@URL
	private String				link;

	// Relationships -------------------------------------------------------------

	@ManyToOne(optional = false)
	private Project				project;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private Invoice				invoice;

}
