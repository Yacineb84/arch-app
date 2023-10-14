package myboot.myapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import myboot.app1.model.Movie;
import myboot.myapp.model.MyUser;


@Repository
@Transactional
public interface MyUserRepository extends CrudRepository<MyUser, Long> {
	MyUser findByName(String name);
}
