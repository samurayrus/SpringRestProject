package ru.innotechnum.controllers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HistoryUserRepository historyUserRepository;

    public UserController(UserRepository userRepository, HistoryUserRepository historyUserRepository) {
        this.userRepository = userRepository;
        this.historyUserRepository = historyUserRepository;
    }

    @GetMapping("/{id}/raiting")
    public Integer getRaiting(@PathVariable int id) {
        return userRepository.findById(id).getRaiting();
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


    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
        addHistoryUser(user);
    }

    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    private void addHistoryUser(User user) {
        HistoryUser historyUser = new HistoryUser();
        historyUser.setNickName(user.getNickName());
        historyUser.setAboutMe(user.getAboutMe());
        historyUser.setDateBegin(LocalDate.now());
        historyUser.setDateEnd(LocalDate.of(2999, 1, 1));
        historyUser.setUser(user);
        historyUserRepository.save(historyUser);

        // Тест транзакции. Потом нужно убрать. Всё ок
        if(user.getNickName().equals("абубу23"))
        throw new RuntimeException();
        //...
    }


    @PutMapping("/{id}")
    public String editUser(@PathVariable int id, @RequestBody User user) {
        if (userRepository.existsById(id)) {
            User oldUser = userRepository.findById(id);
            if (oldUser.getDeleted()) {
                return "DELETED";
            }
            oldUser.setNickName(user.getNickName());
            oldUser.setAboutMe(user.getAboutMe());
            oldUser.setDeleted(user.getDeleted());

            List<HistoryUser> hisUs = oldUser.getHistoryUsers();
            if (hisUs!=null) { //Временная заглушка для тестов
                hisUs.get(hisUs.size() - 1).setDateEnd(LocalDate.now());

                addHistoryUser(oldUser);
            }
            userRepository.flush();
            return "ok";
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
