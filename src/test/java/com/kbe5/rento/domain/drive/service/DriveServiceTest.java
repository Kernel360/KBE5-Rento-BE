package com.kbe5.rento.domain.drive.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.entity.DriveType;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.member.repository.MemberRepository;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import static com.kbe5.rento.domain.vehicle.entity.FuelType.DIESEL;
import static com.kbe5.rento.domain.vehicle.entity.VehicleType.SEDAN;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DriveServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private DriveService driveService;

    private Member member;
    private Vehicle vehicle;
    private Drive drive;

    @BeforeEach
    void setUp() {
        Company companyA = Company.builder()
                .name("testCompanyA")
                .bizNumber(1234567890)
                .build();
        Company companyB = Company.builder()
                .name("testCompanyB")
                .bizNumber(555555555)
                .build();

        member = Member.builder()
//                .company(companyA)
                .name("운행회원")
                .build();

        vehicle = Vehicle.builder()
                .company(companyB)
                .vehicleType(SEDAN)
                .fuelType(DIESEL)
                .vehicleNumber("12가3456")
                .build();

        ReflectionTestUtils.setField(member, "id", 1L);
        ReflectionTestUtils.setField(vehicle, "id", 2L);

        drive = Drive.builder()
                .member(member)
                .vehicle(vehicle)
                .dirveType(DriveType.BUSINESS)
                .startLocation("부산역")
                .endLocation("강남역")
                .build();
    }

    @Test
    void 운행사용자와_운행차량이_같은_업체가_아니면_실패합니다() {
        assertThatThrownBy(() -> driveService.driveAdd(drive))
                .isInstanceOf(DomainException.class);
    }
}
