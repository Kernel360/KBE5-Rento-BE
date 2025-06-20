package com.kbe5.rento.domain.manager.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import com.kbe5.rento.domain.firebase.dto.UpdateFcmTokenRequest;
import com.kbe5.rento.domain.manager.dto.request.ManagerDeleteRequest;
import com.kbe5.rento.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.enums.ManagerRole;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder encoder;

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
//        JwtRefresh jwtRefresh = jwtRefreshRepository.findByManagerId(managerId)
//                .orElseThrow(() -> new DomainException(ErrorType.REFRESH_TOKEN_NOT_FOUND));
//
//        Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));
//
//        manager.assignFcmToken(null);
//        managerRepository.save(manager);
//
//        jwtRefreshRepository.delete(jwtRefresh);
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

        if (!encoder.matches(request.password(), manager.getPassword())) {
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

    public void updateFcmToken(Long managerId, UpdateFcmTokenRequest tokenRequest) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));

        manager.assignFcmToken(tokenRequest.token());
    }
}
