package ru.innotechnum.controllers.database;

import org.springframework.data.repository.CrudRepository;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;

import java.util.List;

public interface PublicationRepository extends CrudRepository<Publication, Integer> {

    Publication findById(int id);

    List<Publication> findOrderByIdAsc();

    List<Publication> findOrderByIdDesc();

    List<Publication> findOrderByRaitingDesc();


    void saveAndFlush(Publication oldUser);

    void flush();
}