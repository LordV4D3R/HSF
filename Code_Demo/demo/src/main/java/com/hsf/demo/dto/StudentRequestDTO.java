package com.hsf.demo.dto;

import com.hsf.demo.entity.Major;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO (Data Transfer Object):
 * - Đối tượng chuyên dùng để NHẬN dữ liệu gửi lên từ Client (RequestBody).
 *
 * TẠI SAO CẦN VALIDATION Ở DTO?
 * - Chặn dữ liệu rác, dữ liệu sai định dạng ngay tại tầng Controller trước khi đi vào Service và DB.
 * - So sánh các annotation cơ bản cho sinh viên:
 *   + @NotNull: Không được phép null (dùng cho Object, Số, Enum).
 *   + @NotEmpty: Không null và độ dài > 0 (chuỗi không được rỗng "").
 *   + @NotBlank: Không null, không rỗng và không được toàn khoảng trắng "   " (dùng cho String).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {

    @Schema(description = "Họ và tên sinh viên", example = "Nguyen Van A")
    @NotBlank(message = "Tên sinh viên không được để trống")
    @Size(min = 2, max = 50, message = "Tên sinh viên phải từ 2 đến 50 ký tự")
    private String name;

    @Schema(description = "Email của sinh viên", example = "vana@fpt.edu.vn")
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng hợp lệ (VD: example@domain.com)")
    private String email;

    @Schema(description = "Tuổi sinh viên (từ 18 đến 100)", example = "20")
    @NotNull(message = "Tuổi không được để trống")
    @Min(value = 18, message = "Tuổi sinh viên phải từ 18 tuổi trở lên")
    @Max(value = 100, message = "Tuổi sinh viên không được vượt quá 100")
    private Integer age;

    @Schema(description = "Chuyên ngành sinh viên", example = "SOFTWARE_ENGINEERING")
    @NotNull(message = "Chuyên ngành không được để trống")
    private Major major;
}
