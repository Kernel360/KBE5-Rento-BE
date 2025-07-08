package com.kbe5.domain.drive.service;

import java.time.LocalDateTime;

public interface DriveService {

    Long findDriveForEvent(Long mdn, LocalDateTime now);
}
