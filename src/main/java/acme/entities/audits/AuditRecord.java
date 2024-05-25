
package acme.entities.audits;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(indexes = {
	@Index(columnList = "code"), @Index(columnList = "id, draftMode"), @Index(columnList = "code_audit_id, draftMode")
})
public class AuditRecord extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	private Boolean				draftMode;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3}")
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startDate;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endDate;

	@NotNull
	private Mark				mark;

	@URL
	private String				link;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	private CodeAudit			codeAudit;

}
