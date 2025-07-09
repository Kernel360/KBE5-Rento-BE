package com.kbe5.pub.service;


import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.infra.infrastructure.drive.repository.DriveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("commonDriveService")
@Transactional
@RequiredArgsConstructor
public class DriveService {

    private final DriveRepository driveRepository;

    public void driveStart(Long driveId){
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND));

        drive.driveStart();
    }

    // 운행 종료
    public void driveEnd(Long driveId, Long distance){
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND));

        drive.driveEnd();

        drive.getVehicle().cancel();
        drive.getVehicle().addDistance(distance);
        drive.addDistance(distance);
    }
}