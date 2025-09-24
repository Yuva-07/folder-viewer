package com.example.folderviewer.controller;

import com.example.folderviewer.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class FolderController {

    @Autowired
    private FileService fileService;

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/list")
    public String list(@RequestParam("path") String path, Model model) {
        try {
            List<FileService.Entry> entries = fileService.listDirectory(path);
            Path absolute = Paths.get(path).toAbsolutePath().normalize();
            model.addAttribute("entries", entries);
            model.addAttribute("path", absolute.toString());
            Path parent = absolute.getParent();
            model.addAttribute("parent", parent == null ? null : parent.toString());
            model.addAttribute("error", entries.isEmpty() && (absolute.toFile().exists() && absolute.toFile().isDirectory()) ? null : null);
        } catch (IOException e) {
            model.addAttribute("entries", List.of());
            model.addAttribute("path", path);
            model.addAttribute("error", "Could not read directory: " + e.getMessage());
        }
        return "listing";
    }

    @GetMapping("/browse")
    public String browse(@RequestParam("p") String p, Model model) {
        return list(p, model);
    }
}