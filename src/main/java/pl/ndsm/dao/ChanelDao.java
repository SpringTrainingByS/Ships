package pl.ndsm.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.ndsm.model.userInfo.Chanel;

@Repository
public interface ChanelDao extends CrudRepository<Chanel, Long> {
	public Chanel save(Chanel websocket);
	
	@Query(value = "SELECT name FROM chanel WHERE user_id = :userId", nativeQuery = true)
	public String findChanelNameByUserId(@Param("userId") Long id);
}
