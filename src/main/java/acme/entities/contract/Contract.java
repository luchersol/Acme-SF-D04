
package acme.entities.contract;

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

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.project.Project;
import acme.roles.Client;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "code"), @Index(columnList = "draftMode, project_id"), @Index(columnList = "code, id")
})
public class Contract extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	@NotBlank
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	private String				providerName;

	@NotBlank
	@Length(max = 75)
	private String				customerName;

	@NotBlank
	@Length(max = 100)
	private String				goal;

	@Valid
	@NotNull
	private Money				budget;

	@NotNull
	private Boolean				draftMode;

	@Valid
	@NotNull
	@ManyToOne(optional = false)

	private Project				project;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	private Client				client;
}
