package myboot.myapp.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import myboot.myapp.dao.UserRepository;
import myboot.myapp.model.User;


@Service
@Profile("usejwt")
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	public String login(String mail, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mail, password));
			var user = userRepository.findById(mail).get();
			String jwt = jwtTokenProvider.createToken(user);
			addToken(jwt);
			return jwt;
		} catch (AuthenticationException e) {
			throw new MyJwtException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public String signup(User user) {
		if (userRepository.findById(user.getEmail()).isPresent()) {
			throw new MyJwtException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return jwtTokenProvider.createToken(user);
	}

	public void delete(String mail) {
		userRepository.deleteById(mail);
	}

	public User search(String mail) {
		return userRepository.findById(mail)
				.orElseThrow(() -> new MyJwtException("The user doesn't exist", HttpStatus.NOT_FOUND));
	}

	public User whoami(HttpServletRequest req) {
		return search(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	public String refresh(String mail) {
		return jwtTokenProvider.createToken(userRepository.findById(mail).get());
	}
	
	
	public void addToken(String token) {
		this.jwtTokenProvider.addToken(token);
	}
	
	public List<String> logout(HttpServletRequest req) {
		this.jwtTokenProvider.removeToken(jwtTokenProvider.resolveToken(req));
		return this.jwtTokenProvider.getJwtList();
	}

}
