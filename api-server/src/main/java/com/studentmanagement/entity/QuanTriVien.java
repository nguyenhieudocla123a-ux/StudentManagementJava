package com.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "QUANTRIVIEN")
public class QuanTriVien {

    @Id
    @Column(name = "ma_qtv")
    private String maQTV;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "email")
    private String email;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "ten_dang_nhap")
    private String tenDangNhap;
}
