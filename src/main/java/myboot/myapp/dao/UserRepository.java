package myboot.myapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import myboot.myapp.model.User;


@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, String> {
	User findByName(String name);
	
	User findByEmail(String email);
	
	@Query("select u from User u where (UPPER(u.name) like %:search%) or (UPPER(u.firstName) like %:search%)")
	Iterable<User> findByNameLike(String search);
}
