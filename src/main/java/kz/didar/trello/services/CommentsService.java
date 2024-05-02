package kz.didar.trello.services;

import kz.didar.trello.models.Comments;
import kz.didar.trello.models.Tasks;
import kz.didar.trello.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository repository;

    @Autowired
    private TasksService tasksService;

    public List<Comments> getCommentsByTaskId(Long taskId) {
        return repository.findByTaskId(taskId);
    }

    public Comments addComment(Long taskId, String commentText) {
        Tasks task = tasksService.getTaskById(taskId);
        if (task != null) {
            Comments comment = new Comments();
            comment.setTask(task);
            comment.setComment(commentText);
            return repository.save(comment);
        }
        return null;
    }

}
