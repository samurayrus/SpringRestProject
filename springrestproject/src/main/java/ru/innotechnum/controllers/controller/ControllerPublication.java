package ru.innotechnum.controllers.controller;

import org.springframework.http.HttpStatus;
import ru.innotechnum.controllers.database.PublicationRepository;
import ru.innotechnum.controllers.database.UserRepository;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public Iterable<Publication> getAllPublication() {
        return publicationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Publication getPublication(@PathVariable int id) {
        return publicationRepository.findById(id);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentAllPublication(@PathVariable int id) {
        return publicationRepository.findById(id).getCommentList();
    }

    @GetMapping("/byRaiting/")
    public List<Publication> getPublicationTop() {
        return publicationRepository.findTop10ByOrderByRaitingDesc();
    }

    @GetMapping("/byComments/")
    public List<Publication> getPublicationPopular() {
        List<Publication> listPubl = (List<Publication>) publicationRepository.findAll();
        return listPubl.stream().sorted(Comparator.comparing(x -> x.getCommentList().size())).limit(10).collect(Collectors.toList());
    }

    @GetMapping("/byNew/")
    public List<Publication> getPublicationNew() {
        return publicationRepository.findTop10ByOrderByIdDesc();
    }

    @GetMapping("/byOld/")
    public List<Publication> getPublicationOld() {
        return publicationRepository.findTop10ByOrderByIdAsc();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPub(@RequestBody Publication publ) {
        publ.setAuthorName(userRepository.findById(publ.getAuthorId()).getNickName());
        publicationRepository.save(publ);
    }

    @PostMapping("/{id}/addraiting")
    public String addPublicationRaiting(@PathVariable int id) {
        Publication publ = publicationRepository.findById(id);
        publ.setRaiting(publ.getRaiting() + 1);
        publicationRepository.flush();
        return "{New raiting =" + publ.getRaiting() + " }";
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
