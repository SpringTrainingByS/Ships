package pl.ndsm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTestConroller {

	@RequestMapping(value = "/hello", method = RequestMethod.POST)
	public  String hello() {
		return "hello";
	}
	
}
