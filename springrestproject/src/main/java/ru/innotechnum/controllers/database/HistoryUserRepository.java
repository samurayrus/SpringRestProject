package ru.innotechnum.controllers.database;

import org.springframework.data.repository.CrudRepository;
import ru.innotechnum.controllers.entity.HistoryUser;
import ru.innotechnum.controllers.entity.Publication;

import java.util.List;

public interface HistoryUserRepository extends CrudRepository<HistoryUser, Integer> {

    HistoryUser findById(int id);

    List<HistoryUser> findTop10ByOrderByIdAsc();

    void flush();
}
