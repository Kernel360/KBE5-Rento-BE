package com.kbe5.rento.domain.manager.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.entity.Company;
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
    private final PasswordEncoder encoder;

    public Manager signUp(Manager manager) {
        manager.encodePassword(encoder);
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
        Optional<List<Manager>> optionalManagerList = managerRepository.findAllByCompanyCode(companyCode);

        if (optionalManagerList.map(List::isEmpty).orElse(true)) {
            throw new DomainException(ErrorType.MANAGER_NOT_FOUND);
        }

        return optionalManagerList.get();
    }

    public Manager update(ManagerUpdateRequest request) {
        Manager manager = managerRepository.findById(request.id())
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));

        manager.toUpdate(request);

        return manager;
    }

    public boolean delete(ManagerDeleteRequest request) {
        Manager manager = managerRepository.findById(request.id())
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
