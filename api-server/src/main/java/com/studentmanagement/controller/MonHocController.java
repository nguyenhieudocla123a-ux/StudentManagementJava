package com.studentmanagement.controller;

import com.studentmanagement.entity.MonHoc;
import com.studentmanagement.repository.MonHocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('Admin', 'GiangVien', 'SinhVien')")
public class MonHocController {
    
    private final MonHocRepository monHocRepository;
    
    @GetMapping
    public ResponseEntity<List<MonHoc>> getAllSubjects() {
        return ResponseEntity.ok(monHocRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable String id) {
        return monHocRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<MonHoc>> searchSubjects(@RequestParam String keyword) {
        return ResponseEntity.ok(monHocRepository.findByTenMHContaining(keyword));  // ✅ Sửa từ findByTenMonHocContaining
    }
    
    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody MonHoc monHoc) {
        if (monHocRepository.existsById(monHoc.getMaMH())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Mã môn học đã tồn tại");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            MonHoc saved = monHocRepository.save(monHoc);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Thêm môn học thành công");
            response.put("data", saved);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable String id, @RequestBody MonHoc monHoc) {
        return monHocRepository.findById(id)
            .map(existing -> {
                monHoc.setMaMH(id);  // ✅ Sửa từ setMaMonHoc → setMaMH
                MonHoc updated = monHocRepository.save(monHoc);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Cập nhật môn học thành công");
                response.put("data", updated);
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable String id) {
        return monHocRepository.findById(id)
            .map(monHoc -> {
                monHocRepository.delete(monHoc);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Xóa môn học thành công");
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
