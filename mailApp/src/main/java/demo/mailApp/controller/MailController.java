package demo.mailApp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import demo.mailApp.dto.ActionDto;
import demo.mailApp.dto.MailDto;
import demo.mailApp.model.AppUser;
import demo.mailApp.model.Box;
import demo.mailApp.model.BoxItem;
import demo.mailApp.service.AccountService;
import demo.mailApp.service.MailService;

@RestController
@RequestMapping("/mails")
public class MailController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private MailService mailService;

	@GetMapping("/{id}")
	public MailDto get(@PathVariable String id) {
		return mailService.getMail(id);
	}

	@GetMapping()
	public List<BoxItem> list(@RequestParam("box") Box box, Principal principal) {
		if (principal != null) {
			AppUser appUser = accountService.findUser(principal.getName());
			List<BoxItem> list = mailService.list(appUser.getProfile(), box);
			return list;
		} else
			return null;
	}

	@PostMapping("/send")
	public MailDto send(@RequestBody MailDto mailDto, Principal principal) {
		if (principal != null) {
			AppUser user = accountService.findUser(principal.getName());
			return mailService.send(user.getProfile(), mailDto);
		} else
			return null;

	}

	@PostMapping()
	public ActionDto action(@RequestParam("action") String action, @RequestBody ActionDto actionDto,
			Principal principal) {
		if (principal != null) {
			AppUser user = accountService.findUser(principal.getName());
			return mailService.action(user.getProfile(), actionDto, action);
		} else
			return null;
	}

}
