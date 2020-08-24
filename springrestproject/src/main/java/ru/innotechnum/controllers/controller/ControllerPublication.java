package ru.innotechnum.controllers.controller;

import ru.innotechnum.controllers.database.Dao;
import ru.innotechnum.controllers.database.PublicationRepository;
import ru.innotechnum.controllers.database.UserRepository;
import ru.innotechnum.controllers.entity.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/publication", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerPublication {
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String getAllPublication() {
        return "{" + publicationRepository.findAll() + "}";
    }

    @GetMapping("/{id}")
    public String getPublication(@PathVariable int id) {
        return publicationRepository.findById(id).toString();
    }

    @GetMapping("/comments/{id}")
    public String getCommentAllPublication(@PathVariable int id) {
        return publicationRepository.findById(id).getCommentList().toString();
    }

    @GetMapping("/byRaiting/")
    public String getPublicationTop() {
        return publicationRepository.findTop10ByOrderByRaitingDesc().toString();
    }

    @GetMapping("/byComments/")
    public String getPublicationPopular() {
        List<Publication> listPubl = (List<Publication>) publicationRepository.findAll();
        return listPubl.stream().sorted(Comparator.comparing(x -> x.getCommentList().size())).limit(10).collect(Collectors.toList()).toString();
    }

    @GetMapping("/byNew/")
    public String getPublicationNew() {
        return publicationRepository.findTop10ByOrderByIdDesc().toString();
    }

    @GetMapping("/byOld/")
    public String getPublicationOld() {
        return publicationRepository.findTop10ByOrderByIdAsc().toString();
    }

    @PostMapping("/")
    public String addPub(@RequestBody Publication publ) {
        publ.setAuthorName(userRepository.findById(publ.getAuthorId()).getNickName());
        publicationRepository.save(publ);
        return "saved";
    }

    @PostMapping("/raiting/{id}")
    public String addPublicationRaiting(@PathVariable int id) {
        Publication publ = publicationRepository.findById(id);
        publ.setRaiting(publ.getRaiting() + 1);
        publicationRepository.flush();
        return "Now raiting =" + publ.getRaiting();
    }

    @PutMapping("/{id}")
    public String editPublication(@PathVariable int id, @RequestBody Publication publ) {
        if (publicationRepository.existsById(id)) {
            Publication orig = publicationRepository.findById(id);
            if (publ.getName() != null)
                orig.setName(publ.getName());
            if (publ.getText() != null)
                orig.setText(publ.getText());
            publicationRepository.flush();
            return orig.toString();
        } else
            return "NotFound";
    }

    @DeleteMapping("/{id}")
    public String deletePublication(@PathVariable int id) {
        Publication publ = new Publication();
        publ.setName("DELETED");
        publ.setText("DELETED");
        return editPublication(id, publ);
    }
}
