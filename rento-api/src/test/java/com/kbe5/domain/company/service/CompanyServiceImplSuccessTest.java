package com.kbe5.domain.company.service;

import com.kbe5.api.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.api.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.api.domain.company.mapper.CompanyRequestMapper;
import com.kbe5.domain.company.dto.CompanyCommand;
import com.kbe5.domain.company.dto.CompanyInfo;
import com.kbe5.domain.company.entity.Company;
import com.kbe5.infra.infrastructure.company.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplSuccessTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyReader companyReader;

    @Mock
    private CompanyStore companyStore;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company company;

    @BeforeEach
    void setUp() {
        company = Company.builder()
                .name("HunCompany")
                .bizNumber(12345)
                .build();

        company.assignCompanyCode("C1");
    }

    @Test
    void 업체_등록() {
        // given
        CompanyRegisterRequest request = new CompanyRegisterRequest(12345, "HunCompany");

        CompanyCommand.Register command = CompanyRequestMapper.toCommand(request);

        when(companyStore.store(any())).thenReturn(company); // 저장 시 반환 값

        // when
        CompanyInfo newCompanyInfo = companyService.register(command);

        // then
        assertNotNull(newCompanyInfo);
        assertEquals("HunCompany", newCompanyInfo.getName());
        assertEquals(12345, newCompanyInfo.getBizNumber());
    }

    @Test
    void update() {
        // given
        CompanyUpdateRequest request = new CompanyUpdateRequest(12333, "UpdateHunCompany");

        CompanyCommand.Update command = CompanyRequestMapper.toCommand(request);

        when(companyReader.findById(1L)).thenReturn(company);

        // when
        CompanyInfo updateCompanyInfo = companyService.update(1L, command);

        // then
        assertNotNull(updateCompanyInfo);
        assertEquals("UpdateHunCompany", updateCompanyInfo.getName());
        assertEquals(12333, updateCompanyInfo.getBizNumber());
    }

    @Test
    void getCompanyDetail() {
        // given
        when(companyReader.findById(1L)).thenReturn(company);

        // when
        CompanyInfo companyInfo = companyService.getCompanyDetail(1L);

        // then
        assertNotNull(companyInfo);
        assertEquals("HunCompany", companyInfo.getName());
        assertEquals(12345, companyInfo.getBizNumber());
        assertEquals("C1", companyInfo.getCompanyCode());
    }

    @Test
    void getCompanyList() {
        // given
        when(companyReader.findAll()).thenReturn(List.of(company));

        // when
        List<CompanyInfo> companyInfoList = companyService.getCompanyList();

        // then
        assertNotNull(companyInfoList);
        assertEquals(1, companyInfoList.size());
        assertEquals("HunCompany", companyInfoList.get(0).getName());
        assertEquals(12345, companyInfoList.get(0).getBizNumber());
        assertEquals("C1", companyInfoList.get(0).getCompanyCode());
    }

    @Test
    void isAvailableBizNumber() {
        // given
        when(companyReader.existsByBizNumber(12345)).thenReturn(true);

        // when
        boolean result = companyService.isAvailableBizNumber(12345);

        // then
        assertFalse(result);
    }
}
