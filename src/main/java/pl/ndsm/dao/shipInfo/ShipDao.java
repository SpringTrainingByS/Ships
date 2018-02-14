package pl.ndsm.dao.shipInfo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ndsm.model.shipInfo.Ship;

@Repository
@Transactional
public interface ShipDao extends CrudRepository<Ship, Long>{
	
	@Query(value = "SELECT id FROM ship WHERE user_id = :userId", nativeQuery = true)
	public List<BigInteger> findShipsIdsByUserId(@Param("userId") long userId);
	
	public void deleteByIdIn(List<Long> shipsIdsList);
	
	//@Query(value = "SELECT id FROM ")
 	
}
