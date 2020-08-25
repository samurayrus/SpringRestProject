package ru.innotechnum.controllers.controller;

import ru.innotechnum.controllers.database.*;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.HistoryUser;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE) //Для /test
public class ControllerUser {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HistoryUserRepository historyUserRepository;

    @GetMapping("/Raiting/{id}")
    public String getRaiting(@PathVariable int id) {
        return "{Raiting = " + userRepository.findById(id).getRaiting() + "}";
    }

    @GetMapping("/comments/{id}")
    public String getUserComments(@PathVariable int id) {
        return userRepository.findById(id).getListCom().toString();
    }

    @GetMapping("/publications/{id}")
    public String getPublicationAllForAuthor(@PathVariable int id) {
        return userRepository.findById(id).getListPubl().toString();
    }

    @GetMapping("/{id}")
    public String getUserInfo(@PathVariable int id) {
        return userRepository.findById(id).toString();
    }

    @PostMapping("/")
    //@ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody User user) {
        userRepository.save(user);
        addHistoryUser(user);
        return "created";
    }

    private void addHistoryUser(User user) {
        HistoryUser historyUser = new HistoryUser();
        historyUser.setNickName(user.getNickName());
        historyUser.setAboutMe(user.getAboutMe());
        historyUser.setDateBegin(LocalDate.now());
        historyUser.setUser(user);
        historyUserRepository.save(historyUser);
    }

    @PutMapping("/{id}")
    public String editUser(@PathVariable int id, @RequestBody User user) {
        if (userRepository.existsById(id)) {
            User oldUser = userRepository.findById(id);
            oldUser.setNickName(user.getNickName());
            oldUser.setAboutMe(user.getAboutMe());

            List<HistoryUser> hisUs = oldUser.getHistoryUsers();
            hisUs.get(hisUs.size()-1).setDateEnd(LocalDate.now());

            userRepository.flush();

            addHistoryUser(oldUser);
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
