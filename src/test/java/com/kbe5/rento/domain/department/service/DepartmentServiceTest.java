package com.kbe5.rento.domain.department.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import com.kbe5.rento.domain.department.dto.request.DepartmentRegisterRequest;
import com.kbe5.rento.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.rento.domain.department.dto.response.DepartmentInfoResponse;
import com.kbe5.rento.domain.department.entity.Department;
import com.kbe5.rento.domain.department.repository.DepartmentRepository;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;
    private Company company;

    @BeforeEach
    void setUp() {
        company = Company.builder()
                .bizNumber(1111)
                .name("테스트 회사")
                .build();
        company.assignCompanyCode("T1");

        department = Department.builder()
                .departmentName("테스트 부서")
                .company(company)
                .build();
    }

    @Test
    @DisplayName("부서 등록 성공")
    void register_Success() {
        // given
        DepartmentRegisterRequest request = new DepartmentRegisterRequest("T1", "테스트 부서");

        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));
        when(departmentRepository.existsByDepartmentNameAndCompanyId("테스트 부서", company.getId()))
                .thenReturn(false);

        // when
        departmentService.register(request);

        // then
        verify(companyRepository).findByCompanyCode("T1");
        verify(departmentRepository).existsByDepartmentNameAndCompanyId("테스트 부서", company.getId());

        ArgumentCaptor<Department> captor = ArgumentCaptor.forClass(Department.class);
        verify(departmentRepository).save(captor.capture());

        Department savedDepartment = captor.getValue();
        assertThat(savedDepartment.getDepartmentName()).isEqualTo("테스트 부서");
        assertThat(savedDepartment.getCompany()).isEqualTo(company);
    }

    @Test
    @DisplayName("부서 등록 시 중복된 부서명인 경우")
    void register_DuplicateDepartmentName() {
        // given
        DepartmentRegisterRequest request = new DepartmentRegisterRequest("T1", "중복 부서");

        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));
        when(departmentRepository.existsByDepartmentNameAndCompanyId("중복 부서", company.getId()))
                .thenReturn(true);

        // when & then
        assertThatThrownBy(() -> departmentService.register(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("이미 존재하는 부서 이름입니다.");

        verify(departmentRepository, never()).save(any());
    }

    @Test
    @DisplayName("부서 목록 테스트")
    void getDepartments() {
        //given
        String companyCode = "T1";
        Long companyId = 1L;

        Company company = Mockito.mock(Company.class);
        when(company.getId()).thenReturn(companyId);

        List<Department> departments = List.of(department);

        when(companyRepository.findByCompanyCode(companyCode)).thenReturn(Optional.of(company));
        when(departmentRepository.findAllByCompanyId(companyId)).thenReturn(departments);

        //when
        List<DepartmentInfoResponse> departmentInfoResponses = departmentService.getDepartments(companyCode);

        //then
        DepartmentInfoResponse departmentInfoResponse = departmentInfoResponses.get(0);
        assertThat(departmentInfoResponse).isNotNull();

    }

    @Test
    @DisplayName("부서 수정 성공")
    void updateDepartment_Success() {
        // given
        Long departmentId = 1L;
        DepartmentUpdateRequest request = new DepartmentUpdateRequest("T1", "수정된 부서");
        List<Member> members = Collections.emptyList();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));
        when(departmentRepository.existsByDepartmentNameAndCompanyId("수정된 부서", company.getId()))
                .thenReturn(false);
        when(memberRepository.findAllByDepartmentId(department.getId())).thenReturn(members);

        // when
        DepartmentInfoResponse result = departmentService.updateDepartment(departmentId, request);

        // then
        assertThat(department.getDepartmentName()).isEqualTo("수정된 부서");
        assertThat(result.departmentName()).isEqualTo("수정된 부서");
        assertThat(result.numberOfEmployees()).isEqualTo(0);
    }

    @Test
    @DisplayName("등록되지 않은 부서 수정 테스트")
    void updateDepartment_NotFound(){
        //given
        Long departmentId = 999L;

        DepartmentUpdateRequest request = new DepartmentUpdateRequest(
                "T1",
                "테스트 수정 부서"
        );

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        DomainException exception = assertThrows(DomainException.class, () -> {
            departmentService.updateDepartment(departmentId, request);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ErrorType.DEPARTMENT_NOT_FOUND.getMessage());
        verify(departmentRepository, never()).save(any());
    }

    @Test
    @DisplayName("부서 삭제 성공")
    void delete_Success() {
        // given
        Long departmentId = 1L;
        List<Member> emptyMembers = Collections.emptyList();

        when(memberRepository.findAllByDepartmentId(departmentId)).thenReturn(emptyMembers);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        // when
        departmentService.delete(departmentId);

        // then
        verify(departmentRepository).delete(department);
    }

    @Test
    @DisplayName("부서 삭제 시 소속 직원이 있는 경우")
    void delete_HasMembers() {
        // given
        Long departmentId = 1L;
        Member member = mock(Member.class);
        List<Member> members = List.of(member);

        when(memberRepository.findAllByDepartmentId(departmentId)).thenReturn(members);

        // when & then
        assertThatThrownBy(() -> departmentService.delete(departmentId))
                .isInstanceOf(DomainException.class)
                .hasMessage("해당 부서에 소속된 직원이 있어 삭제할 수 없습니다.");

        verify(departmentRepository, never()).deleteById(any());
    }
}