package ru.innotechnum.controllers.database;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.innotechnum.controllers.entity.HistoryUser;
import ru.innotechnum.controllers.entity.Publication;

import java.time.LocalDate;
import java.util.List;

public interface HistoryUserRepository extends CrudRepository<HistoryUser, Integer> {

    HistoryUser findById(int id);

    List<HistoryUser> findTop10ByOrderByIdAsc();

    void flush();

   // @Query("select a from HISTORY a where a.date_begin <= :creationDateTime")
   // List<HistoryUser> findAllWithCreationDateTimeBefore(
  //          @Param("creationDateTime") LocalDate creationDateTime);
}
