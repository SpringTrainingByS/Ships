package pl.ndsm.dao.matchInfo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ndsm.model.matchInfo.MatchInfo;

@Repository 
@Transactional
public interface MatchDao extends CrudRepository<MatchInfo, Long> {

	@Query(value = "INSERT INTO match_info  VALUES (null, :userId1, :userId2)", nativeQuery = true)
	public void addMatchInfo(@Param("userId1") long userId1, @Param("userId2") long userId2);
	
	@Query(value = "DELETE FROM match_info WHERE user_id_1 = :userId1 AND user_id_2 = :userId2", nativeQuery = true)
	public void deleteByUsersIds(@Param("userId1") long userId1,@Param("userId2") long userId2);
	
}
