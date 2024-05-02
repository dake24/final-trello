package kz.didar.trello.controllers;

import jakarta.servlet.http.HttpServletRequest;
import kz.didar.trello.models.Folders;
import kz.didar.trello.models.Tasks;
import kz.didar.trello.services.FoldersService;
import kz.didar.trello.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/folders")
public class FoldersController {
    @Autowired
    private TasksService service;

    @Autowired
    private FoldersService foldersService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/{folderId}")
    public String showFolderDetails(@PathVariable Long folderId, Model model) {
        Folders folder = foldersService.getFolderById(folderId);
        List<Tasks> tasks = service.getTaskByFolderId(folderId);
        if (folder == null) {
            return "redirect:/";
        }
        model.addAttribute("folder", folder);
        model.addAttribute("tasks", tasks);
        return "folder-details";
    }

    @PostMapping
    public String addFolder(@RequestParam("folderName") String folderName) {
        foldersService.createFolder(folderName);
        return "redirect:/";
    }

    @PostMapping("/{folderId}/categories")
    public String addCategory(@PathVariable Long folderId, @RequestParam("categoryName") String categoryName) {
        foldersService.addCategoryToFolder(folderId, categoryName);
        return "redirect:/folders/{folderId}";
    }

    @DeleteMapping("/{folderId}/categories/{categoryId}/delete")
    public ResponseEntity<String> deleteCategoryFromFolder(@PathVariable Long folderId, @PathVariable Long categoryId) {
        foldersService.deleteCategoryFromFolder(folderId, categoryId);
        return ResponseEntity.ok().body("Категория успешно удалена");
    }

    @GetMapping("/{folderId}/tasks")
    public List<Tasks> getAllTasks() {
        return service.getAllTasks();
    }

    @GetMapping("/{folderId}/tasks/{id}")
    public String getTaskById(@PathVariable("id") Long id, Model model) {
        Tasks taskById = service.getTaskById(id);
        model.addAttribute("task", taskById);

        String currentUri = request.getRequestURI();
        model.addAttribute("currentUri", currentUri);

        return "tasks-details";
    }

    @PostMapping("/{folderId}/tasks")
    public String createTask(@PathVariable Long folderId, @RequestParam String title, @RequestParam String description) {
        Tasks task = new Tasks(title, description);
        service.createTask(folderId, task);
        return "redirect:/folders/{folderId}";
    }

    @PutMapping("/{folderId}/tasks/{id}")
    public Tasks updateTask(@PathVariable("id") Long id, @RequestBody Tasks task) {
        return service.updateTask(id, task);
    }

    @DeleteMapping("/{folderId}/tasks/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        service.deleteTask(id);
    }

    @PostMapping("/{folderId}/tasks/{id}/status")
    public String changeTaskStatus(@PathVariable("id") Long id, @RequestParam int newStatus) {
        service.changeTaskStatus(id, newStatus);
        return "redirect:/folders/{folderId}";
    }
}
