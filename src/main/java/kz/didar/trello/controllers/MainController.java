package kz.didar.trello.controllers;

import kz.didar.trello.models.Folders;
import kz.didar.trello.services.FoldersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private FoldersService foldersService;

    @GetMapping("/")
    public String showHomePage(Model model) {
        List<Folders> folders = foldersService.getAllFolders();
        model.addAttribute("folders", folders);
        return "index";
    }
}

