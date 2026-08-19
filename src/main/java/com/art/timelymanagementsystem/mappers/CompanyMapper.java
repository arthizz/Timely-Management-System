package com.art.timelymanagementsystem.mappers;

import com.art.timelymanagementsystem.dto.CompanyDto;
import com.art.timelymanagementsystem.entities.Company;
import com.art.timelymanagementsystem.request.CompanyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto toDto(Company company);

    Company toEntity(CompanyRequest companyRequest);

    void updateCompany(CompanyRequest companyRequest, @MappingTarget Company company);

}
