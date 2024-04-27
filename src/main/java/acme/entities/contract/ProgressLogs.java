
package acme.entities.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ProgressLogs extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "PG-[A-Z]{1,2}-[0-9]{4}")
	private String				record_id;

	@Positive
	@NotNull
	private Integer				completeness;

	@NotBlank
	@Length(max = 100)
	private String				comment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registration_moment;

	@NotBlank
	@Length(max = 75)
	private String				responsible_person;

	@NotNull
	@ManyToOne
	private Contract			contract;

}
