package ru.innotechnum.controllers.controller;

import ru.innotechnum.controllers.database.Dao;
import ru.innotechnum.controllers.database.PublicationRepository;
import ru.innotechnum.controllers.entity.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/publication", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerPublication {
    @Autowired
    private Dao dao;
    @Autowired
    private PublicationRepository publicationRepository;

    @GetMapping("/")
    public String getAllPublication() {
        return "{" + publicationRepository.findAll() + "}";
    }

    @GetMapping("/{id}")
    public String getPublication(@PathVariable int id) {
        return publicationRepository.findById(id).toString();
    }


    @GetMapping("/byRaiting/")
    public String getPublicationTop() {
        return dao.getAllPublicationRaiting().toString();
    }

    @GetMapping("/byComments/")
    public String getPublicationPopular() {
        return dao.getAllPublicationComments().toString();
    }

    @GetMapping("/byNew/")
    public String getPublicationNew() {
        return dao.getAllPublicationNew().toString();
    }

    @GetMapping("/byOld/")
    public String getPublicationOld() {
        return dao.getAllPublicationOld().toString();
    }

    @PostMapping("/")
    public String addPub(@RequestBody Publication publ) {
        publicationRepository.save(publ);
        return "saved";
    }

    @PostMapping("/raiting/{id}")
    public String addPublicationRaiting(@PathVariable int id) {
        Publication publ = publicationRepository.findById(id);
        publ.setRaiting(publ.getRaiting()+1);
        publicationRepository.flush();
        return "Now raiting =" + publ.getRaiting();
    }

    @PutMapping("/{id}/{name}/{about}")
    public String editPublication(@PathVariable int id, @PathVariable String name, @PathVariable String about) {
        return dao.editPublication(id, name, about);
    }

    @DeleteMapping("/{id}")
    public String deletePublication(@PathVariable int id) {
        return dao.editPublication(id, "DELETED", "DELETED");
    }




}
