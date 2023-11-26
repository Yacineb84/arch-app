package myboot.myapp.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import myboot.myapp.model.Activity;
import myboot.myapp.model.Cv;
import myboot.myapp.model.User;
import myboot.myapp.security.UserService;


@RestController
@RequestMapping("/api")
@Profile("usejwt")
public class AppRestController {
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	LocalValidatorFactoryBean validationFactory;

	private ModelMapper modelMapper = new ModelMapper();
	
	@PostConstruct
	public void init() {
		System.out.println("Start " + this);
		String[] users_firstname = {"Yacine","Nour","Assia","Kyllian","Sarah",
				"Anis","Qylyane","Charazade","Mona","Eren","Mehdi","Bilal","Thierry","Gérard","Françoise","Cyril","Harchi",
				"Mynnie","Myriam","Hafida","Apagnan","Crampter","Luc","Jean","Pascale","Pascal","Herver","Isabel","Leonard","Ninho"};
		
		String[] users_name = {"Boukhari","Boussedra","Bernardi","Jager","Werner","Parmentier","Chanssoi","Makela","Ornaran",
				"Saidi","Oujjet","Plaisance","Espoir","Massat","Hoareau","Fridja","Dufrene","Hebari","Nebari","Ronaldo","Messi",
				"Bougheraba","Inosuke","Tanjiro","Macaron","Lalou","Natoo","Pero","Silva","Rodriguez"
		};
		
		String[] activity_nature = {"Stage","Expérience_professionnel","Projet","Formation"};
		
		String[] activity_title = {"Développement en React","Développement en Angular","Développement en VueJs", "Administration Base de Données",
				"Sécurité d'une application", "Application web", "Front-end", "Back-end"};
		
		User[] list_users = new User[100000];
		Activity[] list_activities = new Activity[1000];
		for(int i = 0; i < 1000; i++) {
			
			int n = (int) (Math.random() * 4);
			int m = (int) (Math.random() * 8);
			int year = (int) ((Math.random() * 23) + 2000);
			String nature = activity_nature[n];
			String title = activity_title[m];
			String description = "La description du " + activity_nature[n] + " avec " + activity_title[m] ;
			String webAddress = "www."+ activity_nature[n] +".fr";
			list_activities[i] = new Activity(year,nature,title,description,webAddress);
			
		}
		
		for(int i = 0; i < 100000; i++) {
			int n = (int) (Math.random() * 30);
			int p = (int) (Math.random() * 30);
			int m = (int) (Math.random() * 100);
			String email = users_name[n] + users_firstname[p] + m + "@gmail.com";
			String name = users_name[n];
			String firstName = users_firstname[p];
			String site = "lesitede" + name + "-" + firstName + "-" + m +".fr";
			String dateOfBirth = ((int)(Math.random() * 28) + 1) +"/" + ((int)(Math.random() * 12) + 1) +"/" + ((int) (Math.random() * 21) + 1980);
			list_users[i] = appService.addUser(new User(email,name,firstName,site,dateOfBirth,"mdp"));
			
			
			Cv cv = list_users[i].getCv();
			appService.addActivityToCv(cv, list_activities[(int) (Math.random() * 1000)]);
			appService.addActivityToCv(cv, list_activities[(int) (Math.random() * 1000)]);
			appService.addActivityToCv(cv, list_activities[(int) (Math.random() * 1000)]);
			appService.addActivityToCv(cv, list_activities[(int) (Math.random() * 1000)]);
			
			appService.addCvToUser(cv, list_users[i]);
			
		}
		
	}

	@PreDestroy
	public void destroy() {
	}
	
	@GetMapping("/users")
	public List<UserDTO> getUsers(@RequestParam(required = false, defaultValue = "%") String search) {
		var u = appService.getUsersLike(search);

		List<UserDTO> users = modelMapper.map(u, new TypeToken<List<UserDTO>>() {
		}.getType());
		Collections.shuffle(users);

		return users;
	}
	
	@GetMapping("/users/{email}")
	public UserDTO getUser(@PathVariable String email) {
		var u = appService.getUser(email);
		UserDTO user = modelMapper.map(u, UserDTO.class);
		return user;
	
	}
	
	@DeleteMapping("/users/{email}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteUser(@PathVariable String email) {
		appService.deleteUser(email);
	}
	
	@PostMapping("/users")
	public User postUser(@RequestBody @Valid User u) {
		return appService.addUser(u);
	}
	
	@PutMapping("/users/{email}")
	public Map<String,String> putUser(@RequestBody User u, @PathVariable String email) {
		Set<ConstraintViolation<User>> errors = validationFactory.getValidator().validate(u);
		Map<String, String> map = new HashMap<String, String>();
		User user = appService.getUser(email);
		
		for(ConstraintViolation<User> c : errors) {
			System.out.println(c.getPropertyPath().toString());
			System.out.println(c.getMessage());
			map.put(c.getPropertyPath().toString(), c.getMessage());
		}
		if (errors.size() == 0) {
			user.setName(u.getName());
			user.setFirstName(u.getFirstName());
			user.setSite(u.getSite());
			//user.setCv(u.getCv());
			user.setDateOfBirth(u.getDateOfBirth());
			if (u.getPassword() != null)user.setPassword(u.getPassword());
			
			appService.addUser(user);
		} 
		return map;
	}
	
