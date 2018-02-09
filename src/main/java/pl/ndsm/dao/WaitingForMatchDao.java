package pl.ndsm.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ndsm.model.matchInfo.WaitingForMatch;

@Repository
@Transactional
public interface WaitingForMatchDao extends CrudRepository<WaitingForMatch, Long> {
	
	@Modifying
	@Query(value = "INSERT INTO waiting_for_match (user_id) VALUES (:userId)", nativeQuery = true)
	@Transactional
	public void saveUserId(@Param("userId") Long userId);
	
	@Query(value = "SELECT user_id FROM waiting_for_match LIMIT 1", nativeQuery = true)
	public long selectFirstWaitingUserId();
	
	public void deleteByUserId(long userId);

}
