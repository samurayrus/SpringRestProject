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

    @PostMapping("/{name}/{text}/{authorId}")
    public String addPub(@PathVariable String name, @PathVariable String text, @PathVariable int authorId) {
        Publication publ = new Publication(name, text, authorId);
        System.out.println(publ.toString());
        return dao.createPublication(publ);
    }

    @PutMapping("/{id}/{name}/{about}")
    public String editPublication(@PathVariable int id, @PathVariable String name, @PathVariable String about) {
        return dao.editPublication( id, name,about);
    }

    @DeleteMapping("/{id}")
    public String deletePublication(@PathVariable int id) {
        return dao.editPublication(id, "DELETED", "DELETED");
    }

    @PostMapping("/{id}")
    public String addPublicationRaiting(@PathVariable int id) {
        return dao.addRaiting(id);
    }
}
