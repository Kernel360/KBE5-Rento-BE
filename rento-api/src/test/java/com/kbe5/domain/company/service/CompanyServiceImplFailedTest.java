package com.kbe5.domain.company.service;

import com.kbe5.api.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.api.domain.company.mapper.CompanyRequestMapper;
import com.kbe5.domain.company.dto.CompanyCommand;
import com.kbe5.domain.company.dto.CompanyInfo;
import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.infra.infrastructure.company.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplFailedTest {

    @Mock
    private CompanyReader companyReader;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void 업데이트_실패_존재하지_않는_ID() {
        // given
        CompanyUpdateRequest request = new CompanyUpdateRequest(12333, "UpdateHunCompany");
        CompanyCommand.Update command = CompanyRequestMapper.toCommand(request);

        // 여기!
        when(companyReader.findById(999L)).thenThrow(new DomainException(ErrorType.COMPANY_NOT_FOUND));

        // when & then
        assertThrows(DomainException.class, () -> {
            companyService.update(999L, command);
        });
    }

    @Test
    void getCompanyDetail_실패_존재하지_않는_ID() {
        // given
        when(companyReader.findById(999L)).thenThrow(new DomainException(ErrorType.COMPANY_NOT_FOUND));

        // when & then
        assertThrows(DomainException.class, () -> {
            companyService.getCompanyDetail(999L);
        });
    }

    @Test
    void getCompanyList_실패_빈_리스트() {
        // given
        when(companyReader.findAll()).thenReturn(List.of());

        // when
        List<CompanyInfo> companyInfoList = companyService.getCompanyList();

        // then
        assertNotNull(companyInfoList); // 빈 리스트지만 null은 아님
        assertEquals(0, companyInfoList.size());
    }

    @Test
    void isAvailableBizNumber_실패_사용가능한_번호지만_존재한다고_판단() {
        // given
        when(companyReader.existsByBizNumber(54321)).thenReturn(false);

        // when
        boolean result = companyService.isAvailableBizNumber(54321);

        // then
        assertTrue(result);
    }
}
