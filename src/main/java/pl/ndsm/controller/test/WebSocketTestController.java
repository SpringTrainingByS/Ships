package pl.ndsm.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import pl.ndsm.conf.ConfConstants;
import pl.ndsm.conf.UserChanelWRCodes;
import pl.ndsm.model.dataTransport.Message;

@RestController
@RequestMapping("chanel-tests")
public class WebSocketTestController {
	
	@Autowired
	private SimpMessagingTemplate websocket;
	
	@RequestMapping(value = "send-to-game-room", method = RequestMethod.POST)
	public void sendToGameRoom() {
		
		String jsonResult = new Gson().toJson(UserChanelWRCodes.MOVE_TO_GAME);
		System.out.println("Wysy³am info do kana³u: " + ConfConstants.MAIN_CHANEL + ConfConstants.USER_CHANEL_PREFIX + "/dariusz1");
		websocket.convertAndSend(ConfConstants.MAIN_CHANEL + ConfConstants.USER_CHANEL_PREFIX + "/dariusz1", new Message(jsonResult));;
	}
}
