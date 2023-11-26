package myboot.myapp.security;

import java.security.DrbgParameters.NextBytes;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.experimental.var;
import myboot.myapp.dao.UserRepository;
import myboot.myapp.model.Activity;
import myboot.myapp.model.Cv;
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

	public String login(String email, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			var user = userRepository.findByEmail(email);

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
		user = new User(user.getEmail(),user.getName(),user.getFirstName(),user.getSite(),user.getDateOfBirth(),user.getPassword());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		user.setPassword(user.getPassword());
		userRepository.save(user);
		var c = jwtTokenProvider.createToken(user);
		return c;
	}

	public void delete(String email) {
		userRepository.deleteById(email);
	}

	public User search(String email) {
		return userRepository.findById(email)
				.orElseThrow(() -> new MyJwtException("The user doesn't exist", HttpStatus.NOT_FOUND));
	}

	public User whoami(HttpServletRequest req) {
		return search(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	public String refresh(String email) {
		return jwtTokenProvider.createToken(userRepository.findById(email).get());
	}
	
	
	public void addToken(String token) {
		this.jwtTokenProvider.addToken(token);
	}
	
	public List<String> logout(HttpServletRequest req) {
		this.jwtTokenProvider.removeToken(jwtTokenProvider.resolveToken(req));
		
		return this.jwtTokenProvider.getJwtList();
	}

}
