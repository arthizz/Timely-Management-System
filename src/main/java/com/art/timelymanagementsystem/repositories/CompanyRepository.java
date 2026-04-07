package com.art.timelymanagementsystem.repositories;

import com.art.timelymanagementsystem.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
