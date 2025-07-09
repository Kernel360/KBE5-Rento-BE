package com.kbe5.domain.drive.service;

import com.kbe5.domain.drive.dto.DriveAddCommand;
import com.kbe5.domain.drive.entity.Drive;

public interface DriveStore {

    Drive addDrive(DriveAddCommand command);
    void deleteDrive(Long driveId);
}
