package pl.ndsm.dao.shipInfo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ndsm.model.shipInfo.fieldLocation.ShipSize5;

@Repository
@Transactional
public interface ShipSize5Dao extends CrudRepository<ShipSize5, Long> {

	public ShipSize5 findByShipIdIn(List<Long> shipsIds);
	public void deleteByShipIdIn(List<Long> shipIdsList);
	
}
