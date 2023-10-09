package myboot.myapp.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import myboot.myapp.dao.MyCvRepository;

@SpringBootTest
public class TestCvRepository {
	
	@Autowired
	MyCvRepository r;

}
