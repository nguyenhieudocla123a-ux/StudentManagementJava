package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.SinhVienCreateRequest;
import com.studentmanagement.dto.response.SinhVienResponse;
import com.studentmanagement.entity.SinhVien;
import com.studentmanagement.entity.TaiKhoan;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.repository.SinhVienRepository;
import com.studentmanagement.repository.TaiKhoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SinhVienServiceImplTest {

    @Mock
    private SinhVienRepository sinhVienRepository;

    @Mock
    private TaiKhoanRepository taiKhoanRepository;

    @InjectMocks
    private SinhVienServiceImpl sinhVienService;

    private SinhVien sinhVien;
    private TaiKhoan taiKhoan;
    private SinhVienCreateRequest request;

    @BeforeEach
    void setUp() {
        sinhVien = new SinhVien();
        sinhVien.setMaSV("SV001");
        sinhVien.setHoTen("Nguyen Van A");
        sinhVien.setTenDangNhap("sv001");
        sinhVien.setEmail("sv001@example.com");
        sinhVien.setSoDienThoai("0987654321");
        sinhVien.setMaKhoa("CNTT");
        sinhVien.setNgaySinh(LocalDate.of(2002, 1, 1));
        sinhVien.setGioiTinh("Nam");
        sinhVien.setDiaChi("Ha Noi");

        taiKhoan = new TaiKhoan();
        taiKhoan.setTenDangNhap("sv001");
        taiKhoan.setMatKhau("123456");
        taiKhoan.setLoaiNguoiDung("SinhVien");

        request = new SinhVienCreateRequest(
                "SV001", "Nguyen Van A", "sv001", "sv001@example.com",
                "0987654321", "CNTT", LocalDate.of(2002, 1, 1), "Nam", "Ha Noi"
        );
    }

    @Test
    void getAllSinhVien_ShouldReturnList() {
        when(sinhVienRepository.findAll()).thenReturn(List.of(sinhVien));

        List<SinhVienResponse> result = sinhVienService.getAllSinhVien();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("SV001", result.get(0).getMaSV());
        verify(sinhVienRepository, times(1)).findAll();
    }

    @Test
    void getSinhVienById_WhenExists_ShouldReturnStudent() {
        when(sinhVienRepository.findById("SV001")).thenReturn(Optional.of(sinhVien));

        SinhVienResponse result = sinhVienService.getSinhVienById("SV001");

        assertNotNull(result);
        assertEquals("SV001", result.getMaSV());
        assertEquals("Nguyen Van A", result.getHoTen());
    }

    @Test
    void getSinhVienById_WhenNotExists_ShouldThrowException() {
        when(sinhVienRepository.findById("SV999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sinhVienService.getSinhVienById("SV999"));
    }

    @Test
    void createSinhVien_WhenValid_ShouldSaveStudent() {
        when(sinhVienRepository.existsById(request.getMaSV())).thenReturn(false);
        when(taiKhoanRepository.findById(request.getTenDangNhap())).thenReturn(Optional.of(taiKhoan));
        when(sinhVienRepository.existsByTenDangNhap(request.getTenDangNhap())).thenReturn(false);
        when(sinhVienRepository.save(any(SinhVien.class))).thenReturn(sinhVien);

        SinhVienResponse result = sinhVienService.createSinhVien(request);

        assertNotNull(result);
        assertEquals("SV001", result.getMaSV());
        verify(sinhVienRepository, times(1)).save(any(SinhVien.class));
    }

    @Test
    void createSinhVien_WhenIdExists_ShouldThrowBadRequestException() {
        when(sinhVienRepository.existsById(request.getMaSV())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> sinhVienService.createSinhVien(request));
    }

    @Test
    void createSinhVien_WhenAccountNotExists_ShouldThrowResourceNotFoundException() {
        when(sinhVienRepository.existsById(request.getMaSV())).thenReturn(false);
        when(taiKhoanRepository.findById(request.getTenDangNhap())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sinhVienService.createSinhVien(request));
    }

    @Test
    void updateSinhVien_WhenExists_ShouldUpdateAndSave() {
        when(sinhVienRepository.findById("SV001")).thenReturn(Optional.of(sinhVien));
        when(sinhVienRepository.save(any(SinhVien.class))).thenReturn(sinhVien);

        SinhVienResponse result = sinhVienService.updateSinhVien("SV001", request);

        assertNotNull(result);
        assertEquals("SV001", result.getMaSV());
        verify(sinhVienRepository, times(1)).save(sinhVien);
    }

    @Test
    void deleteSinhVien_WhenExists_ShouldDelete() {
        when(sinhVienRepository.findById("SV001")).thenReturn(Optional.of(sinhVien));
        doNothing().when(sinhVienRepository).delete(sinhVien);

        assertDoesNotThrow(() -> sinhVienService.deleteSinhVien("SV001"));
        verify(sinhVienRepository, times(1)).delete(sinhVien);
    }
}
