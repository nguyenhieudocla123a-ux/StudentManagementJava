package com.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MONHOC")
public class MonHoc {
    
    @Id
    @Column(name = "ma_mh")
    private String maMH;  // ✅ Sửa từ maMonHoc → maMH
    
    @Column(name = "ten_mh")
    private String tenMH;  // ✅ Sửa từ tenMonHoc → tenMH
    
    @Column(name = "so_tin_chi")
    private Integer soTinChi;
    
    @Column(name = "mo_ta")
    private String moTa;
    
    @Column(name = "ma_khoa")
    private String maKhoa;
    
    @ManyToOne
    @JoinColumn(name = "ma_khoa", insertable = false, updatable = false)
    private Khoa khoa;
}
