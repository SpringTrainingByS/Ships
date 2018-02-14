package pl.ndsm.dao.matchInfo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ndsm.mapObject.UsersIds;
import pl.ndsm.model.matchInfo.PlayerTurn;

@Repository
@Transactional
public interface PlayerTurnDao extends CrudRepository<PlayerTurn, Long> {
	
	@Modifying
	@Query(value = "INSERT INTO player_turn (match_info_id, user_id) VALUES (:matchInfoId, :userId)", nativeQuery = true)
	public void insert(@Param("matchInfoId") long matchInfoId,@Param("userId") long userId);
	
	@Modifying
	@Query(value = "UPDATE player_turn SET user_id = :userId WHERE match_info_id = :matchInfoId", nativeQuery = true)
	public void updateByMatchId(@Param("matchInfoId") long matchInfoId, @Param("userId") long userId);
	
	@Query(value = "SELECT match_info_id FROM player_turn WHERE user_id = :userId", nativeQuery = true)
	public Long findMatchInfoIdByUserId(@Param("userId") long userId);
	
	public void deleteByMatchInfoId(long matchInfoId);
	
}
