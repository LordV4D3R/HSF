package com.se193910.mvc.repository;

import com.se193910.mvc.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    List<Device> findAllByOrderByInstallationDateDesc();

    @Query("SELECT d FROM Device d WHERE LOWER(d.deviceName) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY d.installationDate DESC")
    List<Device> searchByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(@Param("name") String name);

    boolean existsByDeviceCode(String deviceCode);
}
