package com.kbe5.rento.domain.manager.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.common.jwt.entity.JwtRefresh;
import com.kbe5.rento.common.jwt.respository.JwtRefreshRepository;
import com.kbe5.rento.domain.company.dto.response.CompanyResponse;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import com.kbe5.rento.domain.company.service.CompanyService;
import com.kbe5.rento.domain.manager.dto.request.ManagerDeleteRequest;
import com.kbe5.rento.domain.manager.dto.request.ManagerSignUpRequest;
import com.kbe5.rento.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.rento.domain.manager.dto.response.ManagerDeleteResponse;
import com.kbe5.rento.domain.manager.dto.response.ManagerResponse;
import com.kbe5.rento.domain.manager.dto.response.ManagerSignUpResponse;
import com.kbe5.rento.domain.manager.dto.response.ManagerUpdateResponse;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.enums.ManagerRole;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder encoder;
    private final JwtRefreshRepository jwtRefreshRepository;

    public Manager signUp(Manager manager) {
        Company company = companyRepository.findByCompanyCode(manager.getCompanyCode())
                        .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));

        manager.encodePassword(encoder);
        manager.assignCompany(company);
        manager.assignRole(ManagerRole.ROLE_MANAGER);

        return managerRepository.save(manager);
    }

    public Manager findByLoginId(String loginId) {
        return managerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Manager getManagerDetail(Long id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<Manager> getManagerList(String companyCode) {
        List<Manager> ManagerList = managerRepository.findAllByCompanyCode(companyCode);

        if (ManagerList.isEmpty()) {
            throw new DomainException(ErrorType.MANAGER_NOT_FOUND);
        }

        return ManagerList;
    }

    @Transactional
    public void logout(Long managerId){
        JwtRefresh jwtRefresh = jwtRefreshRepository.findByManagerId(managerId)
                .orElseThrow(() -> new DomainException(ErrorType.REFRESH_TOKEN_NOT_FOUND));

        jwtRefreshRepository.delete(jwtRefresh);
    }

    public Manager update(Long id, ManagerUpdateRequest request) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));

        manager.toUpdate(request);

        return manager;
    }

    public boolean delete(Long id, ManagerDeleteRequest request) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));

        if (!manager.getPassword().equals(request.password())) {
            return false;
        }

        managerRepository.delete(manager);

        return true;
    }

    public boolean isExistsLoginId(String loginId) {
        return managerRepository.existsByLoginId(loginId);
    }

    public boolean isExistsEmail(String email) {
        return managerRepository.existsByEmail(email);
    }
}
