package com.kbe5.infra.infrastructure.manager;

import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.infra.infrastructure.manager.respository.ManagerRepository;
import com.kbe5.domain.manager.service.ManagerReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManagerReaderImpl implements ManagerReader {

    private final ManagerRepository managerRepository;

    @Override
    public Manager getManagerById(Long id) {
        return managerRepository.findById(id).orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));
    }

    @Override
    public Manager getManagerByLoginId(String loginId) {
        return managerRepository.findByLoginId(loginId).orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));
    }

    @Override
    public List<Manager> getManagersByCompanyCode(String companyCode) {
        return managerRepository.findAllByCompanyCode(companyCode);
    }

    @Override
    public Boolean isExistLoginId(String loginId) {
        return managerRepository.existsByLoginId(loginId);
    }

    @Override
    public Boolean isExistEmail(String email) {
        return managerRepository.existsByEmail(email);
    }
}
