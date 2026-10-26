package com.se193910.mvc.service;

import com.se193910.mvc.dto.DeviceDTO;

import java.util.List;

public interface DeviceService {

    List<DeviceDTO> getAllDevices(String searchName);

    DeviceDTO getDeviceById(int id);

    boolean addDevice(DeviceDTO deviceDTO);

    boolean isDeviceCodeExists(String deviceCode);

    boolean deleteDeviceById(int id);
}
