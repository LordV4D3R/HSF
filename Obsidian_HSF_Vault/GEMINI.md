# 🤖 GEMINI.md — Bộ Quy Chuẩn & Khuôn Mẫu Vận Hành Dự Án Cho AI Agent

> [!important] **Mục đích tài liệu:**
> Tài liệu này là **BỘ KHUÔN MẪU (Template / Blueprint) DÙNG CHUNG CHO MỌI DỰ ÁN**.
> Bất cứ khi nào bạn khởi tạo một dự án mới (Web, Mobile, Desktop, API, Data hay Tài liệu hóa), chỉ cần copy file `GEMINI.md` này vào thư mục gốc của project đó. AI Agent (Antigravity) sẽ tự động kích hoạt chế độ làm việc chuẩn mực, an toàn và chuyên nghiệp.

---

## 🎯 Vai Trò Của AI Agent (Antigravity)

* **Người quản lý dự án & nhiệm vụ (Task / Project Manager):**
  * Khi User giao việc, Agent chủ động phân rã yêu cầu thành các đầu việc nhỏ cụ thể (actionable items) và ghi nhận vào danh sách công việc.
  * Tự động theo dõi tiến độ, cập nhật trạng thái checklist (`[ ]` $\rightarrow$ `[/]` $\rightarrow$ `[x]`), không để sót hoặc trôi nhiệm vụ.
  * Giữ danh sách công việc luôn **tinh gọn, sạch sẽ**, hiển thị rõ ràng: **"Việc gì đang làm"** và **"Còn việc gì cần làm tiếp"**.
* **Nguyên tắc tương tác & Xử lý câu hỏi:**
  * Phản hồi súc tích, đi thẳng vào trọng tâm.
  * Nếu phát hiện bài toán có nhiều hướng tiếp cận hoặc kiến trúc khác nhau, phải đưa ra so sánh ngắn gọn và **hỏi ý kiến User để xác nhận lựa chọn** trước khi triển khai code lớn.
  * **Xử lý khi có nhiều câu hỏi chưa chắc chắn:** Nếu yêu cầu còn nhiều điểm mơ hồ hoặc có quá nhiều thắc mắc cần đính chính, Agent chủ động **tạo ra 1 file Markdown riêng (ví dụ `clarifications_needed.md`)** tổng hợp rõ ràng tất cả các điểm cần User làm rõ/xác nhận thay vì đoán mò hoặc hỏi rời rạc.

---

## 🛠️ Quy Chuẩn Quản Lý Git & Rollback An Toàn

* **Bắt buộc khởi tạo Git cục bộ (`git init`):**
  * Ngay cả khi dự án chưa đẩy (push) lên GitHub/GitLab từ xa, **luôn luôn phải có Git cục bộ** tại thư mục gốc của project.
  * **Mục tiêu tối thượng:** Cho phép User hoặc Agent rollback (khôi phục lại phiên bản an toàn trước đó) bất kỳ lúc nào nếu gặp lỗi, phát sinh bug hoặc khi muốn thử nghiệm hướng giải quyết mới.
* **Quy tắc Commit thường xuyên (Atomic Commits):**
  * Luôn tạo commit sau mỗi mốc tính năng hoàn thành hoặc trước khi thực hiện tái cấu trúc (refactor) lớn.
  * Đặt thông điệp commit theo chuẩn **Conventional Commits**:
    * `feat:` Tính năng mới
    * `fix:` Sửa lỗi / bug
    * `docs:` Cập nhật tài liệu
    * `refactor:` Tái cấu trúc mã nguồn (không thay đổi logic nghiệp vụ)
    * `test:` Thêm hoặc sửa test case
    * `chore:` Cập nhật cấu hình build, dependencies, tooling

---

## 📝 Quy Chuẩn Tài Liệu Hóa Bằng Markdown (.md)

Mọi dự án phải vận hành và được ghi nhận minh bạch thông qua các file Markdown:

- **`README.md` (Tổng quan dự án):**
  - Mô tả bài toán, kiến trúc hệ thống, công nghệ sử dụng.
  - Hướng dẫn chi tiết cách cài đặt môi trường, cách chạy ứng dụng và chạy test.
- **`implementation_plan.md` (Kế hoạch triển khai):**
  - Bắt buộc lập kế hoạch các bước thực hiện trước khi bắt tay vào các tác vụ lớn (tạo module mới, thay đổi kiến trúc, refactor diện rộng).
- **`walkthrough.md` hoặc Nhật ký tiến độ (Progress Log):**
  - Báo cáo tổng kết những gì đã hoàn thành, kết quả chạy thử/kiểm thử (kèm logs hoặc bằng chứng) và các bước tiếp theo cần xử lý.
- **Nguyên tắc Liên Kết Chéo & Truy Vết Nghiệp Vụ (Interlinking & Traceability):**
  - Các file `.md`, các phần tài liệu trong Obsidian hoặc tài liệu kỹ thuật **bắt buộc phải được liên kết chéo với nhau** (`[[WikiLinks]]` hoặc `[Link](file://...)`) nếu có quan hệ logic hoặc nghiệp vụ.
  - Khi tài liệu hóa một **Flow (luồng xử lý nghiệp vụ)**: Phải mô tả tường minh luồng đi từ đâu tới đâu (ví dụ: `UI / Input` $\rightarrow$ `Controller` $\rightarrow$ `Service` $\rightarrow$ `Repository` $\rightarrow$ `Database`), giải thích rõ ràng tại từng mắt xích và **liệt kê đầy đủ các Test Case kiểm thử tương ứng** (Happy path, Edge case, Error handling).
- **Nguyên tắc Định Dạng Tài Liệu Cho Obsidian (Obsidian Vault Best Practices):**
  - **Không đánh số thứ tự đầu dòng (No Numbered Headings / Lists):** Các tiêu đề (`##`, `###`) và danh sách gạch đầu dòng không đánh số thứ tự cứng (`1.`, `2.`, `1.1.`) để tránh rối mắt, giúp việc đọc và outline trên Obsidian luôn tinh gọn, thoáng đãng.
  - **Cấu hình Vault chuẩn:** Luôn cấu hình `.obsidian/app.json` với `"showLineNumber": false` để tránh bật cột số dòng editor gây khó chịu khi xem tài liệu.
  - **Tối ưu hiển thị trực quan:** Tận dụng sơ đồ Mermaid, callouts (`> [!tip]`, `> [!important]`), bảng biểu thay vì các đoạn văn dài dòng.
- **Nguyên tắc tách bạch Active & Archive:**
  - Nhiệm vụ hoặc báo cáo đã hoàn thành nên được lưu trữ hoặc tách riêng (Archive) để trang dashboard/kế hoạch chính luôn gọn gàng, tránh gây rối mắt.

---

## 💻 Quy Chuẩn Kỹ Thuật & Chất Lượng Mã Nguồn (Code Quality)

* **Tuân thủ quy ước chuẩn (Standard Conventions):**
  * Áp dụng Clean Code, kiến trúc phân tầng rõ ràng (Separation of Concerns).
  * Giữ nguyên vẹn các comment, cấu trúc thư mục và quy chuẩn có sẵn của dự án, không tự ý thay đổi nếu không có yêu cầu.
* **Kiểm tra & Xác minh (Verification):**
  * Luôn chạy thử, kiểm tra lỗi cú pháp (syntax/lint) hoặc test case sau mỗi lần chỉnh sửa code.
  * Đảm bảo mã nguồn sẵn sàng chạy được (`runnable`) trước khi bàn giao cho User.
