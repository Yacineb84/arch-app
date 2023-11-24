package myboot.myapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import myboot.myapp.dao.UserRepository;


/**
 * Une nouvelle version de la classe qui code la description d'un utilisateur
 * connecté.
 */
@Service
@Profile("usejwt")
public class JwtUserDetails implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final var user = userRepository.findById(email).orElseThrow(() -> {
			throw new UsernameNotFoundException("User '" + email + "' not found");
		});

		
		var authorites = new SimpleGrantedAuthority("USER");
		return org.springframework.security.core.userdetails.User//
				.withUsername(email)//
				.password(user.getPassword())//
				.authorities(authorites)//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();
	}

}
