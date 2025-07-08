package com.kbe5.domain.manager.service;

import com.kbe5.domain.manager.dto.ManagerCommand;
import com.kbe5.domain.manager.dto.ManagerInfo;
import com.kbe5.domain.manager.entity.Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerStore managerStore;
    private final ManagerReader managerReader;

    @Override
    public ManagerInfo signUpManager(ManagerCommand.Register command) {
        Manager initManager = command.toEntity();
        Manager manager = managerStore.store(initManager);

        return ManagerInfo.fromEntity(manager);
    }

    @Override
    public ManagerInfo getManagerInfo(Long id) {
        Manager manager = managerReader.getManagerById(id);

        return ManagerInfo.fromEntity(manager);
    }

    @Override
    public List<ManagerInfo> getManagerList(String companyCode) {
        List<Manager> managerList = managerReader.getManagersByCompanyCode(companyCode);

        return managerList.stream().map(ManagerInfo::fromEntity).toList();
    }

    @Override
    public ManagerInfo updateManager(Long id, ManagerCommand.Update command) {
        Manager manager = managerReader.getManagerById(id);
        manager.toUpdate(command.getName(), command.getPhone(), command.getEmail());

        return ManagerInfo.fromEntity(manager);
    }

    @Override
    public boolean deleteManager(Long id, ManagerCommand.Delete command) {
        Manager manager = managerReader.getManagerById(id);

        if(!manager.getPassword().equals(command.getPassword())) {
            return false;
        }

        managerStore.delete(manager);

        return true;
    }

    @Override
    public boolean isExistLoginId(String loginId) {
        return managerReader.isExistLoginId(loginId);
    }

    @Override
    public boolean isExistEmail(String email) {
        return managerReader.isExistEmail(email);
    }

    @Override
    public void logout(Long managerId) {
        Manager manager = managerReader.getManagerById(managerId);
        managerStore.logout(manager);
    }

    @Override
    public void updateFcmToken(Long managerId, ManagerCommand.UpdateFcmToken command) {
        Manager manager = managerReader.getManagerById(managerId);

        manager.assignFcmToken(command.getFcmToken());
    }
}
