package pl.ndsm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.ndsm.dao.UserDao;
import pl.ndsm.exception.ValidationException;
import pl.ndsm.model.userInfo.UserApp;
import pl.ndsm.validator.UserValidator;

@Service
public class UserService {
	
	@Autowired
	private UserValidator validator;
	
	@Autowired 
	private UserDao dao;
	
	@Autowired
	private BCryptPasswordEncoder bCrytpPasswordEncoder;
		
	public void add(UserApp user) throws ValidationException {
		validator.validate(user, dao);
		String passwordCrypted = bCrytpPasswordEncoder.encode(user.getPassword());
		//user.setPassword(passwordCrypted);
		dao.save(user);
	}

}
