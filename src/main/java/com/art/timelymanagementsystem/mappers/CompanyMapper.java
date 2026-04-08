package com.art.timelymanagementsystem.mappers;

import com.art.timelymanagementsystem.dto.CompanyDto;
import com.art.timelymanagementsystem.entities.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto toDto(Company company);

}
