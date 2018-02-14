package pl.ndsm.service.game;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ndsm.conf.ShotResult;
import pl.ndsm.dao.matchInfo.QuantitySunkDao;
import pl.ndsm.dao.shipInfo.ShipSize2Dao;
import pl.ndsm.dao.shipInfo.ShipSize3Dao;
import pl.ndsm.dao.shipInfo.ShipSize4Dao;
import pl.ndsm.dao.shipInfo.ShipSize5Dao;
import pl.ndsm.model.matchInfo.QuantitySunk;
import pl.ndsm.model.shipInfo.Ship;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize2;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize3;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize4;
import pl.ndsm.model.shipInfo.fieldLocation.ShipSize5;

@Service
public class ShotAnalyzer {
	
	@Autowired
	private ShipSize2Dao shipSize2Dao;
	
	@Autowired
	private ShipSize3Dao shipSize3Dao;
	
	@Autowired
	private ShipSize4Dao shipSize4Dao;
	
	@Autowired
	private ShipSize5Dao shipSize5Dao;
	
	@Autowired 
	private QuantitySunkDao quantitySunkDao;
	
	@Autowired
	private ShipAnalyzer shipAnalayzer;
	
	private ShotResult shotResult;
	
	public ShotResult checkShot(String location, List<Long> shipsIds, long userId) {
		
		System.out.println("ShotAnalyzer: checkShot");
		
		shotResult = ShotResult.MISSED;
		
		ShipSize shipSize = findProperShip(location, shipsIds);
		
		if (shipSize != null) {
			changeStateOfShip(shipSize, location, userId);
		}
		else {
			System.out.println("Nie znaleziono ¿adnych strza³ów dla statków");
		}
		
		return shotResult;
	}
	
	public ShipSize findProperShip(String location, List<Long> shipsIds) {
		
		System.out.println("ShotAnalyzer: findProperShip");
		System.out.println("Location: " + location);
		
		ShipSize shipSize = null;
		
		ShipSize2 shipSize2 = shipAnalayzer.findShipSize2(shipsIds, location);
		
		if (shipSize2 != null) {
			System.out.println("Trafiony statek to shipSize2");
			return shipSize2;
		}
		
		ShipSize3 shipSize3 = shipAnalayzer.findShipSize3(shipsIds, location);
		
		if (shipSize3 != null) {
			System.out.println("Trafiony statek to shipSize3");
			return shipSize3;
		}
		
		
		ShipSize4 shipSize4 = shipAnalayzer.findShipSize4(shipsIds, location);
		
		if (shipSize4 != null) {
			System.out.println("Trafiony statek to shipSize4");
			return shipSize4;
		}
		
		ShipSize5 shipSize5 = shipAnalayzer.findShipSize5(shipsIds, location);
		
		if (shipSize5 != null) {
			System.out.println("Trafiony statek to shipSize5");
			return shipSize5;
		}
		
		System.out.println("Nie trafi³o ¿adnego statku.");
		
		return shipSize;
	}
	
