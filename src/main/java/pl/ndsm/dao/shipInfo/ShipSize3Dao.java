package pl.ndsm.dao.shipInfo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pl.ndsm.model.shipInfo.fieldLocation.ShipSize2;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize3;

public interface ShipSize3Dao extends CrudRepository<ShipSize3, Long>{

	public List<ShipSize3> findByShipIdIn(List<Long> shipsIds);
	public void deleteByShipIdIn(List<Long> shipIdsList);

}
