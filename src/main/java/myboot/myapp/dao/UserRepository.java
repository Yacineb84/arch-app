package myboot.myapp.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import antlr.collections.List;
import myboot.myapp.model.User;


@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, String> {
	User findByName(String name);
	
	User findByMail(String mail);
}
