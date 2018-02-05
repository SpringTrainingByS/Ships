package pl.ndsm.validator;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import pl.ndsm.dao.UserDao;
import pl.ndsm.exception.ValidationException;
import pl.ndsm.model.userInfo.UserApp;

@Service
public class UserValidator {
	

	public void validate(UserApp user, UserDao dao) throws ValidationException {
		ArrayList<String> messages = new ArrayList<String>();
		
		if (user == null) {
			messages.add("Brak danych");
		}
		else if (user.getUsername() == null) {
			messages.add("Brak loginu");
		}
		else if (user.getPassword() == null) {
			messages.add("Brak has³a");
		}
		
		else {
			if (user.getId() != 0) {
				messages.add("Niepoprawne id.");
			}
			
			if (!user.getUsername().matches("^[a-zA-z0-9-_]+$")) {
				messages.add("Login nie jest zgodny z kryteriami.");
			}
			else if (dao.existsByUsername(user.getUsername())) {
				messages.add("Login u¿ytkownika istnieje ju¿ w bazie.");
			}
			
			if (user.getPassword().isEmpty()) {
				messages.add("Has³o u¿ytkownika nie mo¿e byæ puste.");
			}
		}
		
		if (!messages.isEmpty()) {
			throw new ValidationException(messages);
		}
	}
	
}
