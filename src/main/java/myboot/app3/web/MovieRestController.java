package myboot.app3.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

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

	@PutMapping("/movies/{id}")
	public Movie putMovie(@RequestBody @Valid Movie m, @PathVariable int id) {
		Movie movie = repo.findById(id).orElseThrow(() -> new MovieNotFoundException());

		movie.setName(m.getName());
		movie.setYear(m.getYear());
		movie.setDescription(m.getDescription());
		repo.save(movie);
		return movie;
	}
}