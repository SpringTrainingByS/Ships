package pl.ndsm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ndsm.conf.ConfConstants;
import pl.ndsm.conf.UserChanelWRCodes;
import pl.ndsm.dao.ChanelDao;
import pl.ndsm.dao.UserDao;
import pl.ndsm.dao.WaitingForMatchDao;
import pl.ndsm.dao.matchInfo.MatchDao;
import pl.ndsm.model.matchInfo.MatchInfo;
import pl.ndsm.model.userInfo.UserApp;
import pl.ndsm.service.comunicaiton.ChanelTransmitter;

@Service
public class MatchService {
	
	@Autowired
	private WaitingForMatchDao waitingDao;
	
	@Autowired
	private MatchDao matchDao;
	
	@Autowired
	private ChanelDao chanelDao;
	
	@Autowired
	private ChanelTransmitter chanelTransmitter; 
	
	@Autowired
	private UserDao userDao;
	
	public void startGame(long requestedUserId) {
		long secondUserId = waitingDao.selectFirstWaitingUserId();
		waitingDao.deleteByUserId(secondUserId);
		System.out.println("Id u¿ytkowników: " + requestedUserId + ", " + secondUserId);
		
		UserApp user1 = userDao.findById(secondUserId);
		UserApp user2 = userDao.findById(requestedUserId);
		MatchInfo matchInfo = new MatchInfo(user1, user2);
		
		matchDao.save(matchInfo);
		
		String chanelNameUser1 =  chanelDao.findChanelNameByUserId(requestedUserId);
		String chanelNameUser2 = chanelDao.findChanelNameByUserId(secondUserId);
		
		System.out.println("Wysy³am na kana³y.");
		System.out.println(ConfConstants.MAIN_CHANEL + ConfConstants.USER_CHANEL_PREFIX + "/" + chanelNameUser1);
		System.out.println(ConfConstants.MAIN_CHANEL + ConfConstants.USER_CHANEL_PREFIX + "/" + chanelNameUser2);
		
		chanelTransmitter
			.sendMessageToUser(ConfConstants.MAIN_CHANEL + ConfConstants.USER_CHANEL_PREFIX + "/" + chanelNameUser1, UserChanelWRCodes.MOVE_TO_GAME);
		
		chanelTransmitter
			.sendMessageToUser(ConfConstants.MAIN_CHANEL + ConfConstants.USER_CHANEL_PREFIX + "/" + chanelNameUser2, UserChanelWRCodes.MOVE_TO_GAME);
		
	}
	
}
