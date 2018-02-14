package pl.ndsm.dao.shipInfo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ndsm.model.shipInfo.fieldLocation.ShipSize4;

@Repository
@Transactional
public interface ShipSize4Dao extends CrudRepository<ShipSize4, Long>{
	
	public ShipSize4 findByShipIdIn(List<Long> shipsIds);
	public void deleteByShipIdIn(List<Long> shipIdsList);
	
}
