package com.kbe5.domain.manager.service;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.manager.entity.Manager;

import java.util.List;
import java.util.Optional;

public interface ManagerReader {
    Manager getManagerById(Long id);
    Manager getManagerByLoginId(String loginId);
    List<Manager> getManagersByCompanyCode(String companyCode);
    Boolean isExistLoginId(String loginId);
    Boolean isExistEmail(String email);
    List<Manager> findAllByCompany(Company company);
    Optional<Manager> findByFcmToken(String failedToken);
}
