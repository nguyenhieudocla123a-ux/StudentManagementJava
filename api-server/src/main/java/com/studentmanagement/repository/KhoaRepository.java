package com.studentmanagement.repository;

import com.studentmanagement.entity.Khoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhoaRepository extends JpaRepository<Khoa, String> {
    Optional<Khoa> findByTenKhoa(String tenKhoa);
}
