package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.CompanyDto;
import com.art.timelymanagementsystem.entities.Company;
import com.art.timelymanagementsystem.mappers.CompanyMapper;
import com.art.timelymanagementsystem.repositories.CompanyRepository;
import com.art.timelymanagementsystem.request.CompanyRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public ResponseEntity<CompanyDto> createCompanyService(CompanyRequest companyRequest){

        Company company = companyMapper.toEntity(companyRequest);

        Company newCompany = companyRepository.save(company);

        return ResponseEntity.ok(companyMapper.toDto(newCompany));

    }

    public ResponseEntity<CompanyDto> updateCompanyService(Company company, CompanyRequest companyRequest){

//        companyMapper.updateCompany(companyRequest, company);

        company.setCompanyAddress(companyRequest.getCompanyAddress());
        company.setCompanyName(companyRequest.getCompanyName());
        company.setCompanyAdminEmail(companyRequest.getCompanyAdminEmail());
        company.setIsSubscribed(companyRequest.getIsSubscribed());
        company.setIsVerified(companyRequest.getIsVerified());

        companyRepository.save(company);

        return ResponseEntity.ok(companyMapper.toDto(company));

    }

}
