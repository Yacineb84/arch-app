package myboot.myapp.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import myboot.myapp.dao.MyActivityRepository;
import myboot.myapp.dao.MyCvRepository;
import myboot.myapp.dao.MyUserRepository;
import myboot.myapp.model.Activity;
import myboot.myapp.model.Cv;
import myboot.myapp.model.MyUser;

@SpringBootTest
public class TestCvRepository {
	
	@Autowired
	MyActivityRepository activityRepo;
	
	@Autowired
	MyCvRepository cvRepo;
	
	@Autowired
	MyUserRepository userRepo;
	
	@Test
	public void createAndRead() {
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		activities.add(activity);
		var u = cvRepo.save(new Cv(activities));
		var u2 = cvRepo.findById(u.getId());
		assertEquals(u2.get().getId(), u.getId());
	}
	
	@Test
	public void delete() {
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		activities.add(activity);
		var u = cvRepo.save(new Cv(activities));
		cvRepo.delete(u);
		var u2 = cvRepo.findById(u.getId());
		assertEquals(u2.isEmpty(), true);
	}
	
	@Test
	public void update() {
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		activities.add(activity);
		var u = cvRepo.save(new Cv(activities));
		
		Optional<Cv> curriculum = cvRepo.findById(u.getId());
		assertEquals(curriculum.get().getId(),u.getId());
		MyUser usr = new MyUser("Anis","Busse","anis@gmail.com","mon site", "07/10/1998", "mdp");
		
		curriculum.get().setUser(usr);
		usr.setCv(curriculum.get());
		userRepo.save(usr);
		cvRepo.save(curriculum.get());
		Optional<Cv> new_u = cvRepo.findById(u.getId());
		assertEquals(new_u.get().getUser().getName(), "Anis");
	}
	
	@Test
	public void createCvWithoutUser() {
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		activities.add(activity);
		var cv = cvRepo.save(new Cv(activities));
		var searched = cvRepo.findById(cv.getId());
		assertEquals(searched.get().getId(), cv.getId());
	}
	
	@Test
	public void createCvWithUser() {
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		activities.add(activity);
		Cv cv = new Cv(activities);
		MyUser u = new MyUser("Anis","Busse","anis@gmail.com","mon site", "07/10/1998", "mdp");
		userRepo.save(u);
		u.setCv(cv);
		cv.setUser(u);
		cvRepo.save(cv);		
		var searchedCv = cvRepo.findByUser(u);
		assertEquals(searchedCv.getUser().getName(),u.getName());
		
	}
	
	@Test
	public void deleteCvWhenUserDeleted() {
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		activities.add(activity);
		Cv cv = new Cv(activities);
		MyUser u = new MyUser("mike","tyson","mike@gmail.com","mon site", "07/10/1998", "mdp");
		userRepo.save(u);
		u.setCv(cv);
		cv.setUser(u);
		cvRepo.save(cv);
		userRepo.delete(u);
		//var u2 = cvRepo.findById(cv.getId());
		//assertEquals(u2.isEmpty(), true);
	}
	
	@Test
	public void deleteCv() {
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		activities.add(activity);
		Cv cv = new Cv(activities);
		cvRepo.save(cv);
		cvRepo.delete(cv);
		var u2 = cvRepo.findById(cv.getId());
		assertEquals(u2.isEmpty(), true);
	}
	
	@Test
	public void updateUserOfCv() {
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		activities.add(activity);
		Cv cv = new Cv(activities);
		
		MyUser u = userRepo.save(new MyUser("mike","tyson","mike@gmail.com","mon site", "07/10/1998", "mdp"));
		MyUser user = userRepo.save(new MyUser("Yacine","Boukhari","yac@gmail.com","mon site", "18/01/1998", "mdp"));
		
		cv.setUser(u);
		cvRepo.save(cv);
		var u2 = cvRepo.findById(cv.getId());
		assertEquals(u2.get().getUser().getName(), "mike");
		
		cv.setUser(user);
		cvRepo.save(cv);
		var u3 = cvRepo.findById(cv.getId());
		assertEquals(u3.get().getUser().getName(), "Yacine");
	}
	
	@Test
	public void updateCvActivities() {
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		activities.add(activity);
		activityRepo.save(activity);
		Cv cv = new Cv(activities);
		cvRepo.save(cv);
		assertEquals(cv.getActivities().size(), 1);
		activities.add(new Activity(2024,"sea","sunset","holiday","sea.com"));
		cv.setActivities(activities);
		cvRepo.save(cv);
		assertEquals(cv.getActivities().size(), 2);
		
	}
	
}
