
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Developer extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Size(max = 75)
	private String				degree;

	@NotBlank
	@Size(max = 100)
	private String				specialisation;

	@NotBlank
	@Size(max = 100)
	private String				skills;

	@NotBlank
	@Email
	private String				email;

	@URL
	private String				link;

}
