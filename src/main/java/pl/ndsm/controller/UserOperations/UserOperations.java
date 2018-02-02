package pl.ndsm.controller.UserOperations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.ndsm.exception.ValidationException;
import pl.ndsm.model.userInfo.User;
import pl.ndsm.service.UserService;

@RestController
public class Registration {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public void register(@RequestBody User user) throws ValidationException {
		userService.add(user);
	}
}
