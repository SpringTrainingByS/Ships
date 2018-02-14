package pl.ndsm.service.game;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ndsm.dao.matchInfo.MatchDao;
import pl.ndsm.dao.matchInfo.PlayerTurnDao;
import pl.ndsm.dao.matchInfo.QuantitySunkDao;
import pl.ndsm.dao.shipInfo.ShipDao;
import pl.ndsm.dao.shipInfo.ShipSize2Dao;
import pl.ndsm.dao.shipInfo.ShipSize3Dao;
import pl.ndsm.dao.shipInfo.ShipSize4Dao;
import pl.ndsm.dao.shipInfo.ShipSize5Dao;

@Service
public class GameCleaner {

	@Autowired
	private ShipSize2Dao shipSize2Dao;
	
	@Autowired
	private ShipSize3Dao shipSize3Dao;
	
	@Autowired
	private ShipSize4Dao shipSize4Dao;
	
	@Autowired
	private ShipSize5Dao shipSize5Dao;
	
	@Autowired
	private ShipDao shipDao;
	
	@Autowired 
	private PlayerTurnDao playerTurnDao;
	
	@Autowired
	private QuantitySunkDao sunkDao;
	
	@Autowired
	private MatchDao matchInfoDao;
	
	
	
	public void clearAllGameInfos(long userId, long enemyId, long matchInfoId) { 
		
		List<BigInteger> shipsIdsUser = shipDao.findShipsIdsByUserId(userId);
		List<BigInteger> shipsIdsEnemy = shipDao.findShipsIdsByUserId(enemyId);
		
		List<Long> allShipsIds = new ArrayList<Long>();
		
		for (BigInteger shipId : shipsIdsUser) {
			allShipsIds.add(shipId.longValue());
		}
		
		for (BigInteger shipId : shipsIdsEnemy) {
			allShipsIds.add(shipId.longValue());
		}
		
		shipSize2Dao.deleteByShipIdIn(allShipsIds);
		shipSize3Dao.deleteByShipIdIn(allShipsIds);
		shipSize4Dao.deleteByShipIdIn(allShipsIds);
		shipSize5Dao.deleteByShipIdIn(allShipsIds);
		
		shipDao.deleteByIdIn(allShipsIds);
		
		playerTurnDao.deleteByMatchInfoId(matchInfoId);
		
		sunkDao.deleteByUserId(userId);
		sunkDao.deleteByUserId(enemyId);
		
		matchInfoDao.deleteById(matchInfoId);
		
	}
	
	
	
}
