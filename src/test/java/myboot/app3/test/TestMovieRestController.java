package myboot.app3.test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import myboot.app1.model.Movie;
import myboot.app3.web.*;


public class TestMovieRestController {
	
	
	
	@Test
	public void testGetMovies() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8081/api/movies";
		Movie res = new Movie (1,"Star wars 4",1977,"Il y a bien longtemps, dans une galaxie lointaine, très lointaine...");
		ResponseEntity<Movie[]> response
		  = restTemplate.getForEntity(url , Movie[].class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody()[0],res);

	}
	
	@Test
	public void testGetMovie() {
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/movies/1";
		ResponseEntity<Movie> response
		  = restTemplate.getForEntity(url , Movie.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertTrue(response.getBody().toString().contains("Star wars 4"));	
	}
	
	@Test
	public void testBadGetMovie() throws MovieNotFoundException {
		RestTemplate restTemplate = new RestTemplate();
		assertThrows(HttpClientErrorException.class, () -> {
			ResponseEntity<Movie> response =
				  restTemplate.getForEntity(
				  "http://localhost:8081/api/movies/50",
				  Movie.class);
		});
	}
	
	@Test
	@Disabled
	public void testDeleteMovie() {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/movies/2";
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE,entity,Object.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);	
	}
	
	@Test
	public void testAddMovie() {
		Movie movie = new Movie (4,"Naruto",2023,"Les Aventures de Naruto...");
		
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/movies";
		ResponseEntity<Movie> response
		  = restTemplate.postForEntity(url , movie, Movie.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody(), movie);	
	}
	
	@Test
	public void testAddBadMovie() {
		Movie movie = new Movie (5,"Mauvais Film",1800,"Les Aventures de Naruto...");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8081/api/movies";
		
		assertThrows(HttpClientErrorException.class, ()-> {
			ResponseEntity<Movie> response
			= restTemplate.postForEntity(url , movie, Movie.class);
		});
	}
	
	@Test
	public void testPutMovie() {
		Movie movie = new Movie (3,"Naruto le Retour",2023,"Les Aventures de Naruto 5 ans plus tard...");
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/movies/3";
		HttpEntity entity = new HttpEntity(movie);
		ResponseEntity<Movie> response = restTemplate.exchange(url, HttpMethod.PUT,entity,Movie.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody(), movie);
	
	
	}
	
	@Test
	public void testPutBadMovie() {
		Movie movie = new Movie (10,"Naruto le Retour",2023,"Les Aventures de Naruto 5 ans plus tard...");
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/movies/10";
		assertThrows(HttpClientErrorException.class, ()-> {
			restTemplate.put(url , movie, Movie.class);
		});
	}
	
	@Test
	public void testAddMovieDTO() {
		MovieDTO movie = new MovieDTO (15,"Le film DTO",2003,"La bio...");
		
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://localhost:8081/api/movies";
		ResponseEntity<MovieDTO> response
		  = restTemplate.postForEntity(url , movie, MovieDTO.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody(), movie);	
	}
	
	@Test
	public void testAddBadMovieDTO() {
		Movie movie = new Movie (16,"Mauvais Film",1950,"Les Aventures de Naruto...");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8081/api/movies";
		
		assertThrows(HttpClientErrorException.class, ()-> {
			ResponseEntity<MovieDTO> response
			= restTemplate.postForEntity(url , movie, MovieDTO.class);
		});
	}
	
}

