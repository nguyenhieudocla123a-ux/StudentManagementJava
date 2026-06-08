package com.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "GIANGVIEN")
public class GiangVien {
    
    @Id
    @Column(name = "ma_gv")
    private String maGV;  // ✅ Sửa từ maGiangVien → maGV để khớp với Desktop App
    
    @Column(name = "ho_ten")
    private String hoTen;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "so_dien_thoai")
    private String soDienThoai;
    
    @Column(name = "ma_khoa")
    private String maKhoa;
    
    @Column(name = "ten_dang_nhap")
    private String tenDangNhap;
    
    @ManyToOne
    @JoinColumn(name = "ma_khoa", insertable = false, updatable = false)
    private Khoa khoa;
}
