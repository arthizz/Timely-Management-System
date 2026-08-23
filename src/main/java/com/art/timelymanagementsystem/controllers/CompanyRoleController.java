package com.art.timelymanagementsystem.controllers;

import com.art.timelymanagementsystem.dto.CompanyRoleDto;
import com.art.timelymanagementsystem.entities.CompanyRole;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.mappers.CompanyRoleMapper;
import com.art.timelymanagementsystem.repositories.CompanyRoleRepository;
import com.art.timelymanagementsystem.request.CompanyRoleRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/company-role")
public class CompanyRoleController {

    private final CompanyRoleRepository companyRoleRepository;
    private final CompanyRoleMapper companyRoleMapper;

    @GetMapping
    public ResponseEntity<List<CompanyRoleDto>> getCompanyRoles(){

        List<CompanyRoleDto> companyRole = companyRoleRepository.findAll().stream().map(companyRoleMapper::toDto).toList();

        return ResponseEntity.ok(companyRole);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyRoleDto> getSingleCompanyRole(@PathVariable Long id){

        CompanyRoleDto companyRole = companyRoleRepository.findById(id).map(companyRoleMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Company role Not Found"));

        return ResponseEntity.ok(companyRole);

    }

//    @PostMapping
//    public ResponseEntity<CompanyRoleDto> createCompanyRole(@Valid @RequestBody CompanyRoleRequest companyRoleRequest){
//
//
//
//    }

}
