
package acme.entities.notice;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notice extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	private String				username;

	@NotBlank
	@Length(max = 100)
	private String				message;

	@URL
	private String				link;

	@NotBlank
	private String				name;

	@NotBlank
	private String				surname;

	@Email
	private String				email;

	// Derived attributes -------------------------------------------------------------


	@Transient
	public String getFullName() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(this.surname);
		result.append(", ");
		result.append(this.name);

		return result.toString();
	}

	@Length(max = 75)
	public String author() {
		return this.username + " - " + this.getFullName();
	}

}
