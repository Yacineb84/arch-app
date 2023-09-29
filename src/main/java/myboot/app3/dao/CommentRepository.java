package myboot.app3.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import myboot.app3.model.Comment;

@Repository
@Transactional
public interface CommentRepository extends CrudRepository<Comment, Long> {
        
}