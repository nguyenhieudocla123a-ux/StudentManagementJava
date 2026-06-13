package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.KhoaCreateRequest;
import com.studentmanagement.dto.response.KhoaResponse;
import com.studentmanagement.entity.Khoa;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.mapper.KhoaMapper;
import com.studentmanagement.repository.KhoaRepository;
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
public class KhoaServiceImplTest {

    @Mock
    private KhoaRepository khoaRepository;

    @Mock
    private KhoaMapper khoaMapper;

    @InjectMocks
    private KhoaServiceImpl khoaService;

    private Khoa khoa;
    private KhoaResponse khoaResponse;
    private KhoaCreateRequest request;

    @BeforeEach
    void setUp() {
        khoa = new Khoa();
        khoa.setMaKhoa("CNTT");
        khoa.setTenKhoa("Cong nghe thong tin");

        khoaResponse = new KhoaResponse("CNTT", "Cong nghe thong tin");
        request = new KhoaCreateRequest("CNTT", "Cong nghe thong tin");
    }

    @Test
    void getAllKhoa_ShouldReturnList() {
        when(khoaRepository.findAll()).thenReturn(List.of(khoa));
        when(khoaMapper.toResponse(khoa)).thenReturn(khoaResponse);

        List<KhoaResponse> result = khoaService.getAllKhoa();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CNTT", result.get(0).getMaKhoa());
        verify(khoaRepository, times(1)).findAll();
    }

    @Test
    void getKhoaById_WhenExists_ShouldReturnKhoa() {
        when(khoaRepository.findById("CNTT")).thenReturn(Optional.of(khoa));
        when(khoaMapper.toResponse(khoa)).thenReturn(khoaResponse);

        KhoaResponse result = khoaService.getKhoaById("CNTT");

        assertNotNull(result);
        assertEquals("CNTT", result.getMaKhoa());
        assertEquals("Cong nghe thong tin", result.getTenKhoa());
    }

    @Test
    void getKhoaById_WhenNotExists_ShouldThrowException() {
        when(khoaRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> khoaService.getKhoaById("UNKNOWN"));
    }

    @Test
    void createKhoa_WhenValid_ShouldSaveKhoa() {
        when(khoaRepository.existsById(request.getMaKhoa())).thenReturn(false);
        when(khoaRepository.findByTenKhoa(request.getTenKhoa())).thenReturn(Optional.empty());
        when(khoaMapper.toEntity(request)).thenReturn(khoa);
        when(khoaRepository.save(any(Khoa.class))).thenReturn(khoa);
        when(khoaMapper.toResponse(khoa)).thenReturn(khoaResponse);

        KhoaResponse result = khoaService.createKhoa(request);

        assertNotNull(result);
        assertEquals("CNTT", result.getMaKhoa());
        verify(khoaRepository, times(1)).save(any(Khoa.class));
    }

    @Test
    void createKhoa_WhenIdExists_ShouldThrowBadRequestException() {
        when(khoaRepository.existsById(request.getMaKhoa())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> khoaService.createKhoa(request));
    }

    @Test
    void createKhoa_WhenNameExists_ShouldThrowBadRequestException() {
        when(khoaRepository.existsById(request.getMaKhoa())).thenReturn(false);
        when(khoaRepository.findByTenKhoa(request.getTenKhoa())).thenReturn(Optional.of(khoa));

        assertThrows(BadRequestException.class, () -> khoaService.createKhoa(request));
    }

    @Test
    void updateKhoa_WhenExists_ShouldUpdateAndSave() {
        when(khoaRepository.findById("CNTT")).thenReturn(Optional.of(khoa));
        doNothing().when(khoaMapper).updateEntityFromRequest(request, khoa);
        when(khoaRepository.save(any(Khoa.class))).thenReturn(khoa);
        when(khoaMapper.toResponse(khoa)).thenReturn(khoaResponse);

        KhoaResponse result = khoaService.updateKhoa("CNTT", request);

        assertNotNull(result);
        assertEquals("CNTT", result.getMaKhoa());
        verify(khoaRepository, times(1)).save(khoa);
    }

    @Test
    void deleteKhoa_WhenExists_ShouldDelete() {
        when(khoaRepository.findById("CNTT")).thenReturn(Optional.of(khoa));
        doNothing().when(khoaRepository).delete(khoa);

        assertDoesNotThrow(() -> khoaService.deleteKhoa("CNTT"));
        verify(khoaRepository, times(1)).delete(khoa);
    }
}
