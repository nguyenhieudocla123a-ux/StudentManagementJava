package com.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "KHOA")
public class Khoa {
    
    @Id
    @Column(name = "ma_khoa")
    private String maKhoa;
    
    @Column(name = "ten_khoa")
    private String tenKhoa;
}
