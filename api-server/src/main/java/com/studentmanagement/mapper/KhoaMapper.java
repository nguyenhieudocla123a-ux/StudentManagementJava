package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.KhoaCreateRequest;
import com.studentmanagement.dto.response.KhoaResponse;
import com.studentmanagement.entity.Khoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for Khoa (Department/Faculty)
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface KhoaMapper {
    
    /**
     * Convert Entity to Response DTO
     */
    KhoaResponse toResponse(Khoa khoa);
    
    /**
     * Convert Create Request to Entity
     */
    Khoa toEntity(KhoaCreateRequest request);
    
    /**
     * Update existing entity with request data
     */
    @Mapping(target = "maKhoa", ignore = true) // Don't update primary key
    void updateEntityFromRequest(KhoaCreateRequest request, @MappingTarget Khoa khoa);
}