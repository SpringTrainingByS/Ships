package pl.ndsm.dao.gameInfo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ndsm.model.gameInfo.ReadinessToGame;

@Repository
@Transactional
public interface ReadinessToGameDao extends CrudRepository<ReadinessToGame, Long> {
	public ReadinessToGame findByMatchInfoId(long id);
	
	@Query(value = "SELECT COUNT(*) FROM readiness_to_game WHERE match_info_id = :matchInfoId", nativeQuery = true)
	public int countReadinessForGame(@Param("matchInfoId") long matchInfoId);
	
	@Modifying
	@Query(value = "INSERT INTO readiness_to_game (match_info_id, user_id) VALUES (:matchInfoId, :userId)", nativeQuery = true)
	public void insert(@Param("matchInfoId") long matchInfoId, @Param("userId") long userId); 
	
	public void deleteByMatchInfoId(long matchInfoId);
}
