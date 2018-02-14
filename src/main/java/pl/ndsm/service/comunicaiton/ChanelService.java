package pl.ndsm.service.comunicaiton;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ndsm.dao.ChanelDao;
import pl.ndsm.exception.ValidationException;

@Service
public class ChanelService {

	@Autowired
	private ChanelDao chanelDao;
	
	public String getChanelNameByUserId(Long userId) throws ValidationException {
		if (userId == null || userId == 0) {
			List<String> message = new ArrayList<String>();
			message.add("Podane id jest puste");
			
			throw new ValidationException(message);
		}
		
		return chanelDao.findChanelNameByUserId(userId);
	}
	
}
