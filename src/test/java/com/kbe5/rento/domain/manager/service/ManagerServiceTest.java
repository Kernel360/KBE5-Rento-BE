package com.kbe5.rento.domain.manager.service;

import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.service.CompanyService;
import com.kbe5.rento.domain.manager.dto.request.ManagerDeleteRequest;
import com.kbe5.rento.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.rento.domain.manager.dto.response.ManagerDeleteResponse;
import com.kbe5.rento.domain.manager.dto.response.ManagerResponse;
import com.kbe5.rento.domain.manager.dto.response.ManagerUpdateResponse;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {

    @InjectMocks
    private ManagerService managerService;

    @Mock
    private CompanyService companyService;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private PasswordEncoder encoder;

    Manager manager;
    Company company;

    @BeforeEach
    void init() {
        company = Company.builder()
                .name("testCompany")
                .bizNumber(1234567890)
                .build();

        company.assignCompanyCode("C1");

        manager = Manager.builder()
                .loginId("test")
                .password("1234")
                .phone("010-1234-5678")
                .email("test@test.com")
                .company(company)
                .companyCode(company.getCompanyCode())
                .build();
    }

    @Test
    void signUp() {
        // given

        given(managerRepository.save(any())).willReturn(manager);

        // when
        Manager newManger = managerService.signUp(manager);

        // then
        assertThat(newManger).isEqualTo(manager);
    }

    @Test
    void findByLoginId() {
        // given
        String loginId = manager.getLoginId();

        given(managerRepository.findByLoginId(any())).willReturn(Optional.ofNullable(manager));

        // when
        Manager findManager = managerService.findByLoginId(loginId);

        // then
        assertThat(findManager).isEqualTo(manager);
    }

    @Test
    void getManagerDetail() {
        // given
        Long id = manager.getId();

        given(managerRepository.findById(any())).willReturn(Optional.ofNullable(manager));
        // when
        Manager detailManager = managerService.getManagerDetail(id);

        ManagerResponse response = ManagerResponse.fromEntity(detailManager);
        // then
        assertThat(response.id()).isEqualTo(id);
    }

    @Test
    void getManagerList() {
        // given
        Manager manager1 = Manager.builder()
                .loginId("user1")
                .password("pass1")
                .email("user1@example.com")
                .name("User One")
                .phone("010-1111-1111")
                .company(company)
                .companyCode(company.getCompanyCode())
                .build();

        Manager manager2 = Manager.builder()
                .loginId("user2")
                .password("pass2")
                .email("user2@example.com")
                .name("User Two")
                .phone("010-2222-2222")
                .company(company)
                .companyCode(company.getCompanyCode())
                .build();

        List<Manager> managerList = List.of(manager1, manager2);

        given(managerRepository.findAllByCompanyCode(any())).willReturn(Optional.of(managerList));

        // when
        List<Manager> findManagerList = managerService.getManagerList(company.getCompanyCode());

        List<ManagerResponse> result = ManagerResponse.fromEntity(findManagerList);
        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).loginId()).isEqualTo("user1");
    }

    @Test
    void update() {
        // given
        ManagerUpdateRequest request = new ManagerUpdateRequest(manager.getId(), "updateTest",
                "updateTest", "updateTest");

        manager.toUpdate(request);

        given(managerRepository.findById(any())).willReturn(Optional.ofNullable(manager));
        // when
        Manager updatedManager = managerService.update(company.getId(), request);

        ManagerUpdateResponse response = ManagerUpdateResponse.fromEntity(updatedManager);
        // then
        assertThat(response.name()).isEqualTo("updateTest");
        assertThat(response.phone()).isEqualTo("updateTest");
        assertThat(response.email()).isEqualTo("updateTest");
    }

    @Test
    void delete() {
        // given
        ManagerDeleteRequest request = new ManagerDeleteRequest(manager.getLoginId(),
                manager.getPassword());


        given(managerRepository.findById(any())).willReturn(Optional.ofNullable(manager));

        // when
        boolean result = managerService.delete(company.getId(), request);

        ManagerDeleteResponse response = ManagerDeleteResponse.fromEntity(result);
        // then
        assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    void isExistsLoginId() {
        // given
        String loginId = "test";

        given(managerRepository.existsByLoginId(any())).willReturn(true);

        // when
        boolean result = managerService.isExistsLoginId(loginId);

        // then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void isExistsEmail() {
        // given
        String email = "test@test.com";

        given(managerRepository.existsByEmail(any())).willReturn(true);

        // when
        boolean result = managerService.isExistsEmail(email);

        // then
        assertThat(result).isEqualTo(true);
    }
}