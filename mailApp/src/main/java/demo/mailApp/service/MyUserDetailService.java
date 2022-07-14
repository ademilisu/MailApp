package demo.mailApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import demo.mailApp.model.AppUser;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private UserService appUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = appUserService.findByUsername(username);
		if (appUser == null) {
			throw new UsernameNotFoundException(username);
		} else {
			List<GrantedAuthority> authorities = new ArrayList<>();
			org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
					appUser.getUsername(), appUser.getPassword(), authorities);
			return user;
		}
	}

}
