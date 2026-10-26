package com.se193910.mvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "artifacts")
public class Artifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artifact_id", nullable = false)
    private Integer id;

    @Size(max = 6)
    @NotNull
    @Column(name = "artifact_code", nullable = false,unique = true, length = 6)
    private String artifactCode;

    @Size(max = 200)
    @NotNull
    @Nationalized
    @Column(name = "artifact_name", nullable = false, length = 200)
    private String artifactName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    @Column(name = "estimated_value", nullable = false, precision = 10)
    private BigDecimal estimatedValue;

    @NotNull
    @Column(name = "age_years", nullable = false)
    private Integer ageYears;

    @NotNull
    @Column(name = "acquisition_date", nullable = false)
    private LocalDate acquisitionDate;

    @Size(max = 10)
    @NotNull
    @Nationalized
    @Column(name = "status", nullable = false, length = 10)
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArtifactCode() {
        return artifactCode;
    }

    public void setArtifactCode(String artifactCode) {
        this.artifactCode = artifactCode;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(BigDecimal estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public Integer getAgeYears() {
        return ageYears;
    }

    public void setAgeYears(Integer ageYears) {
        this.ageYears = ageYears;
    }

    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}