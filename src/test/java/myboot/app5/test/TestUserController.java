package myboot.app5.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import myboot.app5.web.XUserDTO;

@SpringBootTest
public class TestUserController {
/*
	@Test
	public void login() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity entity = new HttpEntity(headers);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8081/secu-users/login?username=aaa&password=aaa";
		
		ResponseEntity<String> response
		  = restTemplate.exchange(url,HttpMethod.POST,entity,String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

		String token = "Bearer " + response.getBody();;
		headers.set("Authorization", token);
		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		
		ResponseEntity<XUserDTO> meResponse = restTemplate.exchange("http://localhost:8081/secu-users/me", HttpMethod.GET, jwtEntity,
				XUserDTO.class);
		Assertions.assertEquals(meResponse.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(meResponse.getBody().getUsername(),"aaa");
	}
	*/
	@Test
	public void logout() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity entity = new HttpEntity(headers);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8081/secu-users/login?username=aaa&password=aaa";
		
		ResponseEntity<String> response
		  = restTemplate.exchange(url,HttpMethod.POST,entity,String.class);

		String token = "Bearer " + response.getBody();;
		headers.set("Authorization", token);
		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		
		ResponseEntity<ArrayList> meResponse = restTemplate.exchange("http://localhost:8081/secu-users/logout", HttpMethod.GET, jwtEntity,
				ArrayList.class);
		Assertions.assertEquals(meResponse.getStatusCode(), HttpStatus.OK);
		System.out.println("Mes token =========== " + meResponse.getBody());
	}
}
