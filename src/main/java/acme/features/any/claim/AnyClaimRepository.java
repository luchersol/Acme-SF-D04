
package acme.features.any.claim;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.claim.Claim;

@Repository
public interface AnyClaimRepository extends AbstractRepository {

	@Query("select c from Claim c where c.id = :id")
	Claim findOneClaimById(int id);

	@Query("select c from Claim c")
	Collection<Claim> findAllClaims();

	@Query("select count(c) > 0 from Claim c where c.code = :code")
	Boolean existsByCode(String code);

}
