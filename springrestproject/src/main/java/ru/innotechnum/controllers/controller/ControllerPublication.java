package ru.innotechnum.controllers.controller;

import ru.innotechnum.controllers.database.Dao;
import ru.innotechnum.controllers.entity.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/publication", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerPublication {
    @Autowired
    private Dao dao;

    @GetMapping("/")
    public String getAllPublication() {
        return "{" + dao.getAllPublicationNew() + "}";
    }

    @GetMapping("/{id}")
    public String getPublication(@PathVariable int id) {
        return dao.getPublication(id).toString();
    }
    @GetMapping("/user/{id}")
    public String getPublicationAllForAuthor(@PathVariable int id) {
        return null;
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
        return dao.createPublication(publ);
    }

    @PostMapping("/raiting/{id}")
    public String addPublicationRaiting(@PathVariable int id) {
        return dao.addRaiting(id);
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
