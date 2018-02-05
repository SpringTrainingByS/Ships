package pl.ndsm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping(value = "/")
	public String loadMainPage() {
		return "index";
	}
	
	@RequestMapping(value = "/waiting-room")
	public String loadWaitingRoomPage() {
		return "waiting-room";
	}

}
