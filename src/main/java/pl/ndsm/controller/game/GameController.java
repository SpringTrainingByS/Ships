package pl.ndsm.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.ndsm.service.game.GameService;

@RestController
@RequestMapping(value = "game")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@RequestMapping(value = "readiness", params = {"user_id"}, method = RequestMethod.GET)
	public void setReadiness(@RequestParam("user_id") long userId) {
		gameService.requestReadiness(userId);
	}
	
	@RequestMapping(value = "shot", params = {"user_id", "location"}, method = RequestMethod.GET)
	public void getShot(@RequestParam("user_id") long userId, @RequestParam("location") String location) throws Exception {
		gameService.analyseShot(userId, location);
	}

}
