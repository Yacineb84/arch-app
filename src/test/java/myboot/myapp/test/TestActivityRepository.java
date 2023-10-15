package myboot.myapp.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import myboot.myapp.dao.MyActivityRepository;
import myboot.myapp.model.Activity;

@SpringBootTest
public class TestActivityRepository {
	
	@Autowired
	MyActivityRepository r;
	
	@Test
	public void createActivity() {
		var u = r.save(new Activity(2023,"nature","title","description","webaddress.com"));
		var u2 = r.findById(u.getId());
		assertEquals(u2.get().getNature(), "nature");
	}
	
	@Test
	public void deleteActivity() {
		Activity u = new Activity(2023,"nature","title","description","webaddress.com");
		r.save(u);
		r.delete(u);
		var u2 = r.findById(u.getId());
		assertEquals(u2.isEmpty(), true);
	}
	
	@Test
	public void updateActivity() {
		Activity activity = r.save( new Activity(2023,"nature","title","description","webaddress.com"));
		Optional<Activity> u = r.findById(activity.getId());
		assertEquals(u.get().getNature(), "nature");
		u.get().setNature("industry");
		r.save(u.get());
		Optional<Activity> new_u = r.findById(activity.getId());
		assertEquals(new_u.get().getNature(), "industry");
	}
}
