package com.kbe5.rento.domain.geofence.service;

import com.kbe5.rento.domain.geofence.dto.request.GeofenceUpdateRequest;
import com.kbe5.rento.domain.geofence.entity.Geofence;
import com.kbe5.rento.domain.geofence.enums.EventType;
import com.kbe5.rento.domain.geofence.repository.GeofenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GeofenceServiceTest {

    @InjectMocks
    GeofenceService geofenceService;

    @Mock
    GeofenceRepository geofenceRepository;

    Geofence geofence;

    @BeforeEach
    void init() {
        geofence = Geofence.builder()
                .companyCode("C1")
                .name("testGeofence")
                .description("testGeofence")
                .radius(10)
                .eventType(EventType.ON)
                .latitude(1231421)
                .longitude(3213241)
                .isActive(true)
                .build();
    }

    @Test
    @DisplayName("geofence 등록 테스트")
    void register() {
        // given
        given(geofenceRepository.save(any())).willReturn(geofence);

        // when
        geofenceService.register(geofence);

        // then
        verify(geofenceRepository, times(1)).save(geofence); // 호출되었는지 확인
    }

    @Test
    @DisplayName("geofence 목록을 불러오는 테스트")
    void getGeofenceList() {
        // given
        Geofence geofence1 = Geofence.builder()
                .companyCode("C2")
                .name("testGeofence")
                .description("testGeofence")
                .radius(12)
                .eventType(EventType.ON)
                .latitude(31313)
                .longitude(3213241)
                .isActive(true)
                .build();

        Geofence geofence2 = Geofence.builder()
                .companyCode("C2")
                .name("testGeofence")
                .description("testGeofence")
                .radius(13)
                .eventType(EventType.OFF)
                .latitude(13323)
                .longitude(3232)
                .isActive(true)
                .build();

        List<Geofence> geofenceList = List.of(geofence1, geofence2);

        given(geofenceRepository.findAllByCompanyCode(any())).willReturn(geofenceList);

        // when
        List<Geofence> getGeofenceList = geofenceService.getGeofenceList("C2");

        // then
        assertThat(getGeofenceList).hasSize(2);
        assertThat(getGeofenceList.get(0).getCompanyCode()).isEqualTo("C2");
    }

    @Test
    void getGeofenceDetail() {
        // given
        given(geofenceRepository.findById(any())).willReturn(Optional.of(geofence));

        // when
        Geofence findGeofence = geofenceService.getGeofenceDetail(geofence.getId());

        // then
        assertThat(findGeofence.getId()).isEqualTo(geofence.getId());
        assertThat(findGeofence.getName()).isEqualTo(geofence.getName());
    }

    @Test
    void delete() {
        // given
        given(geofenceRepository.findById(any())).willReturn(Optional.of(geofence));

        // when
        geofenceService.delete(geofence.getId());

        // then
        verify(geofenceRepository, times(1)).delete(geofence);
    }

    @Test
    void update() {
        // given
        GeofenceUpdateRequest geofenceUpdateRequest = new GeofenceUpdateRequest("C3",
                "updatedGeofence", 3214214, 51515214, 21,
                "updatedGeofence", true);

        Geofence updatedGeofence = GeofenceUpdateRequest.toEntity(geofenceUpdateRequest);

        given(geofenceRepository.findById(any())).willReturn(Optional.of(geofence));

        // when
        geofenceService.update(geofence.getId(), updatedGeofence);

        // then
        assertThat(geofence.getLatitude()).isEqualTo(geofenceUpdateRequest.latitude());
    }
}