package ru.innotechnum.controllers.database;

import org.springframework.data.repository.CrudRepository;
import ru.innotechnum.controllers.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findById(int id);

    void saveAndFlush(User oldUser);

    void flush();

}
