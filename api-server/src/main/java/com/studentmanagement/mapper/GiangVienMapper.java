package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.GiangVienCreateRequest;
import com.studentmanagement.dto.response.GiangVienResponse;
import com.studentmanagement.entity.GiangVien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for GiangVien (Teacher/Lecturer)
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GiangVienMapper {
    
    /**
     * Convert Entity to Response DTO
     * Simple mapping - entity fields match response fields
     */
    GiangVienResponse toResponse(GiangVien giangVien);
    
    /**
     * Convert Create Request to Entity
     */
    GiangVien toEntity(GiangVienCreateRequest request);
    
    /**
     * Update existing entity with request data
     */
    @Mapping(target = "maGV", ignore = true) // Don't update primary key
    void updateEntityFromRequest(GiangVienCreateRequest request, @MappingTarget GiangVien giangVien);
}