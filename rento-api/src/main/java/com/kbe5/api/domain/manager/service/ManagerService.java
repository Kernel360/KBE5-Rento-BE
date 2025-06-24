package com.kbe5.api.domain.manager.service;

import com.kbe5.api.domain.manager.dto.request.ManagerDeleteRequest;
import com.kbe5.api.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.api.domain.manager.vo.ManagerUpdateVO;
import com.kbe5.common.exception.DomainException;
import com.kbe5.common.exception.ErrorType;
import com.kbe5.domain.commonservice.firebase.dto.UpdateFcmTokenRequest;
import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.company.repository.CompanyRepository;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.manager.enums.ManagerRole;
import com.kbe5.domain.manager.respository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
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
    private final RedisTemplate<String, String> redisTemplate;

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

    public void logout(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));

        String uuid = manager.getUuid();

        if (uuid != null && redisTemplate.hasKey(uuid)) {
            Boolean deleted = redisTemplate.delete(uuid);
            if (!deleted) {
                throw new DomainException(ErrorType.FAILED_DELETE_FROM_REDIS);
            }
        } else {
            throw new DomainException(ErrorType.FAILED_DELETE_FROM_REDIS);
        }
    }

    public Manager update(Long id, ManagerUpdateRequest request) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));

        ManagerUpdateVO vo = new ManagerUpdateVO(request);
        vo.update(manager);

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
