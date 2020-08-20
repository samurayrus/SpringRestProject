package ru.innotechnum.controllers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.innotechnum.controllers.BaseResponse;
import ru.innotechnum.controllers.database.Dao;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.User;

@RestController
@RequestMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerComments {
    @Autowired
    private Dao dao;

    @PostMapping("/add/{text}/{authorId}/{publicationId}/{parentId}")
    public String addComment(@PathVariable String text, @PathVariable int authorId, @PathVariable int publicationId, @PathVariable int parentId) {
        Comment com = new Comment(text,authorId, publicationId, parentId );
        return dao.createComment(com);
    }

    @GetMapping("/usercomments/{id}")
    public String getUserComments(@PathVariable int id) {
        return dao.findUser(id).getListCom().toString(); //Пока выводит всю информацию о пользователе + все комментарии
    }

}
