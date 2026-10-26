package com.se193910.mvc.dto;

import com.se193910.mvc.entity.Device;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DeviceDTO implements Serializable {

    private Integer id;

    @NotBlank(message = "Device Code is mandatory.")
    @Pattern(regexp = "^[A-Z]{3}\\d{3}$", message = "Must start with 3 uppercase letters followed by 3 digits (e.g., DVC101).")
    private String deviceCode;

    @NotBlank(message = "Device Name is mandatory.")
    @Size(max = 200, message = "Max length 200.")
    @Pattern(regexp = "^(?!\\s)(?!.*\\s$).*$", message = "No space at both sides.")
    private String deviceName;

    @NotNull(message = "Category is mandatory.")
    private CategoryDTO category;

    @NotNull(message = "Price is mandatory.")
    @Positive(message = "Price must be a number greater than 0.")
    private BigDecimal price;

    @NotNull(message = "Warranty is mandatory.")
    @Min(value = 1, message = "Warranty must be a positive integer (between 1 and 60).")
    @Max(value = 60, message = "Warranty must be a positive integer (between 1 and 60).")
    private Integer warrantyMonths;

    @NotNull(message = "Installation Date is mandatory.")
    @Future(message = "Must be a future date.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate installationDate;

    @NotBlank(message = "Status is mandatory.")
    @Pattern(regexp = "^(AC|IN|DR)$", message = "Status must be AC, IN, or DR.")
    private String status;

    public DeviceDTO() {
        this.category = new CategoryDTO();
    }

    public DeviceDTO(Integer id, String deviceCode, String deviceName, CategoryDTO category, BigDecimal price, Integer warrantyMonths, LocalDate installationDate, String status) {
        this.id = id;
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.category = category;
        this.price = price;
        this.warrantyMonths = warrantyMonths;
        this.installationDate = installationDate;
        this.status = status;
    }

    public static DeviceDTO deviceDTO(Device device) {
        if (device == null) return null;
        return new DeviceDTO(
                device.getId(),
                device.getDeviceCode(),
                device.getDeviceName(),
                CategoryDTO.categoryDTO(device.getCategory()),
                device.getPrice(),
                device.getWarrantyMonths(),
                device.getInstallationDate(),
                device.getStatus()
        );
    }

    public Device toEntity() {
        Device device = new Device();
        device.setId(this.id);
        device.setDeviceCode(this.deviceCode);
        device.setDeviceName(this.deviceName);
        device.setPrice(this.price);
        device.setWarrantyMonths(this.warrantyMonths);
        device.setInstallationDate(this.installationDate);
        device.setStatus(this.status);
        if (this.category != null) {
            device.setCategory(this.category.toEntity());
        }
        return device;
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

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
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
