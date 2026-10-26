package com.se193910.mvc.dto;

import com.se193910.mvc.entity.Artifact;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ArtifactDTO implements Serializable {
    private Integer id;

    @NotNull
    @Pattern(regexp = "^[A-Z]{3}\\d{3}$", message = "Must start with 3 uppercase letters followed by 3 digits (e.g., ART101)")
    private String artifactCode;

    @NotNull
    @Size(max = 200)
    private String artifactName;

    @NotNull
    private CategoryDTO category;

    @NotNull
    @Positive(message = "Estimated Value must be a number greater than 0.")
    private BigDecimal estimatedValue;

    @NotNull
    @Positive(message = "Age must be a positive integer.")
    private Integer ageYears;

    @NotNull
    @PastOrPresent(message = "Acquisition Date must be a valid past or present date.")
    private LocalDate acquisitionDate;

    @NotNull
    @Size(max = 10)
    private String status;

    public ArtifactDTO() {
        this.category = new CategoryDTO();
    }

    public ArtifactDTO(Integer id, String artifactCode, String artifactName, CategoryDTO category, BigDecimal estimatedValue, Integer ageYears, LocalDate acquisitionDate, String status) {
        this.id = id;
        this.artifactCode = artifactCode;
        this.artifactName = artifactName;
        this.category = category;
        this.estimatedValue = estimatedValue;
        this.ageYears = ageYears;
        this.acquisitionDate = acquisitionDate;
        this.status = status;
    }

    public static ArtifactDTO artifactDTO(Artifact artifact) {
        if (artifact == null) return null;
        return new ArtifactDTO(
                artifact.getId(),
                artifact.getArtifactCode(),
                artifact.getArtifactName(),
                CategoryDTO.categoryDTO(artifact.getCategory()),
                artifact.getEstimatedValue(),
                artifact.getAgeYears(),
                artifact.getAcquisitionDate(),
                artifact.getStatus()
        );
    }

    public Artifact toEntity() {
        Artifact artifact = new Artifact();
        artifact.setId(this.id);
        artifact.setArtifactCode(this.artifactCode);
        artifact.setArtifactName(this.artifactName);
        artifact.setEstimatedValue(this.estimatedValue);
        artifact.setAgeYears(this.ageYears);
        artifact.setAcquisitionDate(this.acquisitionDate);
        artifact.setStatus(this.status);
        if (this.category != null) {
            artifact.setCategory(this.category.toEntity());
        }
        return artifact;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getArtifactCode() { return artifactCode; }
    public void setArtifactCode(String artifactCode) { this.artifactCode = artifactCode; }
    public String getArtifactName() { return artifactName; }
    public void setArtifactName(String artifactName) { this.artifactName = artifactName; }
    public CategoryDTO getCategory() { return category; }
    public void setCategory(CategoryDTO category) { this.category = category; }
    public BigDecimal getEstimatedValue() { return estimatedValue; }
    public void setEstimatedValue(BigDecimal estimatedValue) { this.estimatedValue = estimatedValue; }
    public Integer getAgeYears() { return ageYears; }
    public void setAgeYears(Integer ageYears) { this.ageYears = ageYears; }
    public LocalDate getAcquisitionDate() { return acquisitionDate; }
    public void setAcquisitionDate(LocalDate acquisitionDate) { this.acquisitionDate = acquisitionDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}