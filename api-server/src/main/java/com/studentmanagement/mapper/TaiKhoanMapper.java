package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.TaiKhoanCreateRequest;
import com.studentmanagement.dto.response.TaiKhoanResponse;
import com.studentmanagement.entity.TaiKhoan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for TaiKhoan (Account)
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TaiKhoanMapper {
    
    /**
     * Convert Entity to Response DTO
     */
    TaiKhoanResponse toResponse(TaiKhoan taiKhoan);
    
    /**
     * Convert Create Request to Entity
     */
    @Mapping(target = "onlineStatus", constant = "Offline") // Default value
    TaiKhoan toEntity(TaiKhoanCreateRequest request);
    
    /**
     * Update existing entity with request data
     */
    @Mapping(target = "tenDangNhap", ignore = true) // Don't update primary key
    @Mapping(target = "onlineStatus", ignore = true) // Don't change online status
    void updateEntityFromRequest(TaiKhoanCreateRequest request, @MappingTarget TaiKhoan taiKhoan);
}