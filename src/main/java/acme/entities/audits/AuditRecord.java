
package acme.entities.audits;

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

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class AuditRecord extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3}")
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startDate;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endDate;

	private Mark				mark;

	@URL
	private String				link;

	@ManyToOne
	@Valid
	private CodeAudit			codeAudit;

}
