package pl.ndsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.ndsm.model.shipInfo.fieldLocation.ShipContainer;
import pl.ndsm.service.ShipService;
import pl.ndsm.service.WaitingForMatchSerivce;

@RestController
@RequestMapping(value = "ships")
public class ShipController {

	@Autowired
	private ShipService shipService;
	
	@Autowired
	private WaitingForMatchSerivce  waitingForMatchService;
	
	@RequestMapping(value = "definition", method = RequestMethod.POST)
	public boolean add(@RequestBody ShipContainer shipContainer) throws Exception {
		shipService.addShip(shipContainer);
		waitingForMatchService.add(shipContainer.getUserId());
		return waitingForMatchService.isOnlyOne();
	}
	
}
