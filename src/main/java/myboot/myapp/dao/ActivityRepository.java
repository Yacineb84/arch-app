package myboot.myapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import myboot.myapp.model.Activity;

@Repository
@Transactional
public interface ActivityRepository extends CrudRepository<Activity, Long>{

}
