package myboot.app3.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class TestHelloRestController {

	
	@Test
	public void testHello() {
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/hello";
		ResponseEntity<String> response
		  = restTemplate.getForEntity(url , String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody(), "Hello");
	}
	
	@Test
    public void testList() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> response =
				  restTemplate.getForEntity(
				  "http://localhost:8081/api/list",
				  List.class);
		List list = response.getBody();
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody(), Arrays.asList(10, 20, 30));
    }
    
	
	@Test
    public void testHelloWithMessage() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response =
				  restTemplate.getForEntity(
				  "http://localhost:8081/api/hello/Bonjour",
				  String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody(), "Hello " + "Bonjour");
    }
    
	
	@Test
    public void testHello2() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response =
				  restTemplate.getForEntity(
				  "http://localhost:8081/api/hello2",
				  String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody(), "Hello");
    }
    
	@Test
    public void testNotFound() {
		
		RestTemplate restTemplate = new RestTemplate();
		assertThrows(HttpClientErrorException.class, () -> {
			ResponseEntity<String> response =
				  restTemplate.getForEntity(
				  "http://localhost:8081/api/notFound",
				  String.class);
		});
    }
    
	@Test
    public void testNoContent() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response =
				  restTemplate.getForEntity(
				  "http://localhost:8081/api/noContent",
				  String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
}
