package com.studentmanagement.controller;

import com.studentmanagement.dto.request.TaiKhoanCreateRequest;
import com.studentmanagement.dto.response.TaiKhoanResponse;
import com.studentmanagement.entity.TaiKhoan;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.repository.TaiKhoanRepository;
import com.studentmanagement.service.TaiKhoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('Admin')")
public class TaiKhoanController {

    private final TaiKhoanService taiKhoanService;
    private final TaiKhoanRepository taiKhoanRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody TaiKhoanCreateRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Validate loại người dùng
            String role = request.getLoaiNguoiDung();
            if (role == null || 
                (!role.equals("Admin") && 
                 !role.equals("SinhVien") && 
                 !role.equals("GiangVien"))) {
                response.put("success", false);
                response.put("message", "Loại người dùng không hợp lệ (Admin/SinhVien/GiangVien)");
                return ResponseEntity.badRequest().body(response);
            }

            // Mã hóa mật khẩu
            if (request.getMatKhau() != null && !request.getMatKhau().isEmpty()) {
                request.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
            }

            TaiKhoanResponse created = taiKhoanService.createTaiKhoan(request);
            response.put("success", true);
            response.put("message", "Tạo tài khoản thành công");
            response.put("data", created);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAccounts() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<TaiKhoanResponse> accounts = taiKhoanService.getAllTaiKhoan();
            List<Map<String, String>> data = accounts.stream()
                .map(tk -> {
                    Map<String, String> item = new HashMap<>();
                    item.put("tenDangNhap", tk.getTenDangNhap());
                    item.put("matKhau", "********");
                    item.put("loaiNguoiDung", tk.getLoaiNguoiDung());
                    item.put("onlineStatus", tk.getOnlineStatus());
                    return item;
                })
                .toList();
            response.put("success", true);
            response.put("data", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<Map<String, Object>> getAccount(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!taiKhoanService.existsTaiKhoan(username)) {
                response.put("success", false);
                response.put("message", "Tài khoản không tồn tại");
                return ResponseEntity.notFound().build();
            }
            TaiKhoanResponse tk = taiKhoanService.getTaiKhoanById(username);
            response.put("success", true);
            response.put("data", tk);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<Map<String, Object>> updateAccount(
            @PathVariable String username,
            @RequestBody TaiKhoanCreateRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            TaiKhoan existing = taiKhoanRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Tài khoản", "tên đăng nhập", username));

            String matKhau = request.getMatKhau();
            if (matKhau == null || matKhau.isEmpty() || "password".equals(matKhau)) {
                request.setMatKhau(existing.getMatKhau());
            } else if (!matKhau.startsWith("$2a$") && !matKhau.startsWith("$2b$")) {
                request.setMatKhau(passwordEncoder.encode(matKhau));
            }

            TaiKhoanResponse updated = taiKhoanService.updateTaiKhoan(username, request);
            response.put("success", true);
            response.put("message", "Cập nhật tài khoản thành công");
            response.put("data", updated);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Map<String, Object>> deleteAccount(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        try {
            taiKhoanService.deleteTaiKhoan(username);
            response.put("success", true);
            response.put("message", "Xóa tài khoản thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
