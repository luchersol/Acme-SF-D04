
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.sponsorship.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select ss from Sponsorship ss where ss.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("select s from Sponsor s where s.id = :id")
	Sponsor findOneSponsorById(int id);

	@Query("select ss from Sponsorship ss where ss.sponsor.id = :id")
	Collection<Sponsorship> findManySponsorshipsBySponsor(int id);

	@Query("select i from Invoice i where i.sponsor.id = :id")
	Collection<Invoice> findManyInvoicesBySponsor(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select i from Invoice i where i.sponsorship.id = :id")
	Collection<Invoice> findManyInvoicesBySponsorshipId(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select s from Sponsorship s where s.code = :code")
	Sponsorship findOneSponsorshipByCode(String code);

	@Query("select sys from SystemConfiguration sys")
	SystemConfiguration findSystemConfiguration();

	@Query("select mr.rate from MoneyRate mr where mr.currency = :currency")
	Double findMoneyRateByMoneyCurrency(String currency);

	@Query("select count(i) = 0 from Invoice i where i.draftMode = true and i.sponsorship.id = :id")
	Boolean allInvoicesPublishedBySponsorshipId(int id);
}
