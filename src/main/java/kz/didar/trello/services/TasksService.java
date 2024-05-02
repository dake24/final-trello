package kz.didar.trello.services;

import kz.didar.trello.models.Folders;
import kz.didar.trello.models.Tasks;
import kz.didar.trello.repository.FoldersRepository;
import kz.didar.trello.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksService {
    @Autowired
    private TasksRepository repository;

    @Autowired
    private FoldersService foldersService;

    public List<Tasks> getAllTasks() {
        return repository.findAll();
    }

    public Tasks getTaskById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Tasks> getTaskByFolderId(Long id) {
        return repository.findByFolderId(id);
    }

    public Tasks createTask(Long folderId, Tasks task) {
        Folders folder = foldersService.getFolderById(folderId);
        if (folder == null) {
            return null;
        }
        task.setStatus(0);
        task.setFolder(folder);

        return repository.save(task);
    }

    public Tasks updateTask(Long id, Tasks newTask) {
        Tasks existingTask = repository.findById(id).orElse(null);
        if (existingTask != null) {
            existingTask.setTitle(newTask.getTitle());
            existingTask.setDescription(newTask.getDescription());
            existingTask.setStatus(newTask.getStatus());
            return repository.save(existingTask);
        }
        return null;
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    public Tasks changeTaskStatus(Long id, int newStatus) {
        Tasks task = repository.findById(id).orElse(null);
        if (task != null) {
            if ((task.getStatus() == 3 || task.getStatus() == 4) && (newStatus == 3 || newStatus == 4)) {
                return null;
            }

            task.setStatus(newStatus);
            return repository.save(task);
        }
        return null;
    }
}

