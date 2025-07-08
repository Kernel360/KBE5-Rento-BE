package com.kbe5.infra.infrastructure.manager;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.company.repository.CompanyRepository;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.manager.enums.ManagerRole;
import com.kbe5.infra.infrastructure.manager.respository.ManagerRepository;
import com.kbe5.domain.manager.service.ManagerStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ManagerStoreImpl implements ManagerStore {

    private final ManagerRepository managerRepository;
    private final CompanyRepository companyRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Manager store(Manager manager) {
        Company company = companyRepository.findByCompanyCode(manager.getCompanyCode())
                .orElseThrow(() -> new com.kbe5.common.exception.DomainException(com.kbe5.common.exception.ErrorType.COMPANY_NOT_FOUND));

        manager.encodePassword(passwordEncoder);
        manager.assignCompany(company);
        manager.assignRole(ManagerRole.ROLE_MANAGER);

        return managerRepository.save(manager);
    }

    @Override
    public void delete(Manager manager) {
        managerRepository.delete(manager);
    }

    @Override
    public void logout(Manager manager) {
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
}
