package com.kbe5.api.domain.drive.service;


import com.kbe5.common.exception.DomainException;
import com.kbe5.common.exception.ErrorType;
import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.entity.DriveStatus;
import com.kbe5.domain.drive.repository.DriveRepository;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.vehicle.entity.Vehicle;
import com.kbe5.domain.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("apiDriveService")
@Transactional
@RequiredArgsConstructor
public class DriveService {

    private final DriveRepository driveRepository;
    private final VehicleRepository vehicleRepository;

    // 운행 등록
    public void driveAdd(Drive drive) {
        if(drive.getVehicle().getCompany() != drive.getMember().getCompany()){
            throw new DomainException(ErrorType.USER_VEHICLE_COMPANY_MISMATCH);}

        Vehicle vehicle = vehicleRepository.findById(drive.getVehicle().getId()).orElseThrow(()
        -> new DomainException(ErrorType.VEHICLE_NOT_FOUND));

        if(drive.getEndDate().isBefore(drive.getStartDate())
                || drive.getEndDate().isEqual(drive.getStartDate())){
            throw new DomainException(ErrorType.DRIVE_OVERLAP);
        }

        if(driveRepository.existsByVehicleOverlap(vehicle.getId(), drive.getStartDate(), drive.getEndDate())){
            throw new DomainException(ErrorType.DRIVE_IS_FOUND);
        }

        LocalDateTime minimumTime = LocalDateTime.now().plusMinutes(5); //5분의 여유 타임 주기
        if(drive.getStartDate().isBefore(minimumTime)){
            throw new DomainException(ErrorType.PAST_TIME);
        }

        driveRepository.save(drive);
        drive.addMdn(vehicle.getMileage().getMdn());
    }

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
        drive.addDistance(distance);
        drive.getVehicle().addDistance(distance);
    }

    // 운행 취소
    public void driveCancel(Long driveId){
        driveRepository.deleteById(driveId);
    }

    // 운행 목록 조회
    public Page<Drive> getDriveList(Manager manager, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable){
        return driveRepository.findByCompanyAndOptionalDateRange(manager.getCompany(), startDate, endDate, pageable);
    }

    // 운행 상세
    public Drive getDriveDetail(Long driveId){
        return driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND)
        );
    }

    // 이벤트를 위한 해당 차량 찾기
    public Long findDriveForEvent(Long mdn, LocalDateTime onTime){

        Long driveId = driveRepository.findIdByMdnAndStartDateBetween(mdn, onTime);

        if(driveId == null){
            throw new DomainException(ErrorType.DRIVE_NOT_FOUND);
        }
        return driveId;
    }

    public List<Drive> findStream(Manager manager, String vehicleNumber){
        return driveRepository.findByCompanyAndStatusAndVehicleNumber(manager.getCompany(),
                vehicleNumber, DriveStatus.DRIVING);
    }

}
