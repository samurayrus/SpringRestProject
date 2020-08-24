package ru.innotechnum.controllers.database;

import org.springframework.data.repository.CrudRepository;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;

import java.util.List;

public interface PublicationRepository extends CrudRepository<Publication, Integer> {

    Publication findById(int id);

    // List<Publication> findAll();

    List<Publication> findTop10ByOrderByIdAsc();

    List<Publication> findTop10ByOrderByIdDesc();

    //findTop10ByOrderByLevelDesc

    List<Publication> findTop10ByOrderByRaitingDesc();


    void saveAndFlush(Publication oldUser);

    void flush();
}