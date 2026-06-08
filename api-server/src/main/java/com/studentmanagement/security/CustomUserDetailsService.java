package com.studentmanagement.security;

import com.studentmanagement.entity.TaiKhoan;
import com.studentmanagement.repository.TaiKhoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final TaiKhoanRepository taiKhoanRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TaiKhoan taiKhoan = taiKhoanRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy tài khoản: " + username));

        return new User(
                taiKhoan.getTenDangNhap(),
                taiKhoan.getMatKhau(),
                getAuthorities(taiKhoan.getLoaiNguoiDung())
        );
    }

    // Chuyển đổi loaiNguoiDung (Admin, SinhVien, GiangVien) thành Spring Security Roles
    private Collection<? extends GrantedAuthority> getAuthorities(String loaiNguoiDung) {
        // Prefix ROLE_ là quy ước bắt buộc của Spring Security
        return List.of(new SimpleGrantedAuthority("ROLE_" + loaiNguoiDung));
    }
}
