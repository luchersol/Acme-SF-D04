
package acme.entities.moneyExchangeConfiguration;

import java.util.Date;

import javax.persistence.Entity;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MoneyExchangeConfiguration extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	private Date				updateMoment;
}
