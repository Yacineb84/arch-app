package myboot.myapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import myboot.myapp.model.MyUser;


@Repository
@Transactional
public interface MyUserRepository extends CrudRepository<MyUser, Long> {

}
