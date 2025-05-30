package com.kbe5.rento.domain.vehicle.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.department.entity.Department;
import com.kbe5.rento.domain.department.repository.DepartmentRepository;
import com.kbe5.rento.domain.drive.dto.DriveAddRequest;
import com.kbe5.rento.domain.drive.entity.DriveType;
import com.kbe5.rento.domain.manager.dto.request.ManagerSignUpRequest;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.entity.VehicleInfo;
import com.kbe5.rento.domain.vehicle.entity.VehicleMilleage;
import com.kbe5.rento.domain.vehicle.repository.VehicleRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
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

import java.util.Optional;
import java.util.Set;

import static com.kbe5.rento.domain.vehicle.entity.FuelType.DIESEL;
import static com.kbe5.rento.domain.vehicle.entity.VehicleType.SEDAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class VehicleServiceTest {

    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private DepartmentRepository departmentRepository;


    @InjectMocks
    private VehicleService vehicleService;

    private Manager manager;
    private Vehicle vehicle;
    private Validator validator;
    private Department department;
    private VehicleAddRequest request;

    @BeforeEach
    void setUp() {

        validator = Validation.buildDefaultValidatorFactory().getValidator();

        Company companyA = Company.builder()
                .name("testCompanyA")
                .bizNumber(1234567890)
                .build();

        manager = Manager.builder()
                .company(companyA)
                .name("운행회원")
                .build();

        department = Department.builder()
                .company(companyA)
                .departmentName("teast부서")
                .build();

        ReflectionTestUtils.setField(manager, "id", 1L);

        request = new VehicleAddRequest(
                1L,
                "123가 1234",
                "벤츠",
                "아반떼",
                SEDAN,
                DIESEL,
                100000L,
                "1000W"
        );

        vehicle = Vehicle.builder()
                .company(manager.getCompany())
                .info(new VehicleInfo(
                        request.vehicleNumber(),
                        request.brand(),
                        request.modelName(),
                        request.vehicleType(),
                        request.fuelType()
                ))
                .mileage(new VehicleMilleage(
                        request.totalDistanceKm(),
                        request.batteryVoltage()
                ))
                .build();
    }

    @Test
    void 자동차_등록_테스트(){
        given(managerRepository.findById(1L)).willReturn(Optional.of(manager));
        given(vehicleRepository.findByInfo_VehicleNumber(vehicle.getInfo().vehicleNumber()))
                .willReturn(Optional.empty());

        given(vehicleRepository.save(any(Vehicle.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        VehicleResponse response = VehicleResponse.fromEntity(vehicleService.addVehicle(vehicle, 1L));

        assertThat(response).isNotNull();
    }

    @Test
    void 차량은_번호판으로_중복을_체크합니다(){
        given(managerRepository.findById(1L)).willReturn(Optional.of(manager));
        Vehicle existing = Vehicle.builder()
                .company(manager.getCompany())
                .info(new VehicleInfo(request.vehicleNumber(), request.brand(), request.modelName(),
                        request.vehicleType(), request.fuelType()))
                .build();

        given(vehicleRepository.findByInfo_VehicleNumber(request.vehicleNumber()))
                .willReturn(Optional.of(existing));

        assertThatThrownBy(() -> vehicleService.addVehicle(vehicle, 1L))
                .isInstanceOf(DomainException.class);
    }

    @Test
    void 자동차_번호_규칙을_준수하지_않으면_예외발생 () {
        VehicleAddRequest request = new VehicleAddRequest(
                1L,
                "가 1234",
                "벤츠",
                "아반떼",
                SEDAN,
                DIESEL,
                100000L,
                "1000W"
        );

        Set<ConstraintViolation<VehicleAddRequest>> violations = validator.validate(request);
        assertThat(violations).isNotEmpty();

    }
}