package com.kbe5.infra.infrastructure.drive;

import com.kbe5.domain.drive.dto.DriveAddCommand;
import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.service.DriveStore;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.infra.infrastructure.drive.repository.DriveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class DriveStoreImpl implements DriveStore {

    private final DriveRepository driveRepository;

    @Override
    public Drive addDrive(DriveAddCommand command) {
        if(command.getVehicle().getCompany() != command.getMember().getCompany()){
            throw new DomainException(ErrorType.USER_VEHICLE_COMPANY_MISMATCH);}


        if(command.getEndDateTime().isBefore(command.getStartDateTime())
                || command.getEndDateTime().isEqual(command.getStartDateTime())){
            throw new DomainException(ErrorType.DRIVE_OVERLAP);
        }

        LocalDateTime minimumTime = LocalDateTime.now().plusMinutes(5);
        if(command.getStartDateTime().isBefore(minimumTime)){
            throw new DomainException(ErrorType.PAST_TIME);
        }

        Drive drive = command.toEntity();
        return driveRepository.save(drive);
    }

    @Override
    public void deleteDrive(Long driveId) {
        driveRepository.deleteById(driveId);
    }

}
