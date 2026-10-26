package com.se193910.mvc.repository;

import com.se193910.mvc.entity.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtifactRepository extends JpaRepository<Artifact, Integer> {
    List<Artifact> findAllByOrderByAcquisitionDateDesc();

    @Query("SELECT a FROM Artifact a WHERE LOWER(a.artifactName) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY a.acquisitionDate DESC")
    List<Artifact> searchByArtifactNameContainingIgnoreCaseOrderByAcquisitionDateDesc(@Param("name") String name);
}