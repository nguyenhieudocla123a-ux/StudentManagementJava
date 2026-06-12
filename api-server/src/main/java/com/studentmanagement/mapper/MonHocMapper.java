package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.MonHocCreateRequest;
import com.studentmanagement.dto.response.MonHocResponse;
import com.studentmanagement.entity.MonHoc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for MonHoc (Subject/Course)
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MonHocMapper {
    
    /**
     * Convert Entity to Response DTO
     */
    MonHocResponse toResponse(MonHoc monHoc);
    
    /**
     * Convert Create Request to Entity
     */
    MonHoc toEntity(MonHocCreateRequest request);
    
    /**
     * Update existing entity with request data
     */
    @Mapping(target = "maMH", ignore = true) // Don't update primary key
    void updateEntityFromRequest(MonHocCreateRequest request, @MappingTarget MonHoc monHoc);
}