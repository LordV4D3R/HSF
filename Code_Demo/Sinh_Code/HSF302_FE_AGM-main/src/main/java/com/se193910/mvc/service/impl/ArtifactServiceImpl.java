package com.se193910.mvc.service.impl;

import com.se193910.mvc.dto.ArtifactDTO;
import com.se193910.mvc.entity.Artifact;
import com.se193910.mvc.entity.Category;
import com.se193910.mvc.repository.ArtifactRepository;
import com.se193910.mvc.repository.CategoryRepository;
import com.se193910.mvc.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtifactServiceImpl implements ArtifactService {

    @Autowired
    private ArtifactRepository artifactRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ArtifactDTO> getAllArtifacts(String searchName) {
        List<Artifact> artifacts;
        if (searchName != null && !searchName.trim().isEmpty()) {
            artifacts = artifactRepository.searchByArtifactNameContainingIgnoreCaseOrderByAcquisitionDateDesc(searchName.trim());
        } else {
            artifacts = artifactRepository.findAllByOrderByAcquisitionDateDesc();
        }
        return artifacts.stream().map(ArtifactDTO::artifactDTO).toList();
    }

    @Override
    public ArtifactDTO getArtifactById(int artifactId) {
        return artifactRepository.findById(artifactId).map(ArtifactDTO::artifactDTO).orElse(null);
    }

    @Override
    public boolean deleteArtifactById(int artifactId) {
        Artifact artifact = artifactRepository.findById(artifactId).orElse(null);
        if (artifact != null) {
            if ("AC".equals(artifact.getStatus())) {
                return false;
            }
            artifactRepository.delete(artifact);
            return true;
        }
        return false;
    }

    @Override
    public boolean addArtifact(ArtifactDTO artifactDTO) {
        try {
            Artifact artifact = artifactDTO.toEntity();
            if (artifact != null) {
                if (artifact.getCategory() != null && artifact.getCategory().getId() != null) {
                    Category category = categoryRepository.findById(artifact.getCategory().getId()).orElse(null);
                    artifact.setCategory(category);
                }
                artifactRepository.save(artifact);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}