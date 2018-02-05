package pl.ndsm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.ndsm.dao.UserDao;
import pl.ndsm.dao.ChanelDao;
import pl.ndsm.exception.ValidationException;
import pl.ndsm.model.userInfo.Chanel;
import pl.ndsm.model.userInfo.UserApp;
import pl.ndsm.validator.UserValidator;

@Service
public class UserService {
	
	@Autowired
	private UserValidator validator;
	
	@Autowired 
	private UserDao userDao;
	
	@Autowired
	private ChanelDao chanelDao;
	
	@Autowired
	private BCryptPasswordEncoder bCrytpPasswordEncoder;
		
	public void add(UserApp user) throws ValidationException {
		validator.validate(user, userDao);
		String passwordCrypted = bCrytpPasswordEncoder.encode(user.getPassword());
		user.setPassword(passwordCrypted);
		userDao.save(user);
		addWebsocketForUser(user);
	}
	
	public void addWebsocketForUser(UserApp user) {
		String nameChanelForUser = user.getUsername() + Long.toString(user.getId());
		Chanel socket = new Chanel(user, nameChanelForUser);
		chanelDao.save(socket);
	}

}
