package ru.innotechnum.controllers.database;

import org.springframework.data.repository.CrudRepository;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;

public interface PublicationRepository extends CrudRepository<Publication, Integer> {

    Publication findById(int id);

    void saveAndFlush(Publication oldUser);

    void flush();
}