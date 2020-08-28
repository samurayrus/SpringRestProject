package ru.innotechnum.controllers.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;

import java.util.List;

public interface PublicationRepository extends PagingAndSortingRepository<Publication, Integer> {        //extends CrudRepository<Publication, Integer> {

    Publication findById(int id);

    List<Publication> findTop10ByOrderByIdAsc();

    List<Publication> findTop10ByOrderByIdDesc();

    List<Publication> findTop10ByOrderByRaitingDesc();

    void saveAndFlush(Publication oldUser);

    //Page<Publication> findAll(Pageable pageable);

    void flush();
}