	@PatchMapping("/users")
	public void patchUser() {
		User user1 = new User("yac@gmail.com","Boukhari","Yacine","Mon site","18/01/1998","mdp");
		User user2 = new User("anis@gmail.com","Boussedra","Anis","Mon site Anis","25/08/1997","anis");
		User user3 = new User("fong@gmail.com","Fong","Cheko","Mon fongus","03/12/1995","fong");
		appService.addUser(user1);
		appService.addUser(user2);
		appService.addUser(user3);
	}
	/////////////////////////////////////////////
	
	@PostMapping("/cv")
	public Cv postCv(@RequestBody @Valid Cv cv) {
		return appService.addCv(cv);
	}
	
	@PostMapping("/cv/{id}/activity")
	public CvDTO postActivityToCv(@PathVariable Long id,@RequestBody @Valid Activity activity) {
		Cv cv = appService.getCv(id);
		appService.addActivityToCv(cv, activity);
		CvDTO cvDTO = modelMapper.map(appService.getCv(id), CvDTO.class);
		return cvDTO;
	}
	
	@PostMapping("/cv/{id}/user/{email}")
	public UserDTO postCvToUser(@PathVariable Long id, @PathVariable String email) {
		var user = appService.getUser(email);
		var cv = appService.getCv(id);
		appService.addCvToUser(cv, user);
		UserDTO userDTO = modelMapper.map(appService.getUser(email), UserDTO.class);
		return userDTO;
	}
	
	@DeleteMapping("/cv/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteCv(@PathVariable Long id) {
		appService.deleteCv(id);
	}
	
	/////////////////////////////////////////////
	
	@GetMapping("/activity")
	public List<UserDTO> getActivities(@RequestParam(required = false, defaultValue = "%") String search) {
		var a = appService.getActivitiesLike(search);
		List<User> users = new LinkedList<User>();
		for (Activity activity : a) {
			users.add(activity.getCv().getUser());
		}
		return modelMapper.map(users, new TypeToken<List<UserDTO>>() {
		}.getType());
	}
	
	@GetMapping("/activity/{id}")
	public ActivityDTO getActivity(@PathVariable Long id) {
		var a = appService.getActivity(id);
		ActivityDTO activity = modelMapper.map(a, ActivityDTO.class);
		return activity;
	
	}
	
	@DeleteMapping("/activity/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteActivity(@PathVariable Long id) {
		var a = appService.getActivity(id);
		var c = appService.getCv(a.getCv().getId());
		appService.removeActivityToCv(c, a);
	}
	
	@PostMapping("/activity")
	public Activity postActivity(@RequestBody @Valid Activity a) {
		return appService.addActivity(a);
	}
	
	@PutMapping("/activity/{id}")
	public Map<String,String> putActivity(@RequestBody Activity a, @PathVariable Long id) {
		Set<ConstraintViolation<Activity>> errors = validationFactory.getValidator().validate(a);
		Map<String, String> map = new HashMap<String, String>();
		Activity activity = appService.getActivity(id);
		
		for(ConstraintViolation<Activity> c : errors) {
			System.out.println(c.getPropertyPath().toString());
			System.out.println(c.getMessage());
			map.put(c.getPropertyPath().toString(), c.getMessage());
		}
		if (errors.size() == 0) {
			activity.setDescription(a.getDescription());
			activity.setNature(a.getNature());
			activity.setTitle(a.getTitle());
			activity.setWebAddress(a.getWebAddress());
			activity.setYear(a.getYear());
			appService.addActivity(activity);
		} 
		return map;
	}
	
	@PatchMapping("/activity")
	public void patchActivities() {
		Activity activity1 = new Activity(2023,"Stage","Developpeur Web","Stage de 6 mois chez Capgemini","capge.com");
		Activity activity2 = new Activity(2022,"Projet","Catalogue d'animé","Projet de génie logiciel","mrAnime.com");
		Activity activity3 = new Activity(2020,"Formation","React","Formation de 1mois sur le framework React","react.com");
		appService.addActivity(activity1);
		appService.addActivity(activity2);
		appService.addActivity(activity3);
	}
	
	/////////////////////////////////////////////

	/**
	 * Authentification et récupération d'un JWT
	 */
	@PostMapping("/login")
	public String login(//
			@RequestParam String email, //
			@RequestParam String password) {
		System.out.println("Je fais login");
		return userService.login(email, password);
	}

	/**
	 * Ajouter un utilisateur
	 */
	@PostMapping("/signup")
	public String signup(@RequestBody User user) {
		return userService.signup(user);
	}

	/**
	 * Supprimer un utilisateur
	 */
	@DeleteMapping("/{username}")
	public String delete(@PathVariable String email) {
		System.out.println("delete " + email);
		userService.delete(email);
		return email;
	}

	/**
	 * Récupérer des informations sur un utilisateur
	 */
	@GetMapping("/{email}")
	public UserDTO search(@PathVariable String email) {
		return modelMapper.map(userService.search(email), UserDTO.class);
	}

	/**
	 * Récupérer des informations sur l'utilisateur courant
	 */
	@GetMapping(value = "/me")
	public UserDTO whoami(HttpServletRequest req) {
		return modelMapper.map(userService.whoami(req), UserDTO.class);
	}

	/**
	 * Récupérer un nouveau JWT
	 */
	@GetMapping("/refresh")
	public String refresh(HttpServletRequest req) {
		return userService.refresh(req.getRemoteUser());
	}
	
	/**
	 * Oublie d'un JWT
	 */
	@GetMapping("/logout")
	public List<String> logout(HttpServletRequest req) {
		return userService.logout(req);
	}

}