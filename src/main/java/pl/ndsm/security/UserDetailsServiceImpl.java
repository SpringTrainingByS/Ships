package pl.ndsm.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.ndsm.dao.UserDao;
import pl.ndsm.model.userInfo.UserApp;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserDao userDao;
	
	
	
	public UserDetailsServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("UserDetailsService.loadUserByUserName");
		System.out.println("Username: " + username);
		UserApp userApp = userDao.findByUsername(username);
		
		if (userApp == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(userApp.getUsername(), userApp.getPassword(), new ArrayList<>());
	}

}
