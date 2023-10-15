package myboot.myapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import myboot.app3.model.User;
import myboot.myapp.model.Cv;
import myboot.myapp.model.MyUser;

@Repository
@Transactional
public interface MyCvRepository extends CrudRepository<Cv, Long>{
	Cv findByUser(MyUser user);
}
