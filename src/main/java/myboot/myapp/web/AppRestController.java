package myboot.myapp.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springdoc.core.converters.models.MonetaryAmount;
import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.experimental.var;
import myboot.myapp.model.Activity;
import myboot.myapp.model.Cv;
import myboot.myapp.model.User;


@RestController
@RequestMapping("/api")
public class AppRestController {
	
	@Autowired
	private AppService appService;
	
	@Autowired
	LocalValidatorFactoryBean validationFactory;

	private ModelMapper modelMapper = new ModelMapper();
	
	@PostConstruct
	public void init() {
		System.out.println("Start " + this);
		if (appService.userRepository.count() == 0) {
			User user1 = new User("yac@gmail.com","Boukhari","Yacine","Mon site","18/01/1998","mdp");
			User user2 = new User("anis@gmail.com","Boussedra","Anis","Mon site Anis","25/08/1997","anis");
			User user3 = new User("fong@gmail.com","Fong","Cheko","Mon fongus","03/12/1995","fong");
			appService.addUser(user1);
			appService.addUser(user2);
			appService.addUser(user3);
			
		}
		
		if (appService.activityRepository.count() == 0) {
			Activity activity1 = new Activity(2023,"Stage","Developpeur Web","Stage de 6 mois chez Capgemini","capge.com");
			Activity activity2 = new Activity(2022,"Projet","Catalogue d'animé","Projet de génie logiciel","mrAnime.com");
			Activity activity3 = new Activity(2020,"Formation","React","Formation de 1mois sur le framework React","react.com");
			appService.addActivity(activity1);
			appService.addActivity(activity2);
			appService.addActivity(activity3);
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
			user.setCv(u.getCv());
			user.setPassword(u.getPassword());
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
		System.out.println("AVANT GET");
		Cv cv = appService.getCv(id);
		System.out.println("APRES GET");
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
	public List<ActivityDTO> getActivities() {
		var a = appService.getAllActivities();

		List<ActivityDTO> activities = modelMapper.map(a, new TypeToken<List<ActivityDTO>>() {
		}.getType());
		
		System.out.println(activities);
		return activities;
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
		appService.deleteActivity(id);
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

}
