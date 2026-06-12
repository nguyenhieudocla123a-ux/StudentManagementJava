package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.SinhVienCreateRequest;
import com.studentmanagement.dto.response.SinhVienResponse;
import com.studentmanagement.entity.SinhVien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for SinhVien (Student)
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SinhVienMapper {
    
    /**
     * Convert Entity to Response DTO
     * Simple mapping - entity fields match response fields
     */
    SinhVienResponse toResponse(SinhVien sinhVien);
    
    /**
     * Convert Create Request to Entity
     */
    SinhVien toEntity(SinhVienCreateRequest request);
    
    /**
     * Update existing entity with request data
     */
    @Mapping(target = "maSV", ignore = true) // Don't update primary key
    void updateEntityFromRequest(SinhVienCreateRequest request, @MappingTarget SinhVien sinhVien);
}