package myboot.myapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myboot.myapp.dao.ActivityRepository;
import myboot.myapp.dao.CvRepository;
import myboot.myapp.dao.UserRepository;
import myboot.myapp.model.User;

@Service
public class AppService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	CvRepository cvRepository;
	
	public void addUser(User user) {
		
		
	}
}
