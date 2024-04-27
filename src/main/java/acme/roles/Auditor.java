
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Auditor extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@NotNull
	@Length(max = 75)
	private String				firm;

	@NotBlank
	@NotNull
	@Length(max = 25)
	private String				professionalId;

	@NotBlank
	@NotNull
	@Length(max = 100)
	private String				certifications;

	@URL
	private String				furtherInformation;

}
