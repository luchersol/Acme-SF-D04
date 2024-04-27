
package acme.entities.training;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingSession extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "TS-[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@Size(min = 7, max = Integer.MAX_VALUE)
	private Date				timeStart;

	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@Size(min = 7, max = Integer.MAX_VALUE)
	private Date				timeEnd;

	@NotBlank
	@Size(max = 75)
	private String				location;

	@NotBlank
	@Size(max = 75)
	private String				instructor;

	@NotBlank
	@Email
	private String				contactEmail;

	@URL
	private String				furtherInformationLink;

	@NotNull
	@ManyToOne(optional = false)
	private TrainingModule		trainingModule;

}
