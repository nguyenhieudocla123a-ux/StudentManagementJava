package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.TaiKhoanCreateRequest;
import com.studentmanagement.dto.response.TaiKhoanResponse;
import com.studentmanagement.entity.TaiKhoan;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.repository.TaiKhoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaiKhoanServiceImplTest {

    @Mock
    private TaiKhoanRepository taiKhoanRepository;

    @InjectMocks
    private TaiKhoanServiceImpl taiKhoanService;

    private TaiKhoan taiKhoan;
    private TaiKhoanCreateRequest request;

    @BeforeEach
    void setUp() {
        taiKhoan = new TaiKhoan();
        taiKhoan.setTenDangNhap("admin");
        taiKhoan.setMatKhau("password123");
        taiKhoan.setLoaiNguoiDung("Admin");
        taiKhoan.setOnlineStatus("Offline");

        request = new TaiKhoanCreateRequest("admin", "password123", "Admin");
    }

    @Test
    void getAllTaiKhoan_ShouldReturnList() {
        when(taiKhoanRepository.findAll()).thenReturn(List.of(taiKhoan));

        List<TaiKhoanResponse> result = taiKhoanService.getAllTaiKhoan();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("admin", result.get(0).getTenDangNhap());
        verify(taiKhoanRepository, times(1)).findAll();
    }

    @Test
    void getTaiKhoanById_WhenExists_ShouldReturnAccount() {
        when(taiKhoanRepository.findById("admin")).thenReturn(Optional.of(taiKhoan));

        TaiKhoanResponse result = taiKhoanService.getTaiKhoanById("admin");

        assertNotNull(result);
        assertEquals("admin", result.getTenDangNhap());
        assertEquals("Admin", result.getLoaiNguoiDung());
    }

    @Test
    void getTaiKhoanById_WhenNotExists_ShouldThrowException() {
        when(taiKhoanRepository.findById("unknown")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taiKhoanService.getTaiKhoanById("unknown"));
    }

    @Test
    void createTaiKhoan_WhenValid_ShouldSaveAccount() {
        when(taiKhoanRepository.existsById(request.getTenDangNhap())).thenReturn(false);
        when(taiKhoanRepository.save(any(TaiKhoan.class))).thenReturn(taiKhoan);

        TaiKhoanResponse result = taiKhoanService.createTaiKhoan(request);

        assertNotNull(result);
        assertEquals("admin", result.getTenDangNhap());
        verify(taiKhoanRepository, times(1)).save(any(TaiKhoan.class));
    }

    @Test
    void createTaiKhoan_WhenUsernameExists_ShouldThrowBadRequestException() {
        when(taiKhoanRepository.existsById(request.getTenDangNhap())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> taiKhoanService.createTaiKhoan(request));
    }

    @Test
    void updateTaiKhoan_WhenExists_ShouldUpdateAndSave() {
        when(taiKhoanRepository.findById("admin")).thenReturn(Optional.of(taiKhoan));
        when(taiKhoanRepository.save(any(TaiKhoan.class))).thenReturn(taiKhoan);

        TaiKhoanResponse result = taiKhoanService.updateTaiKhoan("admin", request);

        assertNotNull(result);
        assertEquals("admin", result.getTenDangNhap());
        verify(taiKhoanRepository, times(1)).save(taiKhoan);
    }

    @Test
    void deleteTaiKhoan_WhenExists_ShouldDelete() {
        when(taiKhoanRepository.findById("admin")).thenReturn(Optional.of(taiKhoan));
        doNothing().when(taiKhoanRepository).delete(taiKhoan);

        assertDoesNotThrow(() -> taiKhoanService.deleteTaiKhoan("admin"));
        verify(taiKhoanRepository, times(1)).delete(taiKhoan);
    }
}
