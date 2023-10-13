package myboot.app3.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.experimental.var;
import myboot.app1.dao.MovieRepository;
import myboot.app1.model.Movie;
import myboot.app1.model.Views;

@RestController
@RequestMapping("/api")
public class MovieRestController {

	@Autowired
	MovieRepository repo;

	@Autowired
	LocalValidatorFactoryBean validationFactory;

//	@JsonView(Views.Public.class)
	@GetMapping("/movies")
	public List<MovieDTO> getMovies(@RequestParam(required = false, defaultValue = "%") String name,
			@RequestParam(required = false, defaultValue = "-1") int year) {
		ModelMapper modelMapper = new ModelMapper();
		var i = (repo.findByNameAndYear(name, year));

		List<MovieDTO> movies = modelMapper.map(i, new TypeToken<List<MovieDTO>>() {
		}.getType());
		return movies;
	}

	@JsonView(Views.Public.class)
	@GetMapping("/movies/{id}")
	public Movie getMovie(@PathVariable int id) {
		// Optional<Movie> opt = repo.findById(id);
		Movie movie = repo.findById(id).orElseThrow(() -> new MovieNotFoundException());
		return movie;

	}

	@DeleteMapping("/movies/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteMovie(@PathVariable int id) {
		repo.deleteById(id);
	}

	@PostMapping("/movies")
	public MovieDTO postMovie(@RequestBody @Valid MovieDTO m) {
		ModelMapper modelMapper = new ModelMapper();
		Movie movie = modelMapper.map(m, Movie.class);
		repo.save(movie);
		return m;
	}

	@SuppressWarnings("null")
	@PutMapping("/movies/{id}")
	public Map<String, String> putMovie(@RequestBody Movie m, @PathVariable int id) {
		Movie movie = repo.findById(id).orElseThrow(() -> new MovieNotFoundException());
		
		Set<ConstraintViolation<Movie>> errors = validationFactory.getValidator().validate(m);
		Map<String, String> map = new HashMap<String, String>();
		
		for(ConstraintViolation<Movie> c : errors) {
			System.out.println(c.getPropertyPath().toString());
			System.out.println(c.getMessage());
			map.put(c.getPropertyPath().toString(), c.getMessage());
		}
		if (errors.size() == 0) {
			movie.setName(m.getName());
			movie.setYear(m.getYear());
			movie.setDescription(m.getDescription());
			repo.save(movie);
		} 
		return map;
	}

	@PatchMapping("/movies")
	public void patchMovies() {
		Movie movie1 = new Movie("Dbz", 2018, "Les Aventures de Goku...");
		Movie movie2 = new Movie("Naruto", 2023, "Les Aventures de Naruto...");
		Movie movie3 = new Movie("One Piece", 2022, "Les Aventures de Luffy au Chapeau de Paille...");
		repo.save(movie1);
		repo.save(movie2);
		repo.save(movie3);
	}
}