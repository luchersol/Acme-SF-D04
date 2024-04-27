
package acme.entities.antiSpamSystem;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.Range;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AntiSpamSystem extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Range(min = 0, max = 1)
	private Double				threshold;

}
