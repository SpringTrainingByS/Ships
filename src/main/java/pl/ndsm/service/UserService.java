package pl.ndsm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ndsm.dao.UserDao;
import pl.ndsm.exception.ValidationException;
import pl.ndsm.model.userInfo.User;
import pl.ndsm.validator.UserValidator;

@Service
public class UserService {
	
	@Autowired
	private UserValidator validator;
	
	@Autowired 
	private UserDao dao;
	
	public void add(User user) throws ValidationException {
		validator.validate(user, dao);
		dao.save(user);
	}

}
