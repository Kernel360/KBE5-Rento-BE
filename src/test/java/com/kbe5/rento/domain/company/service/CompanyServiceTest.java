package com.kbe5.rento.domain.company.service;

import com.kbe5.rento.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.rento.domain.company.dto.response.CompanyRegisterResponse;
import com.kbe5.rento.domain.company.dto.response.CompanyResponse;
import com.kbe5.rento.domain.company.dto.response.CompanyUpdateResponse;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
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

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;

    Company company;

    @BeforeEach
    void init() {
        company = Company.builder()
                .name("testCompany")
                .bizNumber(1234567890)
                .build();

        company.assignCompanyCode("C1");
    }

    @Test
    void register() {
        // given
        CompanyRegisterRequest request = new CompanyRegisterRequest(company.getBizNumber(), company.getName());

        given(companyRepository.save(any())).willReturn(company);
        // when
        CompanyRegisterResponse response = companyService.register(request);

        // then
        assertThat(response).isEqualTo(CompanyRegisterResponse.from(company));
    }

    @Test
    void findByBizNumber() {
        // given
        int bizNumber = company.getBizNumber();

        given(companyRepository.findByBizNumber(bizNumber)).willReturn(Optional.of(company));
        // when
        Company result = companyService.findByBizNumber(bizNumber);

        // then
        assertThat(result).isEqualTo(company);
    }

    @Test
    void findByCompanyCode() {
        // given
        String companyCode = company.getCompanyCode();

        given(companyRepository.findByCompanyCode(companyCode)).willReturn(Optional.of(company));
        // when
        Company result = companyService.findByCompanyCode(companyCode);

        // then
        assertThat(result).isEqualTo(company);
    }

    @Test
    void update() {
        // given
        CompanyUpdateRequest request = new CompanyUpdateRequest(123, "updateTest");

        given(companyRepository.findById(any())).willReturn(Optional.of(company));
        // when
        CompanyUpdateResponse response = companyService.update(company.getId(), request);

        // then
        assertThat(response.bizNumber()).isEqualTo(123);
        assertThat(response.name()).isEqualTo("updateTest");
    }

    // Delete 코드 만들고 구현 할 예정입니다.
    @Test
    void delete() {
        // given

        // when

        // then
    }

    @Test
    void getCompanyList() {
        // given
        Company company1 = Company.builder()
                .bizNumber(1234)
                .name("company1")
                .build();

        company1.assignCompanyCode("C1");

        Company company2 = Company.builder()
                .bizNumber(5678)
                .name("company2")
                .build();

        company1.assignCompanyCode("C2");

        List<Company> companyList = List.of(company1, company2);

        given(companyRepository.findAll()).willReturn(companyList);
        // when
        List<CompanyResponse> result = companyService.getCompanyList();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).bizNumber()).isEqualTo(1234);
        assertThat(result.get(1).name()).isEqualTo("company2");
    }

    @Test
    void getCompanyDetail() {
        // given
        Long id = company.getId();

        given(companyRepository.findById(any())).willReturn(Optional.of(company));
        // when
        CompanyResponse response = companyService.getCompanyDetail(id);

        // then
        assertThat(response.name()).isEqualTo(company.getName());
        assertThat(response.bizNumber()).isEqualTo(company.getBizNumber());
        assertThat(response.companyCode()).isEqualTo(company.getCompanyCode());
    }

    @Test
    void isExistsBizNumber() {
        // given
        int bizNumber = company.getBizNumber();

        given(companyRepository.existsByBizNumber(bizNumber)).willReturn(true);
        // when
        boolean result = companyService.isExistsBizNumber(bizNumber);

        // then
        assertThat(result).isEqualTo(true);
    }
}