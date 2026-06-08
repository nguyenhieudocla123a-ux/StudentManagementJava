package com.studentmanagement.controller;

import com.studentmanagement.entity.TaiKhoan;
import com.studentmanagement.repository.TaiKhoanRepository;
import com.studentmanagement.repository.QuanTriVienRepository;
import com.studentmanagement.repository.SinhVienRepository;
import com.studentmanagement.repository.GiangVienRepository;
import com.studentmanagement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final TaiKhoanRepository taiKhoanRepository;
    private final QuanTriVienRepository quanTriVienRepository;
    private final SinhVienRepository sinhVienRepository;
    private final GiangVienRepository giangVienRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        // Validate input
        if (username == null || username.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Tên đăng nhập không được để trống");
            return ResponseEntity.status(401).body(response);
        }

        System.out.println("🔐 Login attempt: username=" + username);
        
        // Tìm tài khoản trong DB
        TaiKhoan taiKhoan = taiKhoanRepository.findById(username).orElse(null);
        if (taiKhoan == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Tên đăng nhập không tồn tại");
            return ResponseEntity.status(401).body(response);
        }

        // Kiểm tra password - hỗ trợ cả plain text (tài khoản cũ) và BCrypt
        boolean isPasswordCorrect = false;
        String storedPassword = taiKhoan.getMatKhau();
        
        if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$")) {
            // Password đã được mã hóa BCrypt
            isPasswordCorrect = passwordEncoder.matches(password, storedPassword);
        } else if (storedPassword.length() == 64) {
            // Password SHA-256 (hệ thống cũ)
            isPasswordCorrect = verifyPasswordSHA256(password, storedPassword);
        } else {
            // Plain text (tài khoản test/dev)
            isPasswordCorrect = password.equals(storedPassword);
        }
        
        if (!isPasswordCorrect) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Mật khẩu không đúng");
            return ResponseEntity.status(401).body(response);
        }
        
        // Xác thực thành công -> Sinh JWT token
        // Tạo UserDetails thủ công để sinh token (vì password có thể chưa phải BCrypt)
        org.springframework.security.core.userdetails.User userDetails = 
            new org.springframework.security.core.userdetails.User(
                taiKhoan.getTenDangNhap(),
                taiKhoan.getMatKhau(),
                java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(
                    "ROLE_" + taiKhoan.getLoaiNguoiDung()))
            );
        
        // Thêm extra claims vào token
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", taiKhoan.getLoaiNguoiDung());
        
        String jwtToken = jwtService.generateToken(extraClaims, userDetails);
        
        // Tạo response
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Đăng nhập thành công");
        response.put("token", jwtToken);
        response.put("tenDangNhap", taiKhoan.getTenDangNhap());
        response.put("loaiNguoiDung", taiKhoan.getLoaiNguoiDung());
        response.put("onlineStatus", "Online");
        
        // Nếu là Admin, trả thêm thông tin quản trị viên
        if ("Admin".equals(taiKhoan.getLoaiNguoiDung())) {
            quanTriVienRepository.findByTenDangNhap(taiKhoan.getTenDangNhap())
                .ifPresent(qtv -> {
                    response.put("maQTV", qtv.getMaQTV());
                    response.put("hoTen", qtv.getHoTen());
                });
        }
        
        // Nếu là SinhVien, trả thêm mã SV
        if ("SinhVien".equals(taiKhoan.getLoaiNguoiDung())) {
            sinhVienRepository.findByTenDangNhap(taiKhoan.getTenDangNhap())
                .ifPresent(sv -> {
                    response.put("maSV", sv.getMaSV());
                    response.put("hoTen", sv.getHoTen());
                });
        }
        
        // Nếu là GiangVien, trả thêm mã GV
        if ("GiangVien".equals(taiKhoan.getLoaiNguoiDung())) {
            giangVienRepository.findByTenDangNhap(taiKhoan.getTenDangNhap())
                .ifPresent(gv -> {
                    response.put("maGV", gv.getMaGV());
                    response.put("hoTen", gv.getHoTen());
                });
        }
        
        // Cập nhật trạng thái online
        taiKhoan.setOnlineStatus("Online");
        taiKhoanRepository.save(taiKhoan);
        
        System.out.println("✅ Login thành công: " + username + " | Role: " + taiKhoan.getLoaiNguoiDung());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody TaiKhoan taiKhoan) {
        // Kiểm tra tên đăng nhập đã tồn tại
        if (taiKhoanRepository.existsById(taiKhoan.getTenDangNhap())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Tên đăng nhập đã tồn tại");
            return ResponseEntity.badRequest().body(response);
        }
        
        // Validate loại người dùng
        if (taiKhoan.getLoaiNguoiDung() == null || 
            (!taiKhoan.getLoaiNguoiDung().equals("Admin") && 
             !taiKhoan.getLoaiNguoiDung().equals("SinhVien") && 
             !taiKhoan.getLoaiNguoiDung().equals("GiangVien"))) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Loại người dùng không hợp lệ (Admin/SinhVien/GiangVien)");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            // Hash password bằng BCrypt (chuẩn Spring Security)
            String hashedPassword = passwordEncoder.encode(taiKhoan.getMatKhau());
            taiKhoan.setMatKhau(hashedPassword);
            
            // Lưu tài khoản
            TaiKhoan saved = taiKhoanRepository.save(taiKhoan);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Tạo tài khoản thành công");
            response.put("data", saved);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        if (username != null) {
            taiKhoanRepository.findById(username).ifPresent(tk -> {
                tk.setOnlineStatus("Offline");
                taiKhoanRepository.save(tk);
            });
        }
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Đăng xuất thành công");
        return ResponseEntity.ok(response);
    }

    // Hỗ trợ tương thích ngược với password SHA-256 từ hệ thống cũ
    private boolean verifyPasswordSHA256(String plainPassword, String hashedPassword) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(plainPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().equals(hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }
}
