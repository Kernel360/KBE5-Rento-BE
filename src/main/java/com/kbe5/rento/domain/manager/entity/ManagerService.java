package com.kbe5.rento.domain.manager.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;


    public void saveManager(String CompanyCode){

        Manager manager = Manager.builder()
                .componyCode(CompanyCode)
                .build();

        managerRepository.save(manager);
    }
}
