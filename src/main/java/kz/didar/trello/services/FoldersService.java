package kz.didar.trello.services;

import kz.didar.trello.models.Folders;
import kz.didar.trello.models.TaskCategories;
import kz.didar.trello.models.Tasks;
import kz.didar.trello.repository.FoldersRepository;
import kz.didar.trello.repository.TaskCategoriesRepository;
import kz.didar.trello.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FoldersService {
    @Autowired
    private FoldersRepository repository;

    @Autowired
    private TaskCategoriesRepository taskCategoriesRepository;

    @Autowired
    private TasksRepository tasksRepository;

    public List<Folders> getAllFolders() {
        return repository.findAll();
    }

    public Folders getFolderById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Folders createFolder(String folderName) {
        Folders folder = new Folders();
        folder.setName(folderName);
        return repository.save(folder);
    }

    public Folders updateFolder(Long id, Folders newFolder) {
        Folders existingFolder = repository.findById(id).orElse(null);
        if (existingFolder != null) {
            existingFolder.setName(newFolder.getName());
            existingFolder.setCategories(newFolder.getCategories());
            return repository.save(existingFolder);
        }
        return null;
    }

    public void deleteFolder(Long id) {
        List<Tasks> tasksToDelete = tasksRepository.findByFolderId(id);
        tasksToDelete.forEach(task -> task.setFolder(null));
        tasksRepository.deleteAll(tasksToDelete);

        repository.clearCategoriesForFolder(id);

        repository.deleteById(id);
    }

    public void addCategoryToFolder(Long folderId, String categoryName) {
        Folders folder = repository.findById(folderId).orElse(null);
        if (folder != null) {
            TaskCategories category = new TaskCategories();
            category.setName(categoryName);

            taskCategoriesRepository.save(category);

            folder.getCategories().add(category);
            repository.save(folder);
        }
    }

    public void deleteCategoryFromFolder(Long folderId, Long categoryId) {
        Folders folder = repository.findById(folderId).orElse(null);
        if (folder != null) {
            folder.getCategories().removeIf(category -> category.getId().equals(categoryId));
            repository.save(folder);
        }
    }
}

