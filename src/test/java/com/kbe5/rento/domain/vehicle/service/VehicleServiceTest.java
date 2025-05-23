package com.kbe5.rento.domain.vehicle.service;

import com.kbe5.rento.domain.manager.entity.ManagerService;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.repository.VehicleRepository;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


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

    @BeforeEach
    void setManager(){
        String code = "test";
        managerService.saveManager(code);
    }

    VehicleResponse createVehicle() {
        VehicleAddRequest request = new VehicleAddRequest(
                "123가 1234",
                "벤츠",
                "아반떼",
                SEDAN,
                DIESEL,
                100000L,
                "1000W"
        );

        return vehicleService.addVehicle(request);
    }

    @Test
    void 자동차_등록_테스트(){
        //given
        VehicleAddRequest request = new VehicleAddRequest(
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
    void 자동차_번호_규칙을_준수해야합니다 (){
        VehicleAddRequest request = new VehicleAddRequest(
                "가 1234",
                "벤츠",
                "아반떼",
                SEDAN,
                DIESEL,
                100000L,
                "1000W"
        );

        assertThatThrownBy(() -> vehicleService.addVehicle(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}