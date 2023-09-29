package myboot.app3.web;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import myboot.app3.model.*;
import myboot.app1.model.Movie;
import myboot.app3.dao.CommentRepository;
import myboot.app3.dao.PostRepository;
import myboot.app3.dao.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepository userdao;

	@Autowired
	PostRepository postdao;
	
	@Autowired
	CommentRepository commentdao;

	@PostConstruct
	public void init() {
		System.out.println("Start " + this);
		if (userdao.count() == 0) {
			User user = new User("Yacine","yac@gmail.com","ma bio de Yacine");
			User user2 = new User("MrFong","MrFong@gmail.com","ma bio de Fongus");
			User user3 = new User("Anis","anis@gmail.com","ma bio de Anis");
			Post post = new Post("Fifa",user);
			Post post2 = new Post("La Chine",user2);
			Comment comment = new Comment("Reply",post,user);
			Comment comment2 = new Comment("Reply de Anis",post2,user3);

			userdao.save(user);
			postdao.save(post);
			commentdao.save(comment);
			userdao.save(user);
			
			userdao.save(user2);
			userdao.save(user3);
			postdao.save(post2);
			commentdao.save(comment2);
		}
	}

	@PreDestroy
	public void destroy() {
	}
	
	@Operation(summary = "Get a user by its id")
	@ApiResponses
	@GetMapping("/users/{id}")
    public Optional<User> getUser(@Parameter(description = "id of user to be searched")@PathVariable long id) {
    	return userdao.findById(id);
    	
    }

}
