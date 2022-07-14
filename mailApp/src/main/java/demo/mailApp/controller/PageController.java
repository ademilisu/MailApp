package demo.mailApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping(value = { "/", "/inbox", "/outbox", "/trash", "/mail/**",
			"/profile", "/signup", "/login" })
	public String index() {
		return "index.html";
	}
}
