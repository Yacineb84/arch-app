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
	
	public void addCv(Cv cv) {
		cvRepository.save(cv);
	}
	
	public Cv getCv(User user) {
		return cvRepository.findByUser(user);
	}
	
	public void deleteCv(Cv cv) {
		cvRepository.delete(cv);
	}
	
	public User addUser(User user) {
		return userRepository.save(user);
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
	
	public List<User> getUsersLike(String name){
		return (List<User>) userRepository.findByNameLike(name);
	}
	
	/////////////////////////////////////////////
	
	public void addActivityToCv(Cv cv, Activity activity) {
		activityRepository.save(activity);
		var a = cv.getActivities();
		a.add(activity);
		cv.setActivities(a);
		cvRepository.save(cv);
	}
	
	/////////////////////////////////////////////
	
	public Activity getActivity(Long id) {
		return activityRepository.findById(id).orElseThrow(() -> new NotFoundException());
	}
	
	public List<Activity> getAllActivities(){
		List<Activity> activities = (List<Activity>) activityRepository.findAll();
		System.out.println("LES ACTIVITES ==== " + activities);
		return (List<Activity>) activityRepository.findAll();
	}
	
	public Activity addActivity(Activity activity) {
		return activityRepository.save(activity);
	}
	
	public void deleteActivity(Long id) {
		activityRepository.deleteById(id);
	}
	
	/////////////////////////////////////////////

	
}
