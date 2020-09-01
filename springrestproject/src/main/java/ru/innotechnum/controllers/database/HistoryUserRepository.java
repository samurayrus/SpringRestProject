package ru.innotechnum.controllers.database;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.innotechnum.controllers.entity.HistoryUser;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

public interface HistoryUserRepository extends CrudRepository<HistoryUser, Integer> {

    HistoryUser findById(int id);

    List<HistoryUser> findTop10ByOrderByIdAsc();

    void flush();

    @Query("from HistoryUser h where h.user = :author AND h.dateBegin >= :date AND h.dateEnd <= :date")
     HistoryUser findByUser(@Param("date") LocalDate date, @Param("author") User user);

}