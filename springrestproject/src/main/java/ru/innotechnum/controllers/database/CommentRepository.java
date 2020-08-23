package ru.innotechnum.controllers.database;

import org.springframework.data.repository.CrudRepository;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.Publication;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    Comment findById(int id);

    void saveAndFlush(Comment oldUser);

    void flush();
}
