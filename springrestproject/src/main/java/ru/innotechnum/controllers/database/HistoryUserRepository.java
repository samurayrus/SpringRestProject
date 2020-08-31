package ru.innotechnum.controllers.database;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.innotechnum.controllers.entity.HistoryUser;
import ru.innotechnum.controllers.entity.Publication;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

public interface HistoryUserRepository extends CrudRepository<HistoryUser, Integer> {

    HistoryUser findById(int id);

    List<HistoryUser> findTop10ByOrderByIdAsc();

    void flush();

   // @Query(value="from HISTORY where '2020-08-22' BETWEEN date_begin AND date_end") //WHERE start >= '2013-07-22' AND end <= '2013-06-13'
  // @Query(value="from HISTORY where date_begin>=?1")

   // @Query("select d from History d where d.dateBegin=date")
   // List<HistoryUser> findByLastUpdatedInDate(@Param("date") LocalDate date);

    //List<HistoryUser> findTop10Byuser_idWhereBetweendate_beginAnddate_end(int id, LocalDate date);


    //List<HistoryUser> findTop10Byuser_idBetweendate_beginAnddate_end(int user_id, LocalDate date);

    //@Query(value="from HISTORY where thisdate BETWEEN : date_begin AND :date_end")
  //  public List<HistoryUser> getForDates(@Param("thisdate") LocalDate thisDate);

   // @Query("select a from HISTORY a where a.date_begin <= :creationDateTime")
   // List<HistoryUser> findAllWithCreationDateTimeBefore(
  //          @Param("creationDateTime") LocalDate creationDateTime);
}
