package pl.ndsm.dao;

import org.springframework.data.repository.CrudRepository;

import pl.ndsm.model.shipInfo.StateOfShip;

public interface StateOfShipDao extends CrudRepository<StateOfShip, Long> {
	public StateOfShip findById(long id);

}
