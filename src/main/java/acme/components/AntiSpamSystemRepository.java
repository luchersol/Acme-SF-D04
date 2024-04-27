
package acme.components;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface AntiSpamSystemRepository extends AbstractRepository {

	@Query("select s.word from Spam s")
	List<String> findAllSpam();

}
