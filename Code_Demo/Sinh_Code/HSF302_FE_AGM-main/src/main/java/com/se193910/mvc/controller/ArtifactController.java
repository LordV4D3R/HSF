package com.se193910.mvc.controller;

import com.se193910.mvc.dto.ArtifactDTO;
import com.se193910.mvc.service.ArtifactService;
import com.se193910.mvc.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ArtifactController {

    @Autowired
    private ArtifactService artifactService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/", "/list"})
    public String listArtifacts(@RequestParam(required = false) String searchName, Model model) {
        model.addAttribute("artifacts", artifactService.getAllArtifacts(searchName));
        model.addAttribute("searchName", searchName);
        return "list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("artifact", new ArtifactDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add";
    }

    @PostMapping("/add")
    public String addArtifact(@Valid @ModelAttribute("artifact") ArtifactDTO artifactDTO,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "add";
        }
        boolean success = artifactService.addArtifact(artifactDTO);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Created new artifact successfully");
            return "redirect:/list";
        }
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("errorMessage", "Failed to add artifact.");
        return "add";
    }

    @GetMapping("/view")
    public String viewArtifact(@RequestParam("id") int id, Model model) {
        ArtifactDTO artifact = artifactService.getArtifactById(id);
        if (artifact == null) {
            return "redirect:/list";
        }
        model.addAttribute("artifact", artifact);
        return "view";
    }

    @PostMapping("/delete")
    public String deleteArtifact(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        boolean deleted = artifactService.deleteArtifactById(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete an active artifact.");
        }
        return "redirect:/list";
    }
}