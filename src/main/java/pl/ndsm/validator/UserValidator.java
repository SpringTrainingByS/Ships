package pl.ndsm.validator;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import pl.ndsm.dao.UserDao;
import pl.ndsm.exception.ValidationException;
import pl.ndsm.model.userInfo.User;

@Service
public class UserValidator {
	

	public void validate(User user, UserDao dao) throws ValidationException {
		ArrayList<String> messages = new ArrayList<String>();
		
		if (user.getId() != 0) {
			messages.add("Niepoprawne id.");
		}
		
		if (!user.getLogin().matches("^[a-zA-z0-9-_]$")) {
			messages.add("Login nie jest zgodny z kryteriami.");
		}
		else if (dao.existsByLogin(user.getLogin())) {
			messages.add("Login u¿ytkownika istnieje ju¿ w bazie.");
		}
		
		if (user.getPassword().isEmpty()) {
			messages.add("Has³o u¿ytkownika nie mo¿e byæ puste.");
		}
		
		if (!messages.isEmpty()) {
			throw new ValidationException(messages);
		}
	}
	
}
