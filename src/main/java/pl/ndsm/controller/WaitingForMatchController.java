package pl.ndsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.ndsm.exception.ValidationException;
import pl.ndsm.service.WaitingForMatchSerivce;

//@RestController
@RequestMapping(value = "/waiting-for-match")
public class WaitingForMatchController {
	
	@Autowired
	private WaitingForMatchSerivce  waitingForMatchService;
	
	@RequestMapping(params = {"user_id"}, method = RequestMethod.GET)
	public void add(@RequestParam("user_id") long userId) throws ValidationException {
		waitingForMatchService.add(userId);
	}
}
