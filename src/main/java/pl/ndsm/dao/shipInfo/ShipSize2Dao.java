package pl.ndsm.dao.shipInfo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ndsm.model.shipInfo.fieldLocation.ShipSize2;

@Repository
@Transactional
public interface ShipSize2Dao extends CrudRepository<ShipSize2, Long> {
	
	public ShipSize2 findByShipIdIn(List<Long> shipsIds);
	
	public void deleteByShipIdIn(List<Long> shipIdsList);

}
