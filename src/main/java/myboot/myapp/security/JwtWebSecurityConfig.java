package myboot.myapp.security;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import myboot.myapp.dao.UserRepository;



/**
 * Configuration de Spring Security.
 */
@Configuration
@EnableWebSecurity
@Profile("usejwt")
public class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtProvider jwtTokenProvider;

	protected final Log logger = LogFactory.getLog(getClass());

	@PostConstruct
	public void init() {
		/*var encoder = passwordEncoder();
		var aa = new User("aaa", encoder.encode("aaa"), Set.of("ADMIN", "USER"));
		var bb = new User("bbb", encoder.encode("bbb"), Set.of("USER"));
		userRepo.save(aa);
		userRepo.save(bb);
		logger.debug("--- INIT SPRING SECURITY JWT");*/
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Pas de vérification CSRF (cross site request forgery)
		http.csrf().disable();

		// Spring security de doit gérer les sessions
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Déclaration des end-points
		http.authorizeRequests()//
				.antMatchers("/api/login").permitAll()//
				.antMatchers("/api/signup").permitAll()//
				// Autoriser le reste...
				.anyRequest().permitAll();

		// Pas vraiment nécessaire
		http.exceptionHandling().accessDeniedPage("/api/login");

		// Mise en place du filtre JWT
		http.apply(new JwtFilterConfigurer(jwtTokenProvider));

		// Optional, if you want to test the API from a browser
		// http.httpBasic();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
