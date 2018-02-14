package pl.ndsm.service.game;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ndsm.conf.ConfConstants;
import pl.ndsm.conf.ShotResult;
import pl.ndsm.conf.UserChanel_GR_Codes;
import pl.ndsm.dao.ChanelDao;
import pl.ndsm.dao.gameInfo.ReadinessToGameDao;
import pl.ndsm.dao.matchInfo.MatchDao;
import pl.ndsm.dao.matchInfo.PlayerTurnDao;
import pl.ndsm.dao.matchInfo.QuantitySunkDao;
import pl.ndsm.dao.shipInfo.ShipDao;
import pl.ndsm.model.dataTransport.ShotResultMessage;
import pl.ndsm.service.comunicaiton.ChanelTransmitter;


@Service
public class GameService {
	
	@Autowired
	private MatchDao matchDao;
	
	@Autowired
	private ReadinessToGameDao readinessDao;
	
	@Autowired
	private PlayerTurnDao playerTurnDao;
	
	@Autowired 
	private ChanelTransmitter chanelTransmitter;
	
	@Autowired
	private ChanelDao chanelDao;
	
	@Autowired 
	private ShipDao shipDao;
	
	@Autowired 
	private ShotAnalyzer shotAnalzyer;
	
	@Autowired 
	private QuantitySunkDao quantiySunkDao;
	
	@Autowired
	private GameCleaner gameCleaner;
	
	private long firstPlayerId = 0L;
	private long secondPlayerId = 0L;
	
	public void requestReadiness(long userId) {
		long matchInfoId = matchDao.getMatchInfoIdByUserId(userId);
		System.out.println(matchInfoId);
		
		int count = readinessDao.countReadinessForGame(matchInfoId);
		
		if (count == 0) {
			readinessDao.insert(matchInfoId, userId);
			System.out.println("Wstawi³em gracza do bazy ¿e jest ju¿ aktywny");
		}
		else {
			readinessDao.deleteByMatchInfoId(matchInfoId);
			System.out.println("Wysy³am sygna³ do obu graczy ¿e rozpoczynamy grê");
			sendGameStartSignalToBothPlayers(matchInfoId);
			
		}
	}
	
	public void sendGameStartSignalToBothPlayers(long matchInfoId) {
		
		String result = matchDao.selectUsersIdsByMatchInfoId(matchInfoId);
		String[] usersIds = result.split(",");
		long userId1 = Long.parseLong(usersIds[0]);
		long userId2 = Long.parseLong(usersIds[1]);
		
		quantiySunkDao.insert(userId1);
		quantiySunkDao.insert(userId2);
		
		roundFirst(userId1, userId2);
		
		String firstUserChanelName = chanelDao.findChanelNameByUserId(firstPlayerId);
		String secondUserChanelName = chanelDao.findChanelNameByUserId(secondPlayerId);
		
		System.out.println(ConfConstants.FULL_USER_CHANEL_PATH + firstUserChanelName);
		System.out.println(ConfConstants.FULL_USER_CHANEL_PATH + secondUserChanelName);
		
		playerTurnDao.insert(matchInfoId, firstPlayerId);
		
		ShotResultMessage resultForFirst = new ShotResultMessage();
		ShotResultMessage resultForSecond = new ShotResultMessage();
		
		resultForFirst.setOperationCode(UserChanel_GR_Codes.FIRST_SHOOT);
		resultForSecond.setOperationCode(UserChanel_GR_Codes.FIRST_WAIT);
		
		chanelTransmitter.sendMessageToUser(ConfConstants.FULL_USER_CHANEL_PATH + firstUserChanelName, resultForFirst);
		chanelTransmitter.sendMessageToUser(ConfConstants.FULL_USER_CHANEL_PATH + secondUserChanelName, resultForSecond);
	}
	
	public void roundFirst(long userId1, long userId2) {
		Random r = new Random();
		int turn = r.nextInt(2);
		
		if (turn == 0) {
			firstPlayerId = userId1;
			secondPlayerId = userId2;
		}
		else {
			firstPlayerId = userId2;
			secondPlayerId = userId1;
		}
	}
	
