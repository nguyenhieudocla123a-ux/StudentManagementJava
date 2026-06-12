package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.LopHocPhanCreateRequest;
import com.studentmanagement.dto.response.LopHocPhanResponse;
import com.studentmanagement.entity.LopHocPhan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for LopHocPhan (Class/Section)
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LopHocPhanMapper {
    
    /**
     * Convert Entity to Response DTO
     */
    @Mapping(target = "tenMonHoc", source = "monHoc.tenMH")
    @Mapping(target = "tenGiangVien", source = "giangVien.hoTen")
    LopHocPhanResponse toResponse(LopHocPhan lopHocPhan);
    
    /**
     * Convert Create Request to Entity
     */
    @Mapping(target = "monHoc", ignore = true) // Handle relationship separately
    @Mapping(target = "giangVien", ignore = true) // Handle relationship separately
    @Mapping(target = "siSoHienTai", constant = "0") // Default value
    LopHocPhan toEntity(LopHocPhanCreateRequest request);
    
    /**
     * Update existing entity with request data
     */
    @Mapping(target = "maLop", ignore = true) // Don't update primary key
    @Mapping(target = "monHoc", ignore = true) // Handle relationships separately
    @Mapping(target = "giangVien", ignore = true)
    void updateEntityFromRequest(LopHocPhanCreateRequest request, @MappingTarget LopHocPhan lopHocPhan);
}