package com.mailsoft.security;

import com.mailsoft.domain.Role;
import com.mailsoft.domain.User;
import com.mailsoft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String loginOrEmail) throws UsernameNotFoundException {

		if (loginOrEmail.trim().isEmpty()) {
			throw new UsernameNotFoundException("username is empty");
		}

		User user = userRepository.findByLoginOrEmail(loginOrEmail);

		if (user == null) {
			throw new UsernameNotFoundException("User " + loginOrEmail + " not found");
		}

		//return new org.springframework.security.core.userdetails.User(username, user.getPassword(), getGrantedAuthorities(user));
		return org.springframework.security.core.userdetails.User
				.withUsername(loginOrEmail)
				.password(user.getPassword())
				.authorities(getGrantedAuthorities(user))
				.accountExpired(false)
				.accountLocked(false)
				.credentialsExpired(false)
				.disabled(!user.getActivated())
				.build();
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
		return authorities;
	}

}
