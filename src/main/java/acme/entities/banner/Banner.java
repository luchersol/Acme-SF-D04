
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(indexes = {
	@Index(columnList = "displayStart, displayEnd")
})
public class Banner extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instanciationOrUpdateMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				displayStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				displayEnd;

	@URL
	@NotBlank
	private String				image;

	@NotBlank
	@Length(max = 75)
	private String				slogan;

	@URL
	@NotBlank
	private String				link;

}
