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
     * Simple mapping - entity fields already match response fields
     */
    LopHocPhanResponse toResponse(LopHocPhan lopHocPhan);
    
    /**
     * Convert Create Request to Entity
     */
    @Mapping(target = "siSoHienTai", constant = "0") // Default value
    LopHocPhan toEntity(LopHocPhanCreateRequest request);
    
    /**
     * Update existing entity with request data
     */
    @Mapping(target = "maLop", ignore = true) // Don't update primary key
    void updateEntityFromRequest(LopHocPhanCreateRequest request, @MappingTarget LopHocPhan lopHocPhan);
}