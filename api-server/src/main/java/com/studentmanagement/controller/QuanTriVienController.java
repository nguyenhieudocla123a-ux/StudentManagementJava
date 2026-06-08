package com.studentmanagement.controller;

import com.studentmanagement.entity.QuanTriVien;
import com.studentmanagement.entity.TaiKhoan;
import com.studentmanagement.repository.QuanTriVienRepository;
import com.studentmanagement.repository.TaiKhoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('Admin')")
public class QuanTriVienController {

    private final QuanTriVienRepository quanTriVienRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    // GET /admins - Lấy tất cả quản trị viên
    @GetMapping
    public ResponseEntity<List<QuanTriVien>> getAllAdmins() {
        return ResponseEntity.ok(quanTriVienRepository.findAll());
    }

    // GET /admins/{id} - Lấy quản trị viên theo mã
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable String id) {
        return quanTriVienRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // GET /admins/account/{tenDangNhap} - Lấy thông tin admin theo tài khoản
    @GetMapping("/account/{tenDangNhap}")
    public ResponseEntity<?> getAdminByAccount(@PathVariable String tenDangNhap) {
        return quanTriVienRepository.findByTenDangNhap(tenDangNhap)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // POST /admins - Thêm quản trị viên mới
    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody QuanTriVien quanTriVien) {
        // Kiểm tra mã QTV đã tồn tại chưa
        if (quanTriVienRepository.existsById(quanTriVien.getMaQTV())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Mã quản trị viên đã tồn tại");
            return ResponseEntity.badRequest().body(response);
        }

        // Kiểm tra tài khoản có tồn tại và đúng role "Admin" không
        TaiKhoan taiKhoan = taiKhoanRepository.findById(quanTriVien.getTenDangNhap()).orElse(null);
        if (taiKhoan == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Tài khoản không tồn tại");
            return ResponseEntity.badRequest().body(response);
        }
        if (!"Admin".equals(taiKhoan.getLoaiNguoiDung())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Tài khoản này không có quyền Admin (Role: " + taiKhoan.getLoaiNguoiDung() + ")");
            return ResponseEntity.badRequest().body(response);
        }

        // Kiểm tra tài khoản đã liên kết với admin khác chưa
        if (quanTriVienRepository.findByTenDangNhap(quanTriVien.getTenDangNhap()).isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Tài khoản này đã được liên kết với quản trị viên khác");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            QuanTriVien saved = quanTriVienRepository.save(quanTriVien);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Thêm quản trị viên thành công");
            response.put("data", saved);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // PUT /admins/{id} - Cập nhật thông tin quản trị viên
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable String id, @RequestBody QuanTriVien quanTriVien) {
        return quanTriVienRepository.findById(id)
            .map(existing -> {
                quanTriVien.setMaQTV(id);
                QuanTriVien updated = quanTriVienRepository.save(quanTriVien);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Cập nhật quản trị viên thành công");
                response.put("data", updated);
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /admins/{id} - Xóa quản trị viên
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable String id) {
        return quanTriVienRepository.findById(id)
            .map(qtv -> {
                quanTriVienRepository.delete(qtv);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Xóa quản trị viên thành công (tài khoản " + qtv.getTenDangNhap() + " vẫn còn)");
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
