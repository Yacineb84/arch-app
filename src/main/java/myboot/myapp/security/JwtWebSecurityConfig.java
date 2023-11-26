package myboot.myapp.security;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import myboot.myapp.dao.UserRepository;
import myboot.myapp.model.Activity;
import myboot.myapp.model.Cv;
import myboot.myapp.model.User;
import myboot.myapp.web.AppService;

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
	
	@Autowired
	private AppService appService;

	protected final Log logger = LogFactory.getLog(getClass());

	@PostConstruct
	public void init() {
		var encoder = passwordEncoder();
		
		String[] activity_nature = {"Stage","Expérience_professionnel","Projet","Formation"};
		
		String[] activity_title = {"Développement en React","Développement en Angular","Développement en VueJs", "Administration Base de Données",
				"Sécurité d'une application", "Application web", "Front-end", "Back-end"};

		Activity[] list_activities = new Activity[50];
		for(int i = 0; i < 50; i++) {
			
			int n = (int) (Math.random() * 4);
			int m = (int) (Math.random() * 8);
			int year = (int) ((Math.random() * 23) + 2000);
			String nature = activity_nature[n];
			String title = activity_title[m];
			String description = "La description du " + activity_nature[n] + " avec " + activity_title[m] ;
			String webAddress = "www."+ activity_nature[n] +".fr";
			list_activities[i] = new Activity(year,nature,title,description,webAddress);
			
		}
		
		User user = userRepo.save(new User("yac@gmail.com","Boukhari","Yacine","mrAnime.com","18/01/1998",encoder.encode("mdp")));
		Cv cv_user = user.getCv();
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		appService.addActivityToCv(cv_user, list_activities[(int) (Math.random() * 50)]);
		
		appService.addCvToUser(cv_user, user);

		logger.debug("--- INIT SPRING SECURITY JWT");
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
				.antMatchers("/api/signup").authenticated()//
				.antMatchers(HttpMethod.POST,"/api/cv/**").permitAll()//
				.antMatchers("/api/**").permitAll()//
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
