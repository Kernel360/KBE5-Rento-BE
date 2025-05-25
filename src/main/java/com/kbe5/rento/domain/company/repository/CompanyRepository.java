package com.kbe5.rento.domain.company.repository;

import com.kbe5.rento.domain.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByBizNumber(int bizNumber);
    Optional<Company> findByCompanyCode(String code);

    Boolean existsByBizNumber(int bizNumber);
}