	public void analyseShot(long userId, String location) throws Exception {
		
		System.out.println("GameService: analyzeShot");
		
		long matchInfoId = playerTurnDao.findMatchInfoIdByUserId(userId);
		if (matchInfoId == 0) {
			throw new Exception("Shot not allowed");
		}
		
		System.out.println("Znalaz³em matchInfoId: " + matchInfoId);
		
		String result = matchDao.selectUsersIdsByMatchInfoId(matchInfoId);
		String[] results = result.split(",");
		long enemyId = 0;
		
		if (Long.parseLong(results[0]) != userId) {
			enemyId = Long.parseLong(results[0]);
		}
		else {
			enemyId = Long.parseLong(results[1]);
		}
		
		System.out.println("Enemy User Id: " + enemyId);
		
		List<BigInteger> shipsIds = shipDao.findShipsIdsByUserId(enemyId);
		
		for (BigInteger shipId : shipsIds) {
			System.out.println("Statek id: " + shipId);
		}
		
//		String shipsIdsStr = shipsIds.toString();
//		System.out.println("Zbiór id przed konwersji: " + shipsIdsStr);
//		shipsIdsStr = shipsIdsStr.replace("[", "");
//		shipsIdsStr = shipsIdsStr.replace("]", "");
//		System.out.println("Zbiór id po konwersji: " + shipsIdsStr);
		
		List<Long> shipsIdsLongs = new ArrayList<Long>();
		
		for (BigInteger shipId : shipsIds) {
			shipsIdsLongs.add(shipId.longValue());
		}
		
		ShotResult shotResult = shotAnalzyer.checkShot(location, shipsIdsLongs, enemyId);
		
		System.out.println("ShotResult: " + shotResult);
		
		if (shotResult == shotResult.MISSED) {
			playerTurnDao.updateByMatchId(matchInfoId, enemyId);
		}
		else if (shotResult == ShotResult.WIN) {
			gameCleaner.clearAllGameInfos(userId, enemyId, matchInfoId); 
		}
		
		Map messages =  prepareResultShotMessage(userId, enemyId, shotResult, location);
		
		String userChanelName = chanelDao.findChanelNameByUserId(userId);
		String enemyChanelName = chanelDao.findChanelNameByUserId(enemyId);
		
		System.out.println("Wiadomoœæ wys³ana do u¿ytkownika: " + messages.get("user").toString());
		System.out.println("Wiadomoœæ wys³ana do przeciwnika: " + messages.get("enemy").toString());
		
		chanelTransmitter.sendMessageToUser(ConfConstants.FULL_USER_CHANEL_PATH + userChanelName, messages.get("user"));
		chanelTransmitter.sendMessageToUser(ConfConstants.FULL_USER_CHANEL_PATH + enemyChanelName, messages.get("enemy"));
	}
	
	public Map prepareResultShotMessage(long userId, long enemyId, ShotResult shotResult, String location) {
		
		System.out.println("GameSerive: prepareResultShotMessage");
		
		Map messages = new HashMap();
		ShotResultMessage messageForUser = new ShotResultMessage();
		ShotResultMessage messageForEnemy = new ShotResultMessage();
		
		messageForUser.setLocalization(location);
		messageForEnemy.setLocalization(location);
		
		switch (shotResult) {
		
			case MISSED: 
				System.out.println("Strza³ u¿ytkownika to pud³o");
				messageForEnemy.setOperationCode(UserChanel_GR_Codes.ENEMY_SHOT_FAILURE);
				messageForEnemy.setMessage("Pud³o dla twojego przeciwnika. Pole: " + location);
				
				messageForUser.setOperationCode(UserChanel_GR_Codes.YOUR_SHOT_FAILURE);
				messageForUser.setMessage("Pud³o dla Ciebie. Pole: " + location);
				break;
				
			case HITTED: 
				System.out.println("Strza³ u¿ytkownika coœ trafi³");
				messageForEnemy.setOperationCode(UserChanel_GR_Codes.ENEMY_SHOT_SUCCESS);
				messageForEnemy.setMessage("Trafienie dla twojego przeciwnika. Pole: " + location);
				
				messageForUser.setOperationCode(UserChanel_GR_Codes.YOUR_SHOT_SUCCESS);
				messageForUser.setMessage("Trafienie dla Ciebie. Pole: " + location);
				break;
				
			case DESTROYED:
				System.out.println("Strza³ u¿ytkownika coœ zniszczy³");
				messageForEnemy.setOperationCode(UserChanel_GR_Codes.YOUR_SHIP_DESTROYED);
				messageForEnemy.setMessage("Statek zosta³ zatopiony przez twojego przeciwnika. Pole: " + location);
				
				messageForUser.setOperationCode(UserChanel_GR_Codes.ENEMY_SHIP_DESTROYED);
				messageForUser.setMessage("Zatopi³eœ statek przeciwnika. Pole " + location);
				break;
				
			case WIN:
				System.out.println("Strza³ u¿ytkownika coœ wygra³");
				messageForEnemy.setOperationCode(UserChanel_GR_Codes.LOSS_MATCH);
				messageForEnemy.setMessage("PRZEGRA£EŒ BITWÊ. Pole: " + location);
				
				messageForUser.setOperationCode(UserChanel_GR_Codes.VICTORY_RESULT);
				messageForUser.setMessage("WYGRA£EŒ BITWÊ. Pole " + location);
				break;
				
			default:
				System.out.println("Niewiadomo jaki stan dla strzelenia");
				break;
		}
		
		messages.put("user", messageForUser);
		messages.put("enemy", messageForEnemy);
		
		return messages;
	}
	
	
	
}
