package com.kbe5.infra.infrastructure.manager.respository;


import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByLoginId(String managerId);

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    List<Manager> findAllByCompanyCode(String companyCode);

    List<Manager> findAllByCompany(Company company);

    Optional<Manager> findByFcmToken(String failedToken);
}
