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
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final CompanyService companyService;

    public ManagerSignUpResponse signUp(ManagerSignUpRequest request) {

        Company company = companyService.findByCompanyCode(request.companyCode());

        Manager manager = Manager.builder()
                .managerId(request.managerId())
                .password(request.password())
                .email(request.email())
                .name(request.name())
                .phone(request.phone())
                .companyId(company)
                .build();

        Manager newManger = managerRepository.save(manager);

        return new ManagerSignUpResponse(newManger.getId(), newManger.getManagerId(), company.getCompanyCode());
    }

    public Manager findByManagerId(String managerId) {
        return managerRepository.findByManagerId(managerId).orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));
    }

    public ManagerDetailResponse getManagerDetail(Long id) {

        Manager manager = managerRepository.findById(id).orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));

        return new ManagerDetailResponse(manager.getId(), manager.getCompanyId(), manager.getName(),
                manager.getPhone(), manager.getEmail(), manager.getManagerId());
    }

    public ManagerListResponse getManagerList() {

        List<Manager> manager = managerRepository.findAll();

        return new ManagerListResponse(manager);
    }

    public ManagerUpdateResponse update(ManagerUpdateRequest request) {

        Manager manager = managerRepository.findById(request.id()).orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));

        Manager updatedManager = manager.toUpdate(request);
        managerRepository.save(updatedManager);

        return new ManagerUpdateResponse(updatedManager.getId(), updatedManager.getCompanyId(), updatedManager.getName(),
                updatedManager.getPhone(), updatedManager.getEmail(), updatedManager.getManagerId());
    }

    public ManagerDeleteResponse delete(ManagerDeleteRequest request) {

        Manager manager = managerRepository.findById(request.id()).orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));

        if (!manager.getPassword().equals(request.password())) {
            return new ManagerDeleteResponse(false);
        }

        managerRepository.delete(manager);

        if (!managerRepository.existsByCompanyId(manager.getCompanyId())) {
            companyService.delete(manager.getCompanyId());
        }

        return new ManagerDeleteResponse(true);
    }
}
