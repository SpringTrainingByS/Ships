package pl.ndsm.dao.shipInfo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ndsm.model.shipInfo.fieldLocation.ShipSize3;

@Repository
@Transactional
public interface ShipSize3Dao extends CrudRepository<ShipSize3, Long>{

	public List<ShipSize3> findByShipIdIn(List<Long> shipsIds);
	public void deleteByShipIdIn(List<Long> shipIdsList);

}
