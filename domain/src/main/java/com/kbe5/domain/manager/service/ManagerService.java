package com.kbe5.domain.manager.service;

import com.kbe5.domain.manager.dto.ManagerCommand;
import com.kbe5.domain.manager.dto.ManagerInfo;

import java.util.List;

public interface ManagerService {
    ManagerInfo getManagerInfo(Long id);
    List<ManagerInfo> getManagerList(String companyCode);
    ManagerInfo signUpManager(ManagerCommand.Register command);
    ManagerInfo updateManager(Long id, ManagerCommand.Update command);
    boolean deleteManager(Long id, ManagerCommand.Delete command);
    boolean isExistLoginId(String loginId);
    boolean isExistEmail(String email);
    void logout(Long managerId);
    void updateFcmToken(Long managerId, ManagerCommand.UpdateFcmToken command);
}
