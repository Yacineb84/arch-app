package myboot.myapp.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import myboot.myapp.dao.UserRepository;
import myboot.myapp.model.User;

@SpringBootTest
public class TestUserRepository {
	
	@Autowired
	UserRepository r;
	
	@Test
	public void createAndRead() {
		var u = r.save(new User("Yacine","Boukhari","yac@gmail.com","mon site", "18/01/1998", "mdp"));
		var u2 = r.findById(u.getId());
		assertEquals(u2.get().getName(), "Yacine");
	}
	
	@Test
	public void delete() {
		User u = new User("Anis","Busse","anis@gmail.com","mon site", "18/08/1998", "mdp");
		r.save(u);
		r.delete(u);
		var u2 = r.findById(u.getId());
		assertEquals(u2.isEmpty(), true);
	}
	
	@Test
	public void update() {
		User user = r.save(new User("Yacine","Boukhari","yac@gmail.com","mon site", "18/01/1998", "mdp"));
		Optional<User> u = r.findById(user.getId());
		assertEquals(u.get().getName(), "Yacine");
		u.get().setName("Chong");
		r.save(u.get());
		Optional<User> new_u = r.findById(user.getId());
		assertEquals(new_u.get().getName(), "Chong");
	}

}
