package myboot.app1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import myboot.app1.model.Movie;

@Repository
@Transactional
public interface MovieRepository extends CrudRepository<Movie, Integer> {

	Iterable<Movie> findByName(String name);

	Iterable<Movie> findByNameLike(String name);
	
	@Query("select m from Movie m where (m.name like :name) and (:year<0 or :year=m.year)")
	Iterable<Movie> findByNameAndYear(String name, int year);
	
	Iterable<Movie> findByYear(int year);

}