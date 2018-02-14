package pl.ndsm.service.comunicaiton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import pl.ndsm.model.dataTransport.Message;

@Service
public class ChanelTransmitter {
	
	@Autowired
	private SimpMessagingTemplate websocket;
	
	public void sendMessageToUser(String chanelPath, Object content) {
		String jsonResult = new Gson().toJson(content);
		websocket.convertAndSend(chanelPath, new Message(jsonResult));
	}
	
	public void sendMessageToUser(String chanelPath, String content) {
		websocket.convertAndSend(chanelPath, new Message(content));
	}
	
	public void sendMessageToUser(String chanelPath, int content) {
		String result = Integer.toString(content);
		websocket.convertAndSend(chanelPath, new Message(result));
	}
	
}
