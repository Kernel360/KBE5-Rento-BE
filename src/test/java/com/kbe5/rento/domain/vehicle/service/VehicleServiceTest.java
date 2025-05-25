package com.kbe5.rento.domain.vehicle.service;

import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.manager.dto.request.ManagerSignUpRequest;
import com.kbe5.rento.domain.manager.service.ManagerService;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.repository.VehicleRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.kbe5.rento.domain.vehicle.entity.FuelType.DIESEL;
import static com.kbe5.rento.domain.vehicle.entity.VehicleType.SEDAN;
import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class VehicleServiceTest {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private Validator validator;

    @BeforeEach
    void setManager(){
        Company company = new Company();

        ManagerSignUpRequest managerSignUpRequest = new ManagerSignUpRequest(
                "aaaa",
                "aaaa",
                "test",
                "11111",
                "eeee",
                "J110"
        );

        managerService.signUp(managerSignUpRequest);
    }

    @Test
    void 자동차_등록_테스트(){
        //given
        VehicleAddRequest request = new VehicleAddRequest(
                "J101",
                "123가 1234",
                "벤츠",
                "아반떼",
                SEDAN,
                DIESEL,
                100000L,
                "1000W"
        );

        VehicleResponse response = vehicleService.addVehicle(request);
        Vehicle vehicle = vehicleRepository.findById(1L).orElseThrow();

        assertThat(response.vehicleNumber()).isEqualTo(vehicle.getVehicleNumber());
    }

    @Test
    void 자동차_번호_규칙을_준수해야합니다 () {
        VehicleAddRequest request = new VehicleAddRequest(
                "J100",
                "가 1234",
                "벤츠",
                "아반떼",
                SEDAN,
                DIESEL,
                100000L,
                "1000W"
        );
        // 어노테이션 테스트
        Set<ConstraintViolation<VehicleAddRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath()
                        .toString().equals("vehicleNumber"));
    }
}