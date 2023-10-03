package myboot.app4.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepositoryBis extends PagingAndSortingRepository<WebsiteUser, Long> {
    List<WebsiteUser> findByName(@Param("name") String name);
}
