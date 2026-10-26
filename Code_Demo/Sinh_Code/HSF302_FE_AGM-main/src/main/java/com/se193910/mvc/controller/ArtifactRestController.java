package com.se193910.mvc.controller;

import com.se193910.mvc.dto.ArtifactDTO;
import com.se193910.mvc.entity.Artifact;
import com.se193910.mvc.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artifacts")
public class ArtifactRestController {

    @Autowired
    private ArtifactService artifactService;

    @GetMapping
    public List<ArtifactDTO> getAll() {
        return artifactService.getAllArtifacts(null);
    }
}
