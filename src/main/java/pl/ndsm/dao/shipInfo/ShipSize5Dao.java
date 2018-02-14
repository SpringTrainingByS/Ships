package pl.ndsm.dao.shipInfo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pl.ndsm.model.shipInfo.fieldLocation.ShipSize2;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize4;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize5;

public interface ShipSize5Dao extends CrudRepository<ShipSize5, Long> {

	public ShipSize5 findByShipIdIn(List<Long> shipsIds);
	public void deleteByShipIdIn(List<Long> shipIdsList);
	
}
