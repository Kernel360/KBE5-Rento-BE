package com.kbe5.domain.manager.service;

import com.kbe5.domain.manager.entity.Manager;

public interface ManagerStore {
    Manager store(Manager manager);
    void delete(Manager manager);
    void logout(Manager manage);
}
