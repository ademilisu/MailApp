package demo.mailApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import demo.mailApp.model.AppUser;
import demo.mailApp.repository.AppUserRepository;

@Service
public class UserService {

	@Autowired
	private AppUserRepository appUserRepository;

	public AppUser findByUsername(String username) {
		return appUserRepository.findByUsername(username);
	}

	public AppUser save(AppUser appUser) {
		return appUserRepository.save(appUser);
	}
}
