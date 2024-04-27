
package acme.components;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.helpers.MomentHelper;
import acme.client.helpers.RandomHelper;
import acme.client.repositories.AbstractRepository;
import acme.entities.banner.Banner;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("select count(b) from Banner b where b.displayStart <= :actualDate and b.displayEnd >= :actualDate")
	int countBanners(Date actualDate);

	@Query("select b from Banner b where b.displayStart <= :actualDate and b.displayEnd >= :actualDate")
	List<Banner> findManyBanners(PageRequest pageRequest, Date actualDate);

	default Banner findRandomBanner() {
		Banner result;
		int count, index;
		PageRequest page;
		List<Banner> list;
		Date now = MomentHelper.getCurrentMoment();
		count = this.countBanners(now);
		if (count == 0)
			result = null;
		else {
			index = RandomHelper.nextInt(0, count);

			page = PageRequest.of(index, 1, Sort.by(Direction.ASC, "id"));
			list = this.findManyBanners(page, now);
			result = list.isEmpty() ? null : list.get(0);
		}

		return result;
	}

}
