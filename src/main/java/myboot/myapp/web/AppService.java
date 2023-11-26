package myboot.myapp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.experimental.var;
import myboot.myapp.dao.ActivityRepository;
import myboot.myapp.dao.CvRepository;
import myboot.myapp.dao.UserRepository;
import myboot.myapp.model.Activity;
import myboot.myapp.model.Cv;
import myboot.myapp.model.User;

@Service
public class AppService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	CvRepository cvRepository;
	
	public Cv addCv(Cv cv) {
		return cvRepository.save(cv);
	}
	
	public Cv getCv(Long id) {
		return cvRepository.findById(id).orElseThrow(() -> new NotFoundException());
	}
	
	public Cv getCvByUser(User user) {
		return cvRepository.findByUser(user);
	}
	
	public void deleteCv(Long id) {
		cvRepository.deleteById(id);
	}
	
	public User addUser(User user) {
		if (userRepository.findByEmail(user.getEmail()) == null) {
			user = new User(user.getEmail(),user.getName(),user.getFirstName(),user.getSite(),user.getDateOfBirth(),user.getPassword());
		}
		var u = userRepository.save(user);
		return u;
	}
	
	public void deleteUser(String email) {
		userRepository.deleteById(email);
	}
	
	public User getUser(String email) {
		return userRepository.findById(email).orElseThrow(() -> new NotFoundException());
	}
	
	public List<User> getAllUser(){
		return (List<User>) userRepository.findAll();
	}
	
	public List<User> getUsersLike(String search){
		search = search.toUpperCase();
		return (List<User>) userRepository.findByNameLike(search);
	}
	
	/////////////////////////////////////////////
	
	public Cv addActivityToCv(Cv cv, Activity activity) {
		activityRepository.save(activity);
		activity.setCv(cv);
		Activity a = activityRepository.save(activity);
		var c = cvRepository.findById(a.getCv().getId()).get();
		return c;
	}
		
	public void removeActivityToCv(Cv cv, Activity activity) {
		var activities = cv.getActivities();//		activities.remove(activity);
//		cv.setActivities(activities);
		activity.setCv(null);
		activityRepository.save(activity);
//		activityRepository.delete(activity);
		cv = cvRepository.save(cv);
	}
	
	public User addCvToUser(Cv cv, User user) {
		user = userRepository.save(user);
		cv.setUser(user);
		cvRepository.save(cv);
		User user2 = userRepository.findByEmail(user.getEmail());
		return user2;
	}
	
	public void removeCvToUser(Cv cv, User user) {
		user.setCv(null);
		userRepository.save(user);
		cvRepository.delete(cv);
	}
	
	/////////////////////////////////////////////
	
	public Activity getActivity(Long id) {
		return activityRepository.findById(id).orElseThrow(() -> new NotFoundException());
	}
	
	public List<Activity> getAllActivities(){
		List<Activity> activities = (List<Activity>) activityRepository.findAll();
		return (List<Activity>) activityRepository.findAll();
	}
	
	public List<Activity> getActivitiesLike(String search){
		search = search.toUpperCase();
		return (List<Activity>) activityRepository.findByNameLike(search);
	}
	
	public Activity addActivity(Activity activity) {
		return activityRepository.save(activity);
	}
	
	public void deleteActivity(Long id) {
		activityRepository.deleteById(id);
	}
	
	/////////////////////////////////////////////

	
}
