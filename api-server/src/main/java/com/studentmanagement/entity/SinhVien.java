package com.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "SINHVIEN")
public class SinhVien {
    
    @Id
    @Column(name = "ma_sv")
    private String maSV;  // ✅ Sửa từ maSinhVien → maSV
    
    @Column(name = "ho_ten")
    private String hoTen;
    
    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;
    
    @Column(name = "gioi_tinh")
    private String gioiTinh;
    
    @Column(name = "dia_chi")
    private String diaChi;
    
    @Column(name = "so_dien_thoai")
    private String soDienThoai;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "ma_khoa")
    private String maKhoa;
    
    @Column(name = "ten_dang_nhap")
    private String tenDangNhap;
    
    @ManyToOne
    @JoinColumn(name = "ma_khoa", insertable = false, updatable = false)
    private Khoa khoa;
}
