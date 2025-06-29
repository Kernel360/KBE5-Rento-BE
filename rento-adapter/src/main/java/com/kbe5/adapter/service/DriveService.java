package com.kbe5.adapter.service;



import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.repository.DriveRepository;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("commonDriveService")
@Transactional
@RequiredArgsConstructor
public class DriveService {

    private final DriveRepository driveRepository;
    private final VehicleRepository vehicleRepository;

    // 운행 등록
    public void driveAdd(Drive drive) {
        if(drive.getVehicle().getCompany() != drive.getMember().getCompany()){
            throw new DomainException(ErrorType.USER_VEHICLE_COMPANY_MISMATCH);}

        var vehicle = vehicleRepository.findById(drive.getVehicle().getId()).orElseThrow(()
        -> new DomainException(ErrorType.VEHICLE_NOT_FOUND));
        driveRepository.save(drive);

        vehicle.reservation();

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
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND));

        drive.getVehicle().cancel();
        drive.delete();
    }

    // 운행 목록 조회
    // todo: 운행 목록은 업체 기준으로 찾아준다 -> 업체 코드
    public List<Drive> getDriveList(Manager manager){
        return driveRepository.findByMember_Company(manager.getCompany());
    }

    // 운행 상세
    public Drive getDriveDetail(Long driveId){
        return driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND)
        );
    }

    // 이벤트를 위한 해당 차량 찾기
    public Long findDriveForEvent(Long mdn, LocalDateTime onTime){

        Long driveid = driveRepository.findIdByMdnAndStartDateBetween(mdn, onTime);

        if(driveid == null){
            throw new DomainException(ErrorType.DRIVE_NOT_FOUND);
        }
        return driveid;
    }
}