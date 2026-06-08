package com.studentmanagement.controller;

import com.studentmanagement.entity.LopHocPhan;
import com.studentmanagement.repository.LopHocPhanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.LocalDate;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('Admin', 'GiangVien', 'SinhVien')")
public class LopHocPhanController {
    
    private final LopHocPhanRepository lopHocPhanRepository;
    
    @GetMapping
    public ResponseEntity<List<LopHocPhan>> getAllClasses() {
        return ResponseEntity.ok(lopHocPhanRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getClassById(@PathVariable String id) {
        return lopHocPhanRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<LopHocPhan>> getClassesByTeacher(@PathVariable String teacherId) {
        return ResponseEntity.ok(lopHocPhanRepository.findByMaGV(teacherId));  // ✅ Sửa từ findByMaGiangVien
    }
    
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<LopHocPhan>> getClassesBySubject(@PathVariable String subjectId) {
        return ResponseEntity.ok(lopHocPhanRepository.findByMaMH(subjectId));  // ✅ Sửa từ findByMaMonHoc
    }
    
    @GetMapping("/semester")
    public ResponseEntity<List<LopHocPhan>> getClassesBySemester(
            @RequestParam String hocKy,
            @RequestParam String namHoc) {
        return ResponseEntity.ok(lopHocPhanRepository.findByHocKyAndNamHoc(hocKy, namHoc));
    }
    
    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody LopHocPhan lopHocPhan) {
        try {
            LopHocPhan saved = lopHocPhanRepository.save(lopHocPhan);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Thêm lớp học phần thành công");
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
    public ResponseEntity<?> updateClass(@PathVariable String id, @RequestBody LopHocPhan lopHocPhan) {
        return lopHocPhanRepository.findById(id)
            .map(existing -> {
                lopHocPhan.setMaLop(id);  // ✅ Sửa từ setMaLopHocPhan → setMaLop
                LopHocPhan updated = lopHocPhanRepository.save(lopHocPhan);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Cập nhật lớp học phần thành công");
                response.put("data", updated);
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable String id) {
        return lopHocPhanRepository.findById(id)
            .map(lopHocPhan -> {
                lopHocPhanRepository.delete(lopHocPhan);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Xóa lớp học phần thành công");
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // PUT /classes/{id}/deadline - Thiết lập hạn đăng ký cho lớp học phần (Admin only)
    @PutMapping("/{id}/deadline")
    public ResponseEntity<?> setDeadline(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        return lopHocPhanRepository.findById(id)
            .map(lopHocPhan -> {
                try {
                    if (body.containsKey("thoiGianMoDangKy") && body.get("thoiGianMoDangKy") != null) {
                        lopHocPhan.setThoiGianMoDangKy(LocalDateTime.parse(body.get("thoiGianMoDangKy").replace(" ", "T")));
                    }
                    if (body.containsKey("thoiGianDongDangKy") && body.get("thoiGianDongDangKy") != null) {
                        lopHocPhan.setThoiGianDongDangKy(LocalDateTime.parse(body.get("thoiGianDongDangKy").replace(" ", "T")));
                    }
                    if (body.containsKey("ngayBatDauHoc") && body.get("ngayBatDauHoc") != null) {
                        lopHocPhan.setNgayBatDauHoc(LocalDate.parse(body.get("ngayBatDauHoc")));
                    }
                    if (body.containsKey("ngayKetThucHoc") && body.get("ngayKetThucHoc") != null) {
                        lopHocPhan.setNgayKetThucHoc(LocalDate.parse(body.get("ngayKetThucHoc")));
                    }
                    if (body.containsKey("trangThai") && body.get("trangThai") != null) {
                        lopHocPhan.setTrangThai(body.get("trangThai"));
                    }
                    LopHocPhan updated = lopHocPhanRepository.save(lopHocPhan);
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Cập nhật hạn đăng ký thành công");
                    response.put("data", updated);
                    return ResponseEntity.ok(response);
                } catch (Exception e) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Lỗi: " + e.getMessage());
                    return ResponseEntity.badRequest().body(response);
                }
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // GET /classes/open - Lấy danh sách lớp đang mở đăng ký theo thời gian thực
    @GetMapping("/open")
    public ResponseEntity<List<LopHocPhan>> getOpenClasses() {
        LocalDateTime now = LocalDateTime.now();
        List<LopHocPhan> allClasses = lopHocPhanRepository.findAll();
        List<LopHocPhan> openClasses = allClasses.stream()
            .filter(lhp -> "mo".equalsIgnoreCase(lhp.getTrangThai())
                && (lhp.getThoiGianMoDangKy() == null || !now.isBefore(lhp.getThoiGianMoDangKy()))
                && (lhp.getThoiGianDongDangKy() == null || !now.isAfter(lhp.getThoiGianDongDangKy())))
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(openClasses);
    }
}
