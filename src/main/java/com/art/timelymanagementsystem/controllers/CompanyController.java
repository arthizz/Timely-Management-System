package com.art.timelymanagementsystem.controllers;

import com.art.timelymanagementsystem.dto.CompanyDto;
import com.art.timelymanagementsystem.entities.Company;
import com.art.timelymanagementsystem.exceptions.CompanyNotFoundException;
import com.art.timelymanagementsystem.mappers.CompanyMapper;
import com.art.timelymanagementsystem.repositories.CompanyRepository;
import com.art.timelymanagementsystem.request.CompanyRequest;
import com.art.timelymanagementsystem.services.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompany(){

        List<CompanyDto> companies = companyRepository.findAll().stream().map(companyMapper::toDto).toList();

        return ResponseEntity.ok(companies);

    }


    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable(name = "id") Long id){

        CompanyDto company = companyRepository.findById(id).map(companyMapper::toDto).orElseThrow(() -> new CompanyNotFoundException("Company Not Found"));

        return ResponseEntity.ok(company);

    }

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyRequest companyRequest){

        return companyService.createCompanyService(companyRequest);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long id, @RequestBody CompanyRequest companyRequest){

        Company company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Unable to update company, company does not exists"));


        return companyService.updateCompanyService(company, companyRequest);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){

        Company company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Failed! cant delete user does not exists"));

        companyRepository.delete(company);

        return ResponseEntity.status(HttpStatus.OK).body("User Deleted Success!");

    }

}
