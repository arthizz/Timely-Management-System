package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.CompanyDto;
import com.art.timelymanagementsystem.dto.CompanyRoleDto;
import com.art.timelymanagementsystem.entities.Company;
import com.art.timelymanagementsystem.entities.CompanyRole;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.mappers.CompanyMapper;
import com.art.timelymanagementsystem.mappers.CompanyRoleMapper;
import com.art.timelymanagementsystem.repositories.CompanyRepository;
import com.art.timelymanagementsystem.repositories.CompanyRoleRepository;
import com.art.timelymanagementsystem.request.CompanyRoleRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyRoleService {

    private final CompanyRoleRepository companyRoleRepository;
    private final CompanyRoleMapper companyRoleMapper;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public ResponseEntity<CompanyRoleDto> createNewRoleService(CompanyRoleRequest companyRoleRequest){

        Company company = companyRepository.findById(companyRoleRequest.getCompanyId()).orElseThrow(() -> new ResourceNotFoundException("Company Not Found"));

        CompanyRole companyRole = new CompanyRole();

        companyRole.setRoleName(companyRoleRequest.getRoleName());
        companyRole.setCompany(company);

        CompanyRole newCompanyRole = companyRoleRepository.save(companyRole);

        return ResponseEntity.ok(companyRoleMapper.toDto(newCompanyRole));

    }

}
