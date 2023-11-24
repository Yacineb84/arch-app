package myboot.myapp.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.DrbgParameters.NextBytes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.experimental.var;
import myboot.myapp.dao.ActivityRepository;
import myboot.myapp.dao.CvRepository;
import myboot.myapp.dao.UserRepository;
import myboot.myapp.model.Activity;
import myboot.myapp.model.Cv;
import myboot.myapp.model.User;
import myboot.myapp.web.ActivityDTO;
import myboot.myapp.web.AppService;
import myboot.myapp.web.CvDTO;
import myboot.myapp.web.UserDTO;

@SpringBootTest
public class TestAppRestController {
	@Autowired
	private AppService appService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	CvRepository cvRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Test
	public void testGetUsers() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8081/api/users";
		UserDTO res = new UserDTO("yac@gmail.com","Boukhari","Yacine","Mon site","18/01/1998",null);
		ResponseEntity<UserDTO[]> response
		  = restTemplate.getForEntity(url , UserDTO[].class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody()[2],res);

	}
	
	@Test
	public void testGetUser() {
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/users/yac@gmail.com";
		ResponseEntity<UserDTO> response
		  = restTemplate.getForEntity(url , UserDTO.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertTrue(response.getBody().toString().contains("Boukhari"));	
	}
	
	
	@Test
	public void testGetUserLike() {
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/users?search=Boukhari";
		ResponseEntity<UserDTO[]> response
		  = restTemplate.getForEntity(url , UserDTO[].class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertTrue(response.getBody()[0].toString().contains("Boukhari"));	
	}
	
	@Test
	public void testBadGetUser() {
		RestTemplate restTemplate = new RestTemplate();
		assertThrows(HttpClientErrorException.class, () -> {
			ResponseEntity<UserDTO> response =
				  restTemplate.getForEntity(
				  "http://localhost:8081/api/users/yacine@gmail.com",
				  UserDTO.class);
		});
	}
	
	
	@Test
	public void testDeleteUser() {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/users/fong@gmail.com";
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE,entity,Object.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);	
	}
	
	@Test
	public void testAddUser() {
		User user = new User ("naruto@gmail.com", "uzumaki","naruto","mon konoha","15/10/74","mdp");
		
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/users";
		ResponseEntity<User> response
		  = restTemplate.postForEntity(url , user, User.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody(), user);	
	}
	
	@Test
	public void testAddBadUser() {
		User user = new User ("", "uzumaki","naruto","mon konoha","15/10/74","mdp");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8081/api/users";
		
		assertThrows(HttpClientErrorException.class, ()-> {
			ResponseEntity<User> response
			= restTemplate.postForEntity(url , user, User.class);
		});
	}
	
	@Test
	public void testPutUser() {
		User user = new User ("naruto@gmail.com", "uchiha","sasuke","mon konoha","15/10/74","mdp");
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/users/naruto@gmail.com";
		HttpEntity entity = new HttpEntity(user);
		ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.PUT,entity,User.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		
		ResponseEntity<UserDTO> response2
		  = restTemplate.getForEntity("http://localhost:8081/api/users/naruto@gmail.com" , UserDTO.class);
		Assertions.assertEquals(response2.getBody().getName(), "uchiha");
	
	
	}
	
	@Test
	public void testPutBadUser() {
		User user = new User ("naruto@gmail.com", "uchiha","sakura","mon konoha","15/10/74","mdp");
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/users/narutoo@gmail.com";
		assertThrows(HttpClientErrorException.class, ()-> {
			restTemplate.put(url , user, User.class);
		});
	}
	
	/////////////////////////////////////////////
	
	@Test
	public void testGetActivities() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8081/api/activity";
		ActivityDTO res = new ActivityDTO(2023,"Stage","Developpeur Web","Stage de 6 mois chez Capgemini","capge.com");
		ResponseEntity<ActivityDTO[]> response
		  = restTemplate.getForEntity(url , ActivityDTO[].class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody()[0],res);

	}
	
	@Test
	public void testGetActivity() {
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/activity/1";
		ResponseEntity<ActivityDTO> response
		  = restTemplate.getForEntity(url , ActivityDTO.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertTrue(response.getBody().toString().contains("Stage"));	
	}
	
	@Test
	public void testBadGetActivity() {
		RestTemplate restTemplate = new RestTemplate();
		assertThrows(HttpClientErrorException.class, () -> {
			ResponseEntity<ActivityDTO> response =
				  restTemplate.getForEntity(
				  "http://localhost:8081/api/activity/50",
				  ActivityDTO.class);
		});
	}
	
	
	@Test
	public void testDeleteActivity() {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/activity/3";
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE,entity,Object.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);	
	}
	
	@Test
	public void testAddActivity() {
		Activity activity = new Activity(2019,"Stage","Developpeur React","Stage de 2 mois chez Sopra","sopra.com");
		
		ActivityDTO activityDto = modelMapper.map(activity, ActivityDTO.class);
		
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/activity";
		ResponseEntity<Activity> response
		  = restTemplate.postForEntity(url , activity, Activity.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		ActivityDTO res = modelMapper.map(response.getBody(), ActivityDTO.class);
		Assertions.assertEquals(res, activityDto);	
	}
	
	@Test
	public void testAddBadActivity() {
		Activity activity = new Activity(2023,"","Developpeur Web","Stage de 6 mois chez Capgemini","capge.com");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8081/api/activity";
		
		assertThrows(HttpClientErrorException.class, ()-> {
			ResponseEntity<Activity> response
			= restTemplate.postForEntity(url , activity, Activity.class);
		});
	}
	
	@Test
	public void testPutActivity() {
		Activity activity = new Activity(2023,"Stage","Developpeur Web","Stage de 5 mois chez Capgemini","capge.com");
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/activity/1";
		HttpEntity entity = new HttpEntity(activity);
		ResponseEntity<Activity> response = restTemplate.exchange(url, HttpMethod.PUT,entity,Activity.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		
		ResponseEntity<ActivityDTO> response2
		  = restTemplate.getForEntity("http://localhost:8081/api/activity/1" , ActivityDTO.class);
		Assertions.assertEquals(response2.getBody().getDescription(), "Stage de 5 mois chez Capgemini");
	
	
	}
	
	@Test
	public void testPutBadActivity() {
		Activity activity = new Activity(2023,"Stage","Developpeur Web","Stage de 5 mois chez Capgemini","capge.com");
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/activity/8";
		assertThrows(HttpClientErrorException.class, ()-> {
			restTemplate.put(url , activity, Activity.class);
		});
	}
	
	/////////////////////////////////////////////
		
	
	@Test
	public void testAddActivityToCvAndAddCvToUser() {
		RestTemplate restTemplate = new RestTemplate();
		
		User user = new User("bilal@gmail.com","Saidi","bilal","monSite","12/03/2008","mdp",null);
		Activity activity = new Activity(2023,"Développeur en entreprise","Developpeur Web Angular","3 ans chez CapgeMini","capge.com");
	
		restTemplate.postForEntity("http://localhost:8081/api/users" , user, User.class);
		restTemplate.postForEntity("http://localhost:8081/api/activity" , activity, Activity.class);

		String url2 = "http://localhost:8081/api/cv/1/user/bilal@gmail.com";
		
		Cv cv = new Cv(null,user);
		
		restTemplate.postForEntity("http://localhost:8081/api/cv" , cv, Cv.class);
		
		
		String url = "http://localhost:8081/api/cv/1/activity";
		
		ResponseEntity<CvDTO> response
		= restTemplate.postForEntity(url , activity, CvDTO.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().getActivities().get(0).getNature(),activity.getNature());
		
		ResponseEntity<UserDTO> response2
		= restTemplate.postForEntity(url2 ,null,UserDTO.class);
		Assertions.assertEquals(response2.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response2.getBody().getEmail(),user.getEmail());
		
	}

}
