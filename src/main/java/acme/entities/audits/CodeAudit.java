
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

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.project.Project;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "code"), @Index(columnList = "id, draftMode"), // 
	@Index(columnList = "type, auditor_id,draftMode")
})
public class CodeAudit extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	private Boolean				draftMode;

	@Valid
	@ManyToOne(optional = false)
	@NotNull
	Auditor						auditor;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				execution;

	@NotNull
	private AuditType			type;

	@NotBlank
	@Length(max = 100)
	private String				correctiveActions;

	private Mark				mark;

	@URL
	private String				link;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	private Project				project;

}
