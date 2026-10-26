package com.se193910.mvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", nullable = false)
    private Integer id;

    @NotNull
    @Size(max = 6)
    @Column(name = "device_code", nullable = false, unique = true, length = 6)
    private String deviceCode;

    @NotNull
    @Size(max = 200)
    @Column(name = "device_name", nullable = false, length = 200)
    private String deviceName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10)
    private BigDecimal price;

    @NotNull
    @Column(name = "warranty_months", nullable = false)
    private Integer warrantyMonths;

    @NotNull
    @Column(name = "installation_date", nullable = false)
    private LocalDate installationDate;

    @NotNull
    @Size(max = 10)
    @Column(name = "status", nullable = false, length = 10)
    private String status;

    public Device() {
    }

    public Device(Integer id, String deviceCode, String deviceName, Category category, BigDecimal price, Integer warrantyMonths, LocalDate installationDate, String status) {
        this.id = id;
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.category = category;
        this.price = price;
        this.warrantyMonths = warrantyMonths;
        this.installationDate = installationDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(Integer warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public LocalDate getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(LocalDate installationDate) {
        this.installationDate = installationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
