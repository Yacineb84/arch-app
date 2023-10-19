package myboot.myapp.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import myboot.myapp.dao.ActivityRepository;
import myboot.myapp.dao.CvRepository;
import myboot.myapp.dao.UserRepository;
import myboot.myapp.model.Activity;
import myboot.myapp.model.Cv;
import myboot.myapp.model.User;

@SpringBootTest
public class TestCvRepository {
	
	@Autowired
	ActivityRepository activityRepo;
	
	@Autowired
	CvRepository cvRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Test
	public void createAndRead() {

		var u = userRepo.save(new User("Yacine","Boukhari","yac@gmail.com","mon site", "18/01/1998", "mdp"));
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		activityRepo.save(activity);
		activities.add(activity);
		var u1 = cvRepo.save(new Cv(activities,u));
		var u2 = cvRepo.findById(u1.getId());
		assertEquals(u2.get().getId(), u1.getId());
		assertEquals(userRepo.findById(u.getId()).get().getCv().getId(), u2.get().getId());
	}
	
	@Test
	public void delete() {
		var u = userRepo.save(new User("Yacine","Boukhari","yac@gmail.com","mon site", "18/01/1998", "mdp"));
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		activities.add(activity);
		activityRepo.save(activity);
		var cv = cvRepo.save(new Cv(activities,u));
		cvRepo.delete(cv);
		var cv2 = cvRepo.findById(u.getId());
		assertEquals(cv2.isEmpty(), true);
		var u2 = userRepo.findById(u.getId());
		assertEquals(u2.get().getCv(),null);
	}
	
	@Test
	@Transactional
	public void update() {
		var u = userRepo.save(new User("Yacine","Boukhari","yac@gmail.com","mon site", "18/01/1998", "mdp"));
		List<Activity> activities = new LinkedList<>();
		Activity activity = new Activity(2023,"nature","title","description","webaddress.com");
		Activity activity2 = new Activity(2023,"sport","title","description","webaddress.com");
		activities.add(activity);
		activityRepo.save(activity);
		activityRepo.save(activity2);
		var cv = cvRepo.save(new Cv(activities,u));
		activities.add(activity2);
		cv.setActivities(activities);
		cvRepo.save(cv);
		u.setCv(cv);
		userRepo.save(u);
		var cv2 = cvRepo.findById(cv.getId());
		

		assertEquals(cv2.get().getActivities().size(),2);
		assertEquals(userRepo.findById(u.getId()).get().getCv().getActivities().size(), cv2.get().getActivities().size());
	}
	
}
