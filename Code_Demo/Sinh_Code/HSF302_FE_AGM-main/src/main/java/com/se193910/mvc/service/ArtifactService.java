package com.se193910.mvc.service;

import com.se193910.mvc.dto.ArtifactDTO;
import java.util.List;

public interface ArtifactService {
    List<ArtifactDTO> getAllArtifacts(String searchName);
    ArtifactDTO getArtifactById(int artifactId);
    boolean deleteArtifactById(int artifactId);
    boolean addArtifact(ArtifactDTO artifactDTO);
}