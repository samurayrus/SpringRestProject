package ru.innotechnum.controllers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.innotechnum.controllers.database.Dao;
import ru.innotechnum.controllers.entity.Comment;

@RestController
@RequestMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerComments {
    @Autowired
    private Dao dao;

    @PostMapping("/")
    public String addComment(@RequestBody Comment com) {
        return dao.createComment(com);
    }

    @GetMapping("/usercomments/{id}")
    public String getUserComments(@PathVariable int id) {
        return dao.findUser(id).getListCom().toString(); //Пока выводит всю информацию о пользователе + все комментарии
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable int id) {
        return dao.deleteComment(id);
    }
}
