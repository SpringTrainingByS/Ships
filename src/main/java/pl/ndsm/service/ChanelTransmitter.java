package pl.ndsm.service;

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
	
}
