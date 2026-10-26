package com.se193910.mvc.service.impl;

import com.se193910.mvc.dto.DeviceDTO;
import com.se193910.mvc.entity.Category;
import com.se193910.mvc.entity.Device;
import com.se193910.mvc.repository.CategoryRepository;
import com.se193910.mvc.repository.DeviceRepository;
import com.se193910.mvc.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<DeviceDTO> getAllDevices(String searchName) {
        List<Device> devices;
        if (searchName != null && !searchName.trim().isEmpty()) {
            devices = deviceRepository.searchByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(searchName.trim());
        } else {
            devices = deviceRepository.findAllByOrderByInstallationDateDesc();
        }
        return devices.stream().map(DeviceDTO::deviceDTO).toList();
    }

    @Override
    public DeviceDTO getDeviceById(int id) {
        return deviceRepository.findById(id)
                .map(DeviceDTO::deviceDTO)
                .orElse(null);
    }

    @Override
    public boolean addDevice(DeviceDTO deviceDTO) {
        try {
            if (deviceDTO == null) return false;
            Device device = deviceDTO.toEntity();
            if (device.getCategory() != null && device.getCategory().getId() != null) {
                Category category = categoryRepository.findById(device.getCategory().getId()).orElse(null);
                device.setCategory(category);
            }
            deviceRepository.save(device);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isDeviceCodeExists(String deviceCode) {
        if (deviceCode == null) return false;
        return deviceRepository.existsByDeviceCode(deviceCode.trim());
    }

    @Override
    public boolean deleteDeviceById(int id) {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device != null) {
            // Nghiệp vụ đề thi (Q2.2): Nếu status là 'AC' (Active) thì KHÔNG XÓA và báo lỗi
            if ("AC".equalsIgnoreCase(device.getStatus())) {
                return false;
            }
            deviceRepository.delete(device);
            return true;
        }
        return false;
    }
}
