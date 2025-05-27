package com.kbe5.rento.domain.drive.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.rento.domain.company.service.CompanyService;
import com.kbe5.rento.domain.drive.dto.DriveAddRequest;
import com.kbe5.rento.domain.drive.entity.DriveType;
import com.kbe5.rento.domain.manager.dto.request.ManagerSignUpRequest;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import com.kbe5.rento.domain.manager.service.ManagerService;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.member.repository.MemberRepository;
import com.kbe5.rento.domain.member.service.MemberService;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.repository.VehicleRepository;
import com.kbe5.rento.domain.vehicle.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.kbe5.rento.domain.vehicle.entity.FuelType.DIESEL;
import static com.kbe5.rento.domain.vehicle.entity.VehicleType.SEDAN;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DriveServiceTest {

    @Autowired
    private DriveService driveService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private VehicleRepository vehicleRepository;


    @Test
    void 운행사용자와_운행차량이_같은_업체가_아니면안됩니다(){

        CompanyRegisterRequest companyRegisterRequest = new CompanyRegisterRequest(
                11111111,
                "test"
        );

        companyService.register(companyRegisterRequest);

        ManagerSignUpRequest managerSignUpRequest = new ManagerSignUpRequest(
                "aaaa",
                "aaaa",
                "test",
                "11111",
                "eeee",
                "C1"
        );

        managerService.signUp(managerSignUpRequest);

        VehicleAddRequest request = new VehicleAddRequest(
                "123가 1234",
                "벤츠",
                "아반떼",
                SEDAN,
                DIESEL,
                100000L,
                "1000W"
        );
        Manager manager = managerRepository.findById(1L).orElseThrow();
        vehicleService.addVehicle(manager, request);

        Member m = memberRepository.findById(1L).orElseThrow();
        Vehicle v = vehicleRepository.findById(1L).orElseThrow();

        DriveAddRequest request2 = new DriveAddRequest(
                m,
                v,
                DriveType.BUSINESS,
                "테스트 부산",
                "테스트 서울"
        );

        assertThatThrownBy(()-> driveService.driveAdd(request2))
                .isInstanceOf(DomainException.class);
    }
}