	public void changeStateOfShip(ShipSize shipSize, String location, long userId) {
		
		System.out.println("ShotAnalyzer: changeStateOfShip");
		
		if (shipSize instanceof ShipSize2) {
			
			System.out.println("Zmieniam stan dla statku o klasie shipSize2");
			
			if (((ShipSize2) shipSize).getField1().equals(location)) {
				((ShipSize2) shipSize).setField1("");
			}
			else if (((ShipSize2) shipSize).getField2().equals(location)) {
				((ShipSize2) shipSize).setField2("");
			}
			
			Ship ship = ((ShipSize2) shipSize).getShip();
			int shotCount = ship.getShotCount();
			shotCount++;
			ship.setShotCount(shotCount);
			((ShipSize2) shipSize).setShip(ship);
			
			if (shotCount == ship.getSize()) {
				
				shotResult = ShotResult.DESTROYED;
				QuantitySunk quantitySunk = quantitySunkDao.findByUserId(userId);
				int counter = quantitySunk.getCounter();
				counter++;
				
				if (counter == 5) {
					shotResult = ShotResult.WIN;
				}
				else {
					quantitySunk.setCounter(counter);
					quantitySunkDao.save(quantitySunk);
				}
			}
			else {
				shotResult = ShotResult.HITTED;
			}
			
			((ShipSize2) shipSize).setShip(ship);
			shipSize2Dao.save((ShipSize2) shipSize);
			
		}
		else if (shipSize instanceof ShipSize3) {
			
			System.out.println("Zmieniam stan dla statku o klasie shipSize3");
			
			if (((ShipSize3) shipSize).getField1().equals(location)) {
				((ShipSize3) shipSize).setField1("");
			}
			else if (((ShipSize3) shipSize).getField2().equals(location)) {
				((ShipSize3) shipSize).setField2("");
			}
			else if (((ShipSize3) shipSize).getField3().equals(location)) {
				((ShipSize3) shipSize).setField3("");
			}
			
			Ship ship = ((ShipSize3) shipSize).getShip();
			int shotCount = ship.getShotCount();
			shotCount++;
			ship.setShotCount(shotCount);
			((ShipSize3) shipSize).setShip(ship);
			
			if (shotCount == ship.getSize()) {
				
				shotResult = ShotResult.DESTROYED;
				QuantitySunk quantitySunk = quantitySunkDao.findByUserId(userId);
				int counter = quantitySunk.getCounter();
				counter++;
				
				if (counter == 5) {
					shotResult = ShotResult.WIN;
				}
				else {
					quantitySunk.setCounter(counter);
					quantitySunkDao.save(quantitySunk);
				}
			}
			else {
				shotResult = ShotResult.HITTED;
			}
			
			((ShipSize3) shipSize).setShip(ship);
			shipSize3Dao.save((ShipSize3) shipSize);
			
		}
		else if (shipSize instanceof ShipSize4) {
			
			System.out.println("Zmieniam stan dla statku o klasie shipSize4");
			
			if (((ShipSize4) shipSize).getField1().equals(location)) {
				((ShipSize4) shipSize).setField1("");
			}
			else if (((ShipSize4) shipSize).getField2().equals(location)) {
				((ShipSize4) shipSize).setField2("");
			}
			else if (((ShipSize4) shipSize).getField3().equals(location)) {
				((ShipSize4) shipSize).setField3("");
			}
			else if (((ShipSize4) shipSize).getField4().equals(location)) {
				((ShipSize4) shipSize).setField4("");
			}
			
			Ship ship = ((ShipSize4) shipSize).getShip();
			int shotCount = ship.getShotCount();
			shotCount++;
			ship.setShotCount(shotCount);
			((ShipSize4) shipSize).setShip(ship);
			
			if (shotCount == ship.getSize()) {
				
				shotResult = ShotResult.DESTROYED;
				QuantitySunk quantitySunk = quantitySunkDao.findByUserId(userId);
				int counter = quantitySunk.getCounter();
				counter++;
				
				if (counter == 5) {
					shotResult = ShotResult.WIN;
				}
				else {
					quantitySunk.setCounter(counter);
					quantitySunkDao.save(quantitySunk);
				}
			}
			else {
				shotResult = ShotResult.HITTED;
			}
			
			((ShipSize4) shipSize).setShip(ship);
			shipSize4Dao.save((ShipSize4) shipSize);
			
		}
		else if (shipSize instanceof ShipSize5) {
			
			System.out.println("Zmieniam stan dla statku o klasie shipSize5");
			
			if (((ShipSize5) shipSize).getField1().equals(location)) {
				((ShipSize5) shipSize).setField1("");
			}
			else if (((ShipSize5) shipSize).getField2().equals(location)) {
				((ShipSize5) shipSize).setField2("");
			}
			else if (((ShipSize5) shipSize).getField3().equals(location)) {
				((ShipSize5) shipSize).setField3("");
			}
			else if (((ShipSize5) shipSize).getField4().equals(location)) {
				((ShipSize5) shipSize).setField4("");
			}
			else if (((ShipSize5) shipSize).getField5().equals(location)) {
				((ShipSize5) shipSize).setField5("");
			}
			
			Ship ship = ((ShipSize5) shipSize).getShip();
			int shotCount = ship.getShotCount();
			shotCount++;
			ship.setShotCount(shotCount);
			((ShipSize5) shipSize).setShip(ship);
			
			if (shotCount == ship.getSize()) {
				
				shotResult = ShotResult.DESTROYED;
				QuantitySunk quantitySunk = quantitySunkDao.findByUserId(userId);
				int counter = quantitySunk.getCounter();
				counter++;
				
				if (counter == 5) {
					shotResult = ShotResult.WIN;
				}
				else {
					quantitySunk.setCounter(counter);
					quantitySunkDao.save(quantitySunk);
				}
			}
			else {
				shotResult = ShotResult.HITTED;
			}
			
			((ShipSize5) shipSize).setShip(ship);
			shipSize5Dao.save((ShipSize5) shipSize);
			
		}
	}
		
}
