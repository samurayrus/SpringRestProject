package ru.innotechnum.controllers.controller;

import ru.innotechnum.controllers.database.CommentRepository;
import ru.innotechnum.controllers.database.Dao;
import ru.innotechnum.controllers.database.PublicationRepository;
import ru.innotechnum.controllers.database.UserRepository;
import ru.innotechnum.controllers.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE) //Для /test
public class ControllerUser {
    @Autowired
    private Dao dao;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PublicationRepository publicationRepository;

    @GetMapping("/Raiting/{id}")
    public String getRaiting(@PathVariable int id) {
        return userRepository.findById(id).toString();
    }

    @GetMapping("/{id}")
    public String getUserInfo(@PathVariable int id) {
        return userRepository.findById(id).toString();
    }

    @PostMapping("/")
    //@ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody User user) {
        userRepository.save(user);
        return "created";
    }

    @PutMapping("/{id}")
    public String editUser(@PathVariable int id, @RequestBody User user) {
        if (userRepository.existsById(id)) {
            User oldUser = userRepository.findById(id);
            oldUser.setNickName(user.getNickName());
            oldUser.setAboutMe(user.getAboutMe());
            userRepository.flush();
            return oldUser.toString();
        } else {
            return "NotFound";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        return editUser(id, new User("DELETED", "DELETED"));
    }

}
