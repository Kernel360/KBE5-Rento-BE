package com.kbe5.rento.domain.drive.service;

import com.kbe5.rento.CompanyTestFixtures;
import com.kbe5.rento.DriveTestFixtures;
import com.kbe5.rento.MemberTestFixtures;
import com.kbe5.rento.VehicleTestFixtures;
import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.drive.entity.Drive;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DriveServiceTest {

    @InjectMocks
    private DriveService driveService;

    private Drive drive;

    @BeforeEach
    void setUp() {
        Company companyB = CompanyTestFixtures.companyB();

        Member member = MemberTestFixtures.memberA(companyB);
        Vehicle vehicle = VehicleTestFixtures.vehicleA();

        ReflectionTestUtils.setField(member, "id", 1L);
        ReflectionTestUtils.setField(vehicle, "id", 2L);

        drive = DriveTestFixtures.driveA(companyB);
    }

    @Test
    void 운행사용자와_운행차량이_같은_업체가_아니면_실패합니다() {
        assertThatThrownBy(() -> driveService.driveAdd(drive))
                .isInstanceOf(DomainException.class);
    }
}
