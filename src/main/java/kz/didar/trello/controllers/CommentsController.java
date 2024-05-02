package kz.didar.trello.controllers;

import kz.didar.trello.models.Comments;
import kz.didar.trello.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks/{taskId}/comments")
public class CommentsController {
    @Autowired
    private CommentsService service;

    @GetMapping
    public List<Comments> getCommentsByTaskId(@PathVariable Long taskId) {
        return service.getCommentsByTaskId(taskId);
    }

    @PostMapping
    public Comments addComment(@PathVariable Long taskId, @RequestParam String commentText) {
        return service.addComment(taskId, commentText);
    }
}
