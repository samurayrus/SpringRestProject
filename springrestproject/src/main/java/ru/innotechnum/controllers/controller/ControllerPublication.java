package ru.innotechnum.controllers.controller;

import ru.innotechnum.controllers.database.Dao;
import ru.innotechnum.controllers.database.PublicationRepository;
import ru.innotechnum.controllers.entity.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value = "/publication", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerPublication {
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
        return publicationRepository.findOrderByRaitingDesc().toString();
    }

    @GetMapping("/byComments/")
    public String getPublicationPopular() {
        List<Publication> listPubl = publicationRepository.findOrderByIdDesc();
        listPubl.stream().sorted(Comparator.comparing(x-> x.getCommentList().size()));
        return listPubl.toString();
    }

    @GetMapping("/byNew/")
    public String getPublicationNew() {
        return publicationRepository.findOrderByIdDesc().toString();
    }

    @GetMapping("/byOld/")
    public String getPublicationOld() {
        return publicationRepository.findOrderByIdAsc().toString();
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

    @PutMapping("/{id}")
    public String editPublication(@PathVariable int id, @RequestBody Publication publ) {
        if (publicationRepository.existsById(id)) {
            Publication orig = publicationRepository.findById(id);
            if(publ.getName()!=null)
            orig.setName(publ.getName());
            if(publ.getText()!=null)
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
