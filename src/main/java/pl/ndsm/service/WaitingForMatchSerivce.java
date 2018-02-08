package pl.ndsm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ndsm.dao.WaitingForMatchDao;
import pl.ndsm.exception.ValidationException;

@Service
public class WaitingForMatchSerivce {
	
	@Autowired
	private WaitingForMatchDao waitingDao;
	
	public void add(long userId) throws ValidationException {
		if ( userId == 0) {
			List<String> message = new ArrayList<String>();
			message.add("Nie poprawne id u¿ytkownika");
			throw new ValidationException(message);
		}
		
		//WaitingForMatch waitingForMatch = new WaitingForMatch(userId);
		
		waitingDao.saveUserId(userId);
	}
	
	public boolean isOnlyOne() {
		
		boolean isOnlyOne = false;
		long numberOfWaiting = waitingDao.count();
		if (numberOfWaiting == 1) {
			isOnlyOne = true;
		}
		
		return isOnlyOne;
	}
	
}
