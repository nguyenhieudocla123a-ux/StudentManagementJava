package com.studentmanagement.controller;

import com.studentmanagement.entity.DangKyLop;
import com.studentmanagement.repository.DangKyLopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.studentmanagement.entity.LopHocPhan;
import com.studentmanagement.repository.LopHocPhanRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('Admin', 'GiangVien', 'SinhVien')")
public class DangKyLopController {
    
    private final DangKyLopRepository dangKyLopRepository;
    private final LopHocPhanRepository lopHocPhanRepository;
    
    @GetMapping
    public ResponseEntity<List<DangKyLop>> getAllRegistrations() {
        return ResponseEntity.ok(dangKyLopRepository.findAll());
    }
    
    @GetMapping("/{maSinhVien}/{maLopHocPhan}")
    public ResponseEntity<?> getRegistrationById(@PathVariable String maSinhVien, @PathVariable String maLopHocPhan) {
        DangKyLop.DangKyLopId id = new DangKyLop.DangKyLopId();
        id.setMaSV(maSinhVien);  // ✅ Sửa từ setMaSinhVien → setMaSV
        id.setMaLop(maLopHocPhan);  // ✅ Sửa từ setMaLopHocPhan → setMaLop
        return dangKyLopRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<DangKyLop>> getRegistrationsByStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(dangKyLopRepository.findByMaSV(studentId));  // ✅ Sửa từ findByMaSinhVien → findByMaSV
    }
    
    @GetMapping("/class/{classId}")
    public ResponseEntity<?> getRegistrationsByClass(@PathVariable String classId) {
        try {
            List<DangKyLop> registrations = dangKyLopRepository.findByMaLop(classId);  // ✅ Sửa từ findByMaLopHocPhan → findByMaLop
            return ResponseEntity.ok(registrations);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            e.printStackTrace(); // Log chi tiết
            return ResponseEntity.status(500).body(response);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createRegistration(@RequestBody DangKyLop dangKyLop) {
        try {
            // Kiểm tra đã đăng ký chưa
            var existing = dangKyLopRepository.findByMaSVAndMaLop(  // ✅ Sửa từ findByMaSinhVienAndMaLopHocPhan
                dangKyLop.getMaSV(),  // ✅ Sửa từ getMaSinhVien → getMaSV
                dangKyLop.getMaLop()  // ✅ Sửa từ getMaLopHocPhan → getMaLop
            );
            
            if (existing.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Sinh viên đã đăng ký lớp học phần này");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Check deadline
            var lopHocPhanOpt = lopHocPhanRepository.findById(dangKyLop.getMaLop());
            if (lopHocPhanOpt.isPresent()) {
                LopHocPhan lopHocPhan = lopHocPhanOpt.get();
                LocalDateTime now = LocalDateTime.now();
                if (lopHocPhan.getThoiGianMoDangKy() != null && now.isBefore(lopHocPhan.getThoiGianMoDangKy())) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Chưa tới đợt đăng ký lớp học phần này!");
                    return ResponseEntity.badRequest().body(response);
                }
                if (lopHocPhan.getThoiGianDongDangKy() != null && now.isAfter(lopHocPhan.getThoiGianDongDangKy())) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Đã hết hạn đăng ký lớp học phần này!");
                    return ResponseEntity.badRequest().body(response);
                }
                if (!"mo".equalsIgnoreCase(lopHocPhan.getTrangThai())) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Lớp học phần này hiện đang đóng!");
                    return ResponseEntity.badRequest().body(response);
                }
                
                // Kiểm tra sĩ số
                int currentSiSo = lopHocPhan.getSiSoHienTai() != null ? lopHocPhan.getSiSoHienTai() : 0;
                int maxSiSo = lopHocPhan.getSiSoToiDa() != null ? lopHocPhan.getSiSoToiDa() : 0;
                if (currentSiSo >= maxSiSo && maxSiSo > 0) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Lớp học phần này đã đủ số lượng sinh viên!");
                    return ResponseEntity.badRequest().body(response);
                }
                
                // Tăng sĩ số
                lopHocPhan.setSiSoHienTai(currentSiSo + 1);
                lopHocPhanRepository.save(lopHocPhan);
                
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Không tìm thấy thông tin lớp học phần!");
                return ResponseEntity.badRequest().body(response);
            }
            
            DangKyLop saved = dangKyLopRepository.save(dangKyLop);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Đăng ký lớp học phần thành công");
            response.put("data", saved);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/{maSinhVien}/{maLopHocPhan}")
    public ResponseEntity<?> deleteRegistration(@PathVariable String maSinhVien, @PathVariable String maLopHocPhan) {
        DangKyLop.DangKyLopId id = new DangKyLop.DangKyLopId();
        id.setMaSV(maSinhVien);  // ✅ Sửa từ setMaSinhVien → setMaSV
        id.setMaLop(maLopHocPhan);  // ✅ Sửa từ setMaLopHocPhan → setMaLop
        return dangKyLopRepository.findById(id)
            .map(dangKyLop -> {
                // Kiểm tra deadline trước khi hủy
                var lopHocPhanOpt = lopHocPhanRepository.findById(dangKyLop.getMaLop());
                if (lopHocPhanOpt.isPresent()) {
                    LopHocPhan lopHocPhan = lopHocPhanOpt.get();
                    LocalDateTime now = LocalDateTime.now();
                    
                    if (!"mo".equalsIgnoreCase(lopHocPhan.getTrangThai())) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "Không thể hủy đăng ký vì lớp học phần này đã đóng!");
                        return ResponseEntity.badRequest().body(response);
                    }
                    if (lopHocPhan.getThoiGianDongDangKy() != null && now.isAfter(lopHocPhan.getThoiGianDongDangKy())) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "Đã quá hạn, không thể hủy đăng ký lớp học phần này!");
                        return ResponseEntity.badRequest().body(response);
                    }
                    
                    // Giảm sĩ số
                    int currentSiSo = lopHocPhan.getSiSoHienTai() != null ? lopHocPhan.getSiSoHienTai() : 0;
                    if (currentSiSo > 0) {
                        lopHocPhan.setSiSoHienTai(currentSiSo - 1);
                        lopHocPhanRepository.save(lopHocPhan);
                    }
                }
                
                dangKyLopRepository.delete(dangKyLop);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Hủy đăng ký thành công");
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
