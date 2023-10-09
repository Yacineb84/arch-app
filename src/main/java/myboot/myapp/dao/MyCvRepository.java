package myboot.myapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import myboot.myapp.model.Cv;

@Repository
@Transactional
public interface MyCvRepository extends CrudRepository<Cv, Long>{

}
