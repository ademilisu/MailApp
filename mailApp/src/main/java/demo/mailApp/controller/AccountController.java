package demo.mailApp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import demo.mailApp.dto.AccountDto;
import demo.mailApp.dto.UserRequest;
import demo.mailApp.model.AppUser;
import demo.mailApp.model.Profile;
import demo.mailApp.service.AccountService;
import demo.mailApp.service.ProfileService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProfileService profileService;

	@PostMapping("/signup")
	public AccountDto signup(@RequestBody UserRequest userRequest) {
		return accountService.save(userRequest);
	}

	@PostMapping("/login")
	public AccountDto login(@RequestBody UserRequest userRequest) {
		return accountService.login(userRequest);
	}

	@PutMapping("/profile")
	public Profile update(@RequestBody MultipartFile file, @RequestParam("defaultImage") String defaultImage,
			Principal principal) {
		AppUser user = null;
		if (principal != null) {
			user = accountService.findUser(principal.getName());
		}
		return principal != null ? profileService.saveProfile(user.getProfile(), file, defaultImage) : null;
	}

	@GetMapping("/profile")
	public Profile getProfile(Principal principal) {
		return principal != null ? accountService.getProfile(principal.getName()) : null;
	}
}
