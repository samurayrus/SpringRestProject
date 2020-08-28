package ru.innotechnum.controllers.controller;

import org.springframework.http.HttpStatus;
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

    public ControllerUser(UserRepository userRepository, HistoryUserRepository historyUserRepository) {
        this.userRepository = userRepository;
        this.historyUserRepository = historyUserRepository;
    }

    @GetMapping("/{id}/raiting")
    public String getRaiting(@PathVariable int id) {
        return "{Raiting = " + userRepository.findById(id).getRaiting() + "}";
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getUserComments(@PathVariable int id) {
        return userRepository.findById(id).getListCom();
    }

    @GetMapping("/{id}/publications")
    public List<Publication> getPublicationAllForAuthor(@PathVariable int id) {
        return userRepository.findById(id).getListPubl();
    }

    @GetMapping("/{id}")
    public User getUserInfo(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @GetMapping("/test")
    public String getTest() { //Возврат никнейма по дате изменения
        int id=14;
        LocalDate ld = LocalDate.of(2020,8,27);
        List<HistoryUser> list =  userRepository.findById(id).getHistoryUsers();
        for(HistoryUser historyUser : list) {
            if(historyUser.getDateBegin().compareTo(ld)<0 && historyUser.getDateEnd().compareTo(ld)>0)
            {
                return historyUser.getNickName();
            }
        }
        return "list";
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
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
        historyUser.setDateEnd(LocalDate.of(2999,1,1));
        historyUser.setUser(user);
        historyUserRepository.save(historyUser);
    }

    @PutMapping("/{id}")
    public String editUser(@PathVariable int id, @RequestBody User user) {
        if (userRepository.existsById(id)) {
            User oldUser = userRepository.findById(id);
            if(oldUser.getDeleted()) { return "DELETED";}
            oldUser.setNickName(user.getNickName());
            oldUser.setAboutMe(user.getAboutMe());
            oldUser.setDeleted(user.getDeleted());

            List<HistoryUser> hisUs = oldUser.getHistoryUsers();
            hisUs.get(hisUs.size()-1).setDateEnd(LocalDate.now());

            userRepository.flush();

            addHistoryUser(oldUser);
            return "Edited";
        } else {
            return "NotFound";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        User user = new User("DELETED", "DELETED");
        user.setDeleted(true);
        return editUser(id, user);
    }

}
