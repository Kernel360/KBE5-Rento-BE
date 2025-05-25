package com.kbe5.rento.domain.drive.service;

import com.kbe5.rento.common.DomainException;
import com.kbe5.rento.common.ErrorType;
import com.kbe5.rento.domain.drive.dto.DriveAddRequest;
import com.kbe5.rento.domain.drive.dto.DriveDetailResponse;
import com.kbe5.rento.domain.drive.dto.DriveResponse;
import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.repository.DriveRepository;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.entity.ManagerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriveService {

    private final DriveRepository driveRepository;

    // test
    private final ManagerRepository managerRepository;

    // 운행 등록
    public void driveAdd(DriveAddRequest request){
        // 경우 1 같은 회사가 아니면 이어지지 않습니다 -> 같은 부서면 등록되게 해야하나?
        if(request.vehicle().getCompany() != request.member().getCompany()){
            throw new DomainException(ErrorType.VALIDATION_ERROR);
        }

        Drive drive = DriveAddRequest.toEntity(request);
        driveRepository.save(drive);
    }

    // 운행 시작
    @Transactional
    public void driveStart(Long driveId){
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new IllegalArgumentException("운행이 존재하지 않습니다")
        );

        drive.driveStart();
    }

    // 운행 종료
    @Transactional
    public void driveEnd(Long driveId){
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new IllegalArgumentException("운행이 존재하지 않습니다")
        );

        drive.driveEnd();
    }

    // 운행 취소
    public void driveCancel(Long driveId){
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new IllegalArgumentException("운행이 존재하지 않습니다")
        );

        drive.delete();
    }

    // 운행 목록 조회
    // todo: 운행 목록은 업체 기준으로 찾아준다 -> 업체 코드
    public List<DriveResponse> getDriveList(Manager manager){
        // test
        Manager manager1 = managerRepository.findById(1L).orElseThrow();

        List<Drive> driveList = driveRepository.findByMember_Company(manager1.getCompany());

        return driveList.stream().map(DriveResponse::fromEntity)
                .toList();
    }

    // 운행 상세
    public DriveDetailResponse getDriveDetail(Long driveId){
        Drive drive = driveRepository.findById(driveId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 운행입니다")
        );

        return DriveDetailResponse.fromEntity(drive);
    }
}
