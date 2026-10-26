package com.se193910.mvc.controller;

import com.se193910.mvc.dto.DeviceDTO;
import com.se193910.mvc.service.CategoryService;
import com.se193910.mvc.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CategoryService categoryService;

    /**
     * Q1) List function
     * URL: http://localhost:8080/ or http://localhost:8080/list
     */
    @GetMapping({"/", "/list"})
    public String listDevices(@RequestParam(required = false) String searchName, Model model) {
        model.addAttribute("devices", deviceService.getAllDevices(searchName));
        model.addAttribute("searchName", searchName);
        return "list";
    }

    /**
     * Q3) Add New screen (Hiển thị form thêm mới)
     * URL: http://localhost:8080/add
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("device", new DeviceDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add";
    }

    /**
     * Q3) Xử lý lưu thiết bị mới
     * Theo yêu cầu đề bài (Q3.2):
     * - Nếu hợp lệ: Lưu DB, hiển thị thông báo "Created new device successfully" ngay trên màn hình và xóa trắng các trường (clear fields).
     * - Nếu không hợp lệ: Hiển thị thông báo lỗi cạnh các trường tương ứng.
     */
    @PostMapping("/add")
    public String addDevice(@Valid @ModelAttribute("device") DeviceDTO deviceDTO,
                            BindingResult bindingResult,
                            Model model) {
        // Kiểm tra trùng mã thiết bị (Unique check)
        if (deviceDTO.getDeviceCode() != null && deviceService.isDeviceCodeExists(deviceDTO.getDeviceCode())) {
            bindingResult.rejectValue("deviceCode", "error.device", "Device Code must be unique.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "add";
        }

        boolean success = deviceService.addDevice(deviceDTO);
        if (success) {
            model.addAttribute("successMessage", "Created new device successfully");
            model.addAttribute("device", new DeviceDTO()); // Clear fields
        } else {
            model.addAttribute("errorMessage", "Failed to add device.");
        }
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add";
    }

    /**
     * Q4) View detail screen
     * URL: http://localhost:8080/view?id=...
     */
    @GetMapping("/view")
    public String viewDevice(@RequestParam("id") int id, Model model) {
        DeviceDTO device = deviceService.getDeviceById(id);
        if (device == null) {
            return "redirect:/list";
        }
        model.addAttribute("device", device);
        return "view";
    }

    /**
     * Q2) Delete function
     * - Nếu Active (AC): Báo lỗi "Cannot delete an active device."
     * - Nếu Inactive (IN) hoặc Draft (DR): Xóa khỏi DB và báo "Deleted successfully"
     */
    @PostMapping("/delete")
    public String deleteDevice(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        boolean deleted = deviceService.deleteDeviceById(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete an active device.");
        }
        return "redirect:/list";
    }
}
