
package acme.entities.project;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStory extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@NotNull
	@Positive
	private Double				estimatedCost; //Representa horas

	@NotBlank
	@Length(max = 100)
	private String				acceptanceCriteria;

	@URL
	private String				link;

	@NotNull
	private Priority			priority;

	@Valid
	@ManyToOne(optional = false)
	private Manager				manager;

	@Valid
	@ManyToOne(optional = false)
	private Project				project;

}
