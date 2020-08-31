package ru.innotechnum.controllers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.innotechnum.controllers.BaseResponse;
import ru.innotechnum.controllers.database.CommentRepository;
import ru.innotechnum.controllers.database.PublicationRepository;
import ru.innotechnum.controllers.database.UserRepository;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.Publication;

import java.util.List;

@RestController
@RequestMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PublicationRepository publicationRepository;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addComment(@RequestBody Comment com) {
        com.setUser(userRepository.findById(com.getAuthorId()));
        com.setAuthName(com.getUser().getNickName());
        com.setPublication(publicationRepository.findById(com.getPublicationId()));
        if(com.getParentId()!=0) {
            com.setComment(commentRepository.findById(com.getParentId()));
        }
        commentRepository.save(com);
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable int id) {
        commentRepository.deleteById(id);
        return "ok";
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable int id) {
        return commentRepository.findById(id);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentComments(@PathVariable int id) {
        return commentRepository.findById(id).getCommentList();
    }

    @GetMapping("/")
    public List<Comment> getLastComment() {
        return commentRepository.findTop10ByOrderByIdDesc();
    }

    @PutMapping("/{id}")
    public String editComment(@PathVariable int id, @RequestBody Comment comment) {
        if (commentRepository.existsById(id)) {
            Comment orig = commentRepository.findById(id);
            if (comment.getText() != null)
                orig.setText(comment.getText());
            commentRepository.flush();
            return "ok";
        } else
            return "NotFound";
    }

    @PutMapping("/{id}/addraiting")
    public String updateCommentRaiting(@PathVariable int id) {
        if (commentRepository.existsById(id)) {
            Comment orig = commentRepository.findById(id);
            orig.setRaiting(orig.getRaiting() + 1);
            commentRepository.flush();
            return "New Raiting = " + orig.getRaiting();
        } else
            return "NotFound";
    }

}
