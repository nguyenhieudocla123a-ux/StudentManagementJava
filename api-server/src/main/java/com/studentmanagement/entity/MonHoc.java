package com.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "monhoc")
public class MonHoc {
    
    @Id
    @Column(name = "ma_mh")
    private String maMH;
    
    @Column(name = "ten_mh")
    private String tenMH;
    
    @Column(name = "so_tin_chi")
    private Integer soTinChi;
    
    @Column(name = "mo_ta")
    private String moTa;
    
    @Column(name = "ma_khoa")
    private String maKhoa;
}
