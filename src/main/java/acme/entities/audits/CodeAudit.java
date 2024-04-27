
package acme.entities.audits;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.datatypes.AuditType;
import acme.datatypes.Mark;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CodeAudit extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//	private Project project;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Past
	private Date				execution;

	private AuditType			type;

	@NotBlank
	@Length(max = 101)
	private String				correctiveActions;

	@Transient
	private Mark mark() {
		Mark res = this.getAuditRecords().stream()
             .map(x-> x.getMark())
             .collect(Collectors.groupingBy(x-> x, Collectors.counting()))
             .entrySet().stream()
             .max(Map.Entry.comparingByValue())
             .map(Map.Entry::getKey)
             .orElse(null);
		return res;
	};

	@URL
	private String				furtherInformation;

	@OneToMany
	private List<AuditRecord>	auditRecords;

}
