package com.art.timelymanagementsystem.mappers;

import com.art.timelymanagementsystem.dto.CompanyRoleDto;
import com.art.timelymanagementsystem.entities.CompanyRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyRoleMapper {

    CompanyRoleDto toDto(CompanyRole companyRole);

}
