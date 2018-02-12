package pl.ndsm.dao.shipInfo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pl.ndsm.model.shipInfo.Ship;

public interface ShipDao extends CrudRepository<Ship, Long>{
	
	@Query(value = "SELECT id FROM ship WHERE user_id = :userId", nativeQuery = true)
	public List<BigInteger> findShipsIdsByUserId(@Param("userId") long userId);
	
	//@Query(value = "SELECT id FROM ")
 	
}
