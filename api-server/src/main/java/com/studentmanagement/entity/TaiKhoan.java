package com.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TAIKHOAN")
public class TaiKhoan {
    
    @Id
    @Column(name = "ten_dang_nhap")
    private String tenDangNhap;
    
    @Column(name = "mat_khau")
    private String matKhau;
    
    @Column(name = "loai_nguoi_dung")
    private String loaiNguoiDung;
    
    @Column(name = "online_status")
    private String onlineStatus;
}
