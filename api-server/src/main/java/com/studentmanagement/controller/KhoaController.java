package com.studentmanagement.controller;

import com.studentmanagement.entity.Khoa;
import com.studentmanagement.repository.KhoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/khoa")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('Admin', 'GiangVien', 'SinhVien')")
public class KhoaController {

    @Autowired
    private KhoaRepository khoaRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllKhoa() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Khoa> list = khoaRepository.findAll();
            response.put("success", true);
            response.put("message", "Lấy danh sách khoa thành công");
            response.put("data", list);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getKhoaById(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Khoa khoa = khoaRepository.findById(id).orElse(null);
            if (khoa != null) {
                response.put("success", true);
                response.put("message", "Lấy thông tin khoa thành công");
                response.put("data", khoa);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Không tìm thấy khoa");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createKhoa(@RequestBody Khoa khoa) {
        Map<String, Object> response = new HashMap<>();
        try {
            Khoa saved = khoaRepository.save(khoa);
            response.put("success", true);
            response.put("message", "Thêm khoa thành công");
            response.put("data", saved);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateKhoa(@PathVariable String id, @RequestBody Khoa khoa) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (khoaRepository.existsById(id)) {
                khoa.setMaKhoa(id);
                Khoa updated = khoaRepository.save(khoa);
                response.put("success", true);
                response.put("message", "Cập nhật khoa thành công");
                response.put("data", updated);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Không tìm thấy khoa");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteKhoa(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (khoaRepository.existsById(id)) {
                khoaRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Xóa khoa thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Không tìm thấy khoa");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
