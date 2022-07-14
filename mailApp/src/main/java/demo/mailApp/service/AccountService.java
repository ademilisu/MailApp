package demo.mailApp.service;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demo.mailApp.dto.AccountDto;
import demo.mailApp.dto.UserRequest;
import demo.mailApp.model.AppUser;
import demo.mailApp.model.Profile;

@Service
public class AccountService {

	private UserService userService;
	private ProfileService profileService;
	private MyUserDetailService myUserDetailService;
	private AuthenticationManager authManager;
	private PasswordEncoder passwordEncoder;

	public AccountService(UserService userService, ProfileService profileService,
			MyUserDetailService myUserDetailService, AuthenticationManager authManager,
			PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.profileService = profileService;
		this.myUserDetailService = myUserDetailService;
		this.authManager = authManager;
		this.passwordEncoder = passwordEncoder;
	}

	public AccountDto save(UserRequest userRequest) {
		AppUser appUser = userService.findByUsername(userRequest.getUsername());
		AccountDto ac = new AccountDto();
		if (appUser == null) {
			Profile profile = new Profile();
			profile.setUsername(userRequest.getUsername());
			profile.setId(UUID.randomUUID().toString());
			profile = profileService.saveProfile(profile, null, null);
			appUser = new AppUser();
			appUser.setUsername(userRequest.getUsername());
			appUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
			appUser.setProfile(profile);
			appUser = userService.save(appUser);
			ac.setCode(10);
		} else {
			ac.setCode(0);
		}
		return ac;
	}

	public AccountDto login(UserRequest userRequest) {
		UserDetails user = myUserDetailService.loadUserByUsername(userRequest.getUsername());
		AccountDto ac = new AccountDto();
		if (user != null) {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					userRequest.getUsername(), userRequest.getPassword());
			Authentication authentication = authManager.authenticate(token);
			if (authentication.getPrincipal() != null) {
				AppUser appUser = userService.findByUsername(userRequest.getUsername());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				ac.setProfile(appUser.getProfile());
				ac.setCode(10);
			}
		} else {
			ac.setCode(0);
		}
		return ac;
	}

	public AppUser findUser(String username) {
		return userService.findByUsername(username);
	}

	public Profile getProfile(String username) {
		AppUser appUser = findUser(username);
		return appUser != null ? appUser.getProfile() : null;
	}

}
