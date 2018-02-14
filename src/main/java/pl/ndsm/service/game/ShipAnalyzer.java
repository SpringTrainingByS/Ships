package pl.ndsm.service.game;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ndsm.dao.shipInfo.ShipSize2Dao;
import pl.ndsm.dao.shipInfo.ShipSize3Dao;
import pl.ndsm.dao.shipInfo.ShipSize4Dao;
import pl.ndsm.dao.shipInfo.ShipSize5Dao;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize2;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize3;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize4;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize5;

@Service
public class ShipAnalyzer {

	@Autowired
	private ShipSize2Dao shipSize2Dao;
	
	@Autowired
	private ShipSize3Dao shipSize3Dao;
	
	@Autowired
	private ShipSize4Dao shipSize4Dao;
	
	@Autowired
	private ShipSize5Dao shipSize5Dao;
	
	public ShipSize2 findShipSize2(List<Long> shipsIds, String location) {
		
		System.out.println("ShipAnalyzer: shipSize2");
		
		ShipSize2 shipSize2 = shipSize2Dao.findByShipIdIn(shipsIds);
		
		if ( !(shipSize2.getField1().equals(location) || shipSize2.getField2().equals(location)) ) {
			shipSize2 = null;
		}
		
		if (shipSize2 != null) {
			System.out.println("Znaleziono statek ze strza쿮m: shipSize2");
		}
		
		return shipSize2;
	}
	
	public ShipSize3 findShipSize3(List<Long> shipsIds, String location) {
		
		System.out.println("ShipAnalyzer: shipSize3");
		
		List<ShipSize3> shipSize3List = shipSize3Dao.findByShipIdIn(shipsIds);
		ShipSize3 result = null;
		
		if ( shipSize3List.get(0).getField1().equals(location) || shipSize3List.get(0).getField2().equals(location) || shipSize3List.get(0).getField3().equals(location) ) {
			result = shipSize3List.get(0);
		}
		
		else if ( shipSize3List.get(1).getField1().equals(location) || shipSize3List.get(1).getField2().equals(location) || shipSize3List.get(1).getField3().equals(location) ) {
			result = shipSize3List.get(1);
		}
		
		if (result != null) {
			System.out.println("Znaleziono statek ze strza쿮m: shipSize3");
		}
		
		return result;
	}
	
	public ShipSize4 findShipSize4(List<Long> shipsIds, String location) {
		
		System.out.println("ShipAnalyzer: shipSize4");
		
		ShipSize4 shipSize4 = shipSize4Dao.findByShipIdIn(shipsIds);
		
		if ( !(shipSize4.getField1().equals(location) || shipSize4.getField2().equals(location) || shipSize4.getField3().equals(location) 
				|| shipSize4.getField4().equals(location)) )  {
			shipSize4 = null;
		}
		
		if (shipSize4 != null) {
			System.out.println("Znaleziono statek ze strza쿮m: shipSize4");
		}
		
		return shipSize4;
	}
	
	public ShipSize5 findShipSize5(List<Long> shipsIds, String location) {
		
		System.out.println("ShipAnalyzer: shipSize5");
		
		ShipSize5 shipSize5 = shipSize5Dao.findByShipIdIn(shipsIds);
		
		if ( !(shipSize5.getField1().equals(location) || shipSize5.getField2().equals(location) || shipSize5.getField3().equals(location) || shipSize5.getField4().equals(location)
				|| shipSize5.getField5().equals(location)) )  {
			shipSize5 = null;
		}
		
		if (shipSize5 != null) {
			System.out.println("Znaleziono statek ze strza쿮m: shipSize5");
		}

		return shipSize5;
	}
}
