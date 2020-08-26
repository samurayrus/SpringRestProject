package ru.innotechnum.controllers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.innotechnum.controllers.BaseResponse;
import ru.innotechnum.controllers.database.CommentRepository;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.Publication;

import java.util.List;

@RestController
@RequestMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerComments {
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/")
    public String addComment(@RequestBody Comment com) {
        commentRepository.save(com);
        return new BaseResponse("POST","OK").toString();
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable int id) {
        commentRepository.deleteById(id);
        return new BaseResponse("DELETE","OK").toString();
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable int id) {
        return commentRepository.findById(id);
    }

    @GetMapping("/")
    public List<Comment> getLastComment(@PathVariable int id) {
        return commentRepository.findTop10ByOrderByIdDesc();
    }

    @PutMapping("/{id}")
    public String editComment(@PathVariable int id, @RequestBody Comment comment) {
        if (commentRepository.existsById(id)) {
            Comment orig = commentRepository.findById(id);
            if (comment.getText() != null)
                orig.setText(comment.getText());
            commentRepository.flush();
            return orig.toString();
        } else
            return new BaseResponse("Error","NotFound").toString();
    }

    @PutMapping("/raiting/{id}")
    public String updateCommentRaiting(@PathVariable int id) {
        if (commentRepository.existsById(id)) {
            Comment orig = commentRepository.findById(id);
            orig.setRaiting(orig.getRaiting() + 1);
            commentRepository.flush();
            return new BaseResponse("PUT","Raiting Added. New Raiting = " + orig.getRaiting() ).toString();
        } else
            return new BaseResponse("Error","Not Found").toString();
    }

}
