package ru.innotechnum.controllers.controller;

import ru.innotechnum.controllers.BaseResponse;
import ru.innotechnum.controllers.database.Dao;
import ru.innotechnum.controllers.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerUser {
    @Autowired
    private Dao dao;

    @PostMapping("/add/{name}/{about}")
    public String addUser(@PathVariable String name, @PathVariable String about) {
        User user = new User(name, about);
        System.out.println(user.toString());
        return dao.createUser(user);
    }

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.CREATED)
    public String test(@RequestBody BaseResponse request) {
        return request.getStatus() + request.getCode();
    }

    @GetMapping("/Raiting/{id}")
    public String getRaiting(@PathVariable int id) {
        return dao.getUserRaiting(id);
    }

    @PutMapping("/{id}/{name}/{about}")
    public String editUser(@PathVariable int id, @PathVariable String name, @PathVariable String about) {
        User user = new User(name, about);
        return dao.editUser(user, id);
    }

    @GetMapping("/info/{id}")
    public String getUserInfo(@PathVariable int id) {
        return dao.findUser(id).toString();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        return editUser(id, "DELETED"," DELETED");
    }

}
