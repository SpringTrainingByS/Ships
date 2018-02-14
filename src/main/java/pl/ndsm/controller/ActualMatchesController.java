package pl.ndsm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import pl.ndsm.conf.ConfConstants;
import pl.ndsm.dao.matchInfo.ActualMatchesDao;
import pl.ndsm.model.dataTransport.Message;
import pl.ndsm.model.matchInfo.ActualMatches;

@RestController
@RequestMapping("actual-matches")
public class ActualMatchesController {

	@Autowired
	private ActualMatchesDao aMatchesDao;
	
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	@RequestMapping(value = "get/all", method = RequestMethod.GET)
	public List<ActualMatches> getAll() {
		return aMatchesDao.findAll();
	}
	
	// test
	@RequestMapping(value = "add", method = RequestMethod.POST) 
	public void add() {
		List<ActualMatches> list = aMatchesDao.findAll();
		
		for (ActualMatches aMatch : list) {
			aMatch.setMatch(null);
		}
		
		String jsonResult = new Gson().toJson(list);
		
		System.out.println("Przekonwertowane na jsona: " + jsonResult);
		webSocket.convertAndSend(ConfConstants.MAIN_CHANEL + ConfConstants.FIRST10_CHANEL, new Message(jsonResult));
	}
	
}
