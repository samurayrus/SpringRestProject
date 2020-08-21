package ru.innotechnum.controllers.controller;

import ru.innotechnum.controllers.BaseRequest;
import ru.innotechnum.controllers.database.Dao;
import ru.innotechnum.controllers.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE) //Для /test
public class ControllerUser {
    @Autowired
    private Dao dao;

    @PostMapping("/")
    //@ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody User user) {
        return dao.createUser(user);
    }

    @GetMapping("/Raiting/{id}")
    public String getRaiting(@PathVariable int id) {
        return dao.getUserRaiting(id);
    }

    @PutMapping("/{id}")
    public String editUser(@PathVariable int id, @RequestBody User user) {
        return dao.editUser(user, id);
    }

    @GetMapping("/{id}")
    public String getUserInfo(@PathVariable int id) {
        return dao.findUser(id).toString();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        return editUser(id, new User("DELETED", "DELETED"));
    }

}
