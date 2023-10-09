package myboot.myapp.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.experimental.var;
import myboot.myapp.dao.MyUserRepository;
import myboot.myapp.model.MyUser;

@SpringBootTest
public class TestUserRepository {
	
	@Autowired
	MyUserRepository r;
	
	@Test
	public void save() {
		var u = r.save(new MyUser("Yacine","Boukhari","yac@gmail.com","mon site", "18/01/1998", "mdp"));
		var u2 = r.findById(u.getId());
		assertEquals(u2.get().getName(), "Yacine");
	}
	
	@Test
	public void remove() {
		MyUser u = new MyUser("Anis","Fong","anis@gmail.com","mon site", "18/08/1998", "mdp");
		r.save(u);
		r.delete(u);
		var u2 = r.findById(u.getId());
		assertEquals(u2.isEmpty(), true);
	}

}
