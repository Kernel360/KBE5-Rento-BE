package com.kbe5.rento.domain.drive.service;

import com.kbe5.rento.common.DomainException;
import com.kbe5.rento.common.ErrorType;
import com.kbe5.rento.domain.drive.dto.DriveAddRequest;
import com.kbe5.rento.domain.drive.dto.DriveDetailResponse;
import com.kbe5.rento.domain.drive.dto.DriveResponse;
import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.repository.DriveRepository;
import com.kbe5.rento.domain.member.repository.MemberRepository;
import com.kbe5.rento.domain.vehicle.service.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DriveService {

    private final DriveRepository driveRepository;
    private final VehicleService vehicleService;
    private final MemberRepository memberRepository;

    // 운행 등록
    public void driveAdd(DriveAddRequest request){
        if(!Objects.equals(request.member().getComponyCode(), request.vehicle().getCompanyCode())){
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
    public List<DriveResponse> getDriveList(String companyCode){
        List<Drive> driveList = driveRepository.findByCompanyCode(companyCode);

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
