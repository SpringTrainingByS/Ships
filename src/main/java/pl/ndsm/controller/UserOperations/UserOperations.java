package pl.ndsm.controller.UserOperations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.ndsm.exception.ValidationException;
import pl.ndsm.model.userInfo.UserApp;
import pl.ndsm.service.UserService;

@RestController
@RequestMapping(value = "user")
public class UserOperations {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public void register(@RequestBody UserApp user) throws ValidationException {
		System.out.println(user.toString());
		userService.add(user);
	}
	
	@RequestMapping(params = {"username"}, method = RequestMethod.GET) 
	public Long getIdByUserName(@RequestParam("username") String username) throws ValidationException {
		return userService.getIdByUsername(username);
	}
}
