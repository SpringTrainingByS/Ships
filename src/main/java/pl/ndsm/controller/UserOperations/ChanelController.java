package pl.ndsm.controller.UserOperations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.ndsm.exception.ValidationException;
import pl.ndsm.service.comunicaiton.ChanelService;

@RestController
@RequestMapping(value = "chanel")
public class ChanelController {
	
	@Autowired 
	public ChanelService chanelService;
	
	@RequestMapping(params = "user_id", method = RequestMethod.GET)
	public String getChanelNameByUserId(@RequestParam("user_id") Long userId) throws ValidationException {
		return chanelService.getChanelNameByUserId(userId);
	}

}
