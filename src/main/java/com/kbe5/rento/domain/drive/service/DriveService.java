package com.kbe5.rento.domain.drive.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.repository.DriveRepository;
import com.kbe5.rento.domain.event.repository.EventRepository;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DriveService {

    private final DriveRepository driveRepository;
    private final EventRepository eventRepository;
    private final VehicleRepository vehicleRepository;

    // 운행 등록
    public void driveAdd(Drive drive) {
        if(drive.getVehicle().getCompany() != drive.getMember().getCompany()){
            throw new DomainException(ErrorType.USER_VEHICLE_COMPANY_MISMATCH);}

        var vehicle = vehicleRepository.findById(drive.getVehicle().getId()).orElseThrow(()
        -> new DomainException(ErrorType.VEHICLE_NOT_FOUND));
        driveRepository.save(drive);

        vehicle.reservation(vehicle.getId());

        drive.addMdn(vehicle.getMileage().getMdn());
    }

    // 운행 시작
    // todo: 시동 on 이벤트가 걸린다면 이 메서드 호출 되게 해야함 5.28
    public void driveStart(Long driveId){
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND));

        drive.driveStart();
    }

    // 운행 종료
    // todo: 시동 off제일 마지막거 아니면 그냥 운행 종료가 온다면 해당 메서드 호출 5.28
    public void driveEnd(Long driveId){
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND));

        drive.driveEnd();

        // mdn 및 예약 시간/예약 종료 시간으로 찾아오기
        // todo: 추후 운행id를 이용하게 수정 필요함 6.07
        Long distance = eventRepository.findLastOffDistance(drive.getVehicle().getMileage().getMdn(),
                drive.getStartDate(), drive.getEndDate()).orElseThrow(() ->
                new DomainException(ErrorType.DRIVE_NOT_DISTANCE));

        drive.addDistance(distance);
        drive.getVehicle().addDistance(distance);
    }

    // 운행 취소
    public void driveCancel(Long driveId){
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND));

        drive.delete();
    }

    // 운행 목록 조회
    // todo: 운행 목록은 업체 기준으로 찾아준다 -> 업체 코드
    public List<Drive> getDriveList(Manager manager){
        return driveRepository.findByMember_Company(manager.getCompany());
    }

    // 운행 상세
    public Drive getDriveDetail(Long driveId){
        driveEnd(driveId);

        return driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND)
        );
    }

    // 이벤트를 위한 해당 차량 찾기
    public Long findDriveForEvent(Long mdn, LocalDateTime onTime){
        return driveRepository.findMdnAndStartDate(mdn, onTime);
    }
}