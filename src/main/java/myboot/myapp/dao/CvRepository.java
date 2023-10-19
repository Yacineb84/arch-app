package myboot.myapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import myboot.myapp.model.Cv;
import myboot.myapp.model.User;

@Repository
@Transactional
public interface CvRepository extends CrudRepository<Cv, Long>{
	Cv findByUser(User user);
}
