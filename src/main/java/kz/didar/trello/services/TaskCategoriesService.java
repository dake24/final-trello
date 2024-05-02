package kz.didar.trello.services;

import kz.didar.trello.models.TaskCategories;
import kz.didar.trello.repository.TaskCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCategoriesService {
    @Autowired
    private TaskCategoriesRepository repository;

    public List<TaskCategories> getAllTaskCategories() {
        return repository.findAll();
    }

    public TaskCategories getTaskCategoryById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public TaskCategories createTaskCategory(TaskCategories taskCategory) {
        return repository.save(taskCategory);
    }

    public TaskCategories updateTaskCategory(Long id, TaskCategories newTaskCategory) {
        TaskCategories existingTaskCategory = repository.findById(id).orElse(null);
        if (existingTaskCategory != null) {
            existingTaskCategory.setName(newTaskCategory.getName());
            return repository.save(existingTaskCategory);
        }
        return null;
    }

    public void deleteTaskCategory(Long id) {
        repository.deleteById(id);
    }
}

