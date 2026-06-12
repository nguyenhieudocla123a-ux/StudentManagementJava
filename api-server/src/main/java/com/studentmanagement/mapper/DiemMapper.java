package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.DiemCreateRequest;
import com.studentmanagement.dto.response.DiemResponse;
import com.studentmanagement.entity.Diem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for Diem (Grade/Score)
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DiemMapper {
    
    /**
     * Convert Entity to Response DTO
     */
    @Mapping(target = "tenSinhVien", source = "sinhVien.hoTen")
    @Mapping(target = "tenLopHocPhan", source = "lopHocPhan.tenLopHP")
    @Mapping(target = "tenMonHoc", source = "lopHocPhan.monHoc.tenMH")
    @Mapping(target = "maMonHoc", source = "lopHocPhan.monHoc.maMH")
    DiemResponse toResponse(Diem diem);
    
    /**
     * Convert Create Request to Entity
     */
    @Mapping(target = "id", ignore = true) // Auto-generated
    @Mapping(target = "sinhVien", ignore = true) // Handle relationship separately
    @Mapping(target = "lopHocPhan", ignore = true) // Handle relationship separately
    Diem toEntity(DiemCreateRequest request);
    
    /**
     * Update existing entity with request data
     */
    @Mapping(target = "id", ignore = true) // Don't update primary key
    @Mapping(target = "maSV", ignore = true) // Don't update foreign keys
    @Mapping(target = "maLop", ignore = true)
    @Mapping(target = "sinhVien", ignore = true) // Handle relationships separately
    @Mapping(target = "lopHocPhan", ignore = true)
    void updateEntityFromRequest(DiemCreateRequest request, @MappingTarget Diem diem);
}