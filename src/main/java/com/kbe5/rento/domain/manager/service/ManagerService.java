package com.kbe5.rento.domain.manager.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.service.CompanyService;
import com.kbe5.rento.domain.manager.dto.request.ManagerDeleteRequest;
import com.kbe5.rento.domain.manager.dto.request.ManagerSignUpRequest;
import com.kbe5.rento.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.rento.domain.manager.dto.response.*;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.enums.ManagerRole;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final CompanyService companyService;
    private final BCryptPasswordEncoder encoder;

    public ManagerSignUpResponse signUp(ManagerSignUpRequest request) {

        Company company = companyService.findByCompanyCode(request.companyCode());

        Manager manager = Manager.builder()
                .loginId(request.loginId())
                .password(encoder.encode(request.password()))
                .email(request.email())
                .name(request.name())
                .phone(request.phone())
                .companyId(company)
                .companyCode(request.companyCode())
                .role(ManagerRole.ROLE_MANAGER)
                .build();

        manager = managerRepository.save(manager);

        return ManagerSignUpResponse.from(manager, company.getCompanyCode());
    }

    public Manager findByLoginId(String loginId) {
        return managerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public ManagerResponse getManagerDetail(Long id) {

        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));

        return ManagerResponse.from(manager);
    }

    @Transactional(readOnly = true)
    public List<ManagerResponse> getManagerList(String companyCode) {

        Optional<List<Manager>> optionalManagerList = managerRepository.findAllByCompanyCode(companyCode);

        if (optionalManagerList.map(List::isEmpty).orElse(true)) {
            throw new DomainException(ErrorType.MANAGER_NOT_FOUND);
        }

        return ManagerResponse.from(optionalManagerList.get());
    }

    public ManagerUpdateResponse update(ManagerUpdateRequest request) {

        Manager manager = managerRepository.findById(request.id())
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));

        manager.toUpdate(request);

        return ManagerUpdateResponse.from(manager);
    }

    public ManagerDeleteResponse delete(ManagerDeleteRequest request) {

        Manager manager = managerRepository.findById(request.id())
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));

        if (!manager.getPassword().equals(request.password())) {
            return new ManagerDeleteResponse(false);
        }

        managerRepository.delete(manager);

        return new ManagerDeleteResponse(true);
    }

    public boolean isExistsLoginId(String loginId) {
        return managerRepository.existsByLoginId(loginId);
    }

    public boolean isExistsEmail(String email) {
        return managerRepository.existsByEmail(email);
    }
}
