package com.kbe5.rento.domain.drive.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.repository.DriveRepository;
import com.kbe5.rento.domain.manager.entity.Manager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DriveService {

    private final DriveRepository driveRepository;

    // 운행 등록
    public void driveAdd(Drive drive) {
        if(drive.getVehicle().getCompany() != drive.getMember().getCompany()){
            throw new DomainException(ErrorType.USER_VEHICLE_COMPANY_MISMATCH);}

        driveRepository.save(drive);
    }

    // 운행 시작
    // todo: 시동 on 이벤트가 걸린다면 이 메서드 호출 되게 해야함 5.28
    public void driveStart(Long driveId){
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND));

        drive.driveStart();
    }

    // 운행 종료
    // todo: 시동 off? 아니면 그냥 운행 종료가 온다면 해당 메서드 호출 5.28
    public void driveEnd(Long driveId){
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND));

        drive.driveEnd();
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
        return driveRepository.findById(driveId).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND)
        );
    }
}
