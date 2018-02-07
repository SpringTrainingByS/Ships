package pl.ndsm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ndsm.dao.StateOfShipDao;
import pl.ndsm.dao.UserDao;
import pl.ndsm.dao.shipInfo.ShipSize2Dao;
import pl.ndsm.dao.shipInfo.ShipSize3Dao;
import pl.ndsm.dao.shipInfo.ShipSize4Dao;
import pl.ndsm.dao.shipInfo.ShipSize5Dao;
import pl.ndsm.model.shipInfo.StateOfShip;
import pl.ndsm.model.shipInfo.fieldLocation.ShipContainer;
import pl.ndsm.model.userInfo.UserApp;

@Service
public class ShipService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private StateOfShipDao stateOfShipDao;
	
	@Autowired
	private ShipSize2Dao shipSize2Dao;
	@Autowired
	private ShipSize3Dao shipSize3Dao;
	@Autowired
	private ShipSize4Dao shipSize4Dao;
	@Autowired
	private ShipSize5Dao shipSize5Dao;
	
	public void addShip(ShipContainer shipContainer) throws Exception {
		
		System.out.println(shipContainer);
		
		UserApp user = userDao.findById(shipContainer.getUserId());
		StateOfShip stateOfShip = stateOfShipDao.findById(1);
		
		shipContainer.getShipSize2().getShip().setStateOfShip(stateOfShip);
		shipContainer.getShipSize3p1().getShip().setStateOfShip(stateOfShip);
		shipContainer.getShipSize3p2().getShip().setStateOfShip(stateOfShip);
		shipContainer.getShipSize4().getShip().setStateOfShip(stateOfShip);
		shipContainer.getShipSize5().getShip().setStateOfShip(stateOfShip);
		
		shipContainer.getShipSize2().getShip().setUser(user);
		shipContainer.getShipSize3p1().getShip().setUser(user);
		shipContainer.getShipSize3p2().getShip().setUser(user);
		shipContainer.getShipSize4().getShip().setUser(user);
		shipContainer.getShipSize5().getShip().setUser(user);
		
		System.out.println("Statek o rozmiarze 2: " + shipContainer.getShipSize2());
		
		try {
			shipSize2Dao.save(shipContainer.getShipSize2());
			shipSize3Dao.save(shipContainer.getShipSize3p1());
			shipSize3Dao.save(shipContainer.getShipSize3p2());
			shipSize4Dao.save(shipContainer.getShipSize4());
			shipSize5Dao.save(shipContainer.getShipSize5());
		}
		catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	
	
	
}
