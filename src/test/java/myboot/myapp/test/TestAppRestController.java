package myboot.myapp.test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import myboot.myapp.dao.ActivityRepository;
import myboot.myapp.dao.CvRepository;
import myboot.myapp.dao.UserRepository;
import myboot.myapp.model.User;
import myboot.myapp.web.UserDTO;

@SpringBootTest
public class TestAppRestController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	CvRepository cvRepository;
	
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
}
