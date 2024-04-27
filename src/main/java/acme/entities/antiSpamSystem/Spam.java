
package acme.entities.antiSpamSystem;

import javax.persistence.Entity;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Spam extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	private String				word;

}
