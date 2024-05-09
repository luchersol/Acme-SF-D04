
package acme.entities.systemConfiguration;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "[A-Z]{3}")
	private String				systemCurrency;

	@NotBlank
	@Pattern(regexp = "[A-Z]{3}(,[A-Z]{3})*")
	private String				acceptedCurrencies;

	private Date				updateMoment;

}
