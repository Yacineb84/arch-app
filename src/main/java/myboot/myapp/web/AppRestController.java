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
	}

	@PreDestroy
	public void destroy() {
	}
	
	@GetMapping("/users")
	public List<UserDTO> getUsers(@RequestParam(required = false, defaultValue = "%") String name) {
		var u = appService.getUsersLike(name);

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
	public void patchMovies() {
		User user1 = new User("yac@gmail.com","Boukhari","Yacine","Mon site","18/01/1998","mdp");
		User user2 = new User("anis@gmail.com","Boussedra","Anis","Mon site Anis","25/08/1997","anis");
		User user3 = new User("fong@gmail.com","Fong","Cheko","Mon fongus","03/12/1995","fong");
		appService.addUser(user1);
		appService.addUser(user2);
		appService.addUser(user3);
	}

}
