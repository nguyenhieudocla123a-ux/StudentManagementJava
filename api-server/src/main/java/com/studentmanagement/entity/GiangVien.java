package com.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "giangvien")
public class GiangVien {
    
    @Id
    @Column(name = "ma_gv")
    private String maGV;
    
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
}
