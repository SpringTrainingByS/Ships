package pl.ndsm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "")
public class MainController {
	
	@RequestMapping(value = "")
	public String loadMainPage() {
		return "index";
	}

}
