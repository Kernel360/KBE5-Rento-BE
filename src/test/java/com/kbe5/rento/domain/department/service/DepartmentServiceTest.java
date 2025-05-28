package com.kbe5.rento.domain.department.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import com.kbe5.rento.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.rento.domain.department.dto.response.DepartmentInfoResponse;
import com.kbe5.rento.domain.department.entity.Department;
import com.kbe5.rento.domain.department.repository.DepartmentRepository;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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
    private Company otherCompany;
    private Manager manager;
    private Manager otherManager;

    @BeforeEach
    void setUp() {
        // Mock 객체들만 생성하고, stubbing은 각 테스트에서 필요한 것만 설정
        company = mock(Company.class);
        otherCompany = mock(Company.class);
        manager = mock(Manager.class);
        otherManager = mock(Manager.class);
        department = mock(Department.class);
    }

    @Test
    @DisplayName("부서 등록 성공")
    void register_Success() {
        // given
        when(company.getId()).thenReturn(1L);

        Department newDepartment = mock(Department.class);
        when(newDepartment.getDepartmentName()).thenReturn("테스트 부서");
        when(newDepartment.getCompany()).thenReturn(company);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(departmentRepository.existsByDepartmentNameAndCompanyId("테스트 부서", 1L))
                .thenReturn(false);
        when(departmentRepository.save(any(Department.class))).thenReturn(newDepartment);

        // when
        Department result = departmentService.register(newDepartment);

        // then
        assertThat(result.getDepartmentName()).isEqualTo("테스트 부서");
        verify(companyRepository).findById(1L);
        verify(departmentRepository).existsByDepartmentNameAndCompanyId("테스트 부서", 1L);
        verify(departmentRepository).save(newDepartment);
    }

    @Test
    @DisplayName("부서 등록 시 회사를 찾을 수 없는 경우")
    void register_CompanyNotFound() {
        // given
        when(company.getId()).thenReturn(1L);

        Department newDepartment = mock(Department.class);
        when(newDepartment.getCompany()).thenReturn(company);

        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> departmentService.register(newDepartment))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.COMPANY_NOT_FOUND.getMessage());

        verify(departmentRepository, never()).save(any());
    }

    @Test
    @DisplayName("부서 등록 시 중복된 부서명인 경우")
    void register_DuplicateDepartmentName() {
        // given
        when(company.getId()).thenReturn(1L);

        Department newDepartment = mock(Department.class);
        when(newDepartment.getDepartmentName()).thenReturn("테스트 부서");
        when(newDepartment.getCompany()).thenReturn(company);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(departmentRepository.existsByDepartmentNameAndCompanyId("테스트 부서", 1L))
                .thenReturn(true);

        // when & then
        assertThatThrownBy(() -> departmentService.register(newDepartment))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.DUPLICATE_DEPARTMENT_NAME.getMessage());

        verify(departmentRepository, never()).save(any());
    }

    @Test
    @DisplayName("부서 목록 조회 성공")
    void getDepartments_Success() {
        // given
        when(company.getId()).thenReturn(1L);

        String companyCode = "T1";
        List<Department> departments = List.of(department);

        when(companyRepository.findByCompanyCode(companyCode)).thenReturn(Optional.of(company));
        when(departmentRepository.findAllByCompanyId(1L)).thenReturn(departments);

        // when
        List<Department> result = departmentService.getDepartments(companyCode);

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(department);
        verify(companyRepository).findByCompanyCode(companyCode);
        verify(departmentRepository).findAllByCompanyId(1L);
    }

    @Test
    @DisplayName("부서 목록 조회 시 회사를 찾을 수 없는 경우")
    void getDepartments_CompanyNotFound() {
        // given
        String companyCode = "INVALID";

        when(companyRepository.findByCompanyCode(companyCode)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> departmentService.getDepartments(companyCode))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.COMPANY_NOT_FOUND.getMessage());

        verify(departmentRepository, never()).findAllByCompanyId(any());
    }

    @Test
    @DisplayName("부서 수정 성공")
    void updateDepartment_Success() {
        // given
        when(company.getId()).thenReturn(1L);
        when(manager.getCompany()).thenReturn(company);
        when(department.getId()).thenReturn(1L);
        when(department.getDepartmentName()).thenReturn("테스트 부서");

        Long departmentId = 1L;
        DepartmentUpdateRequest request = new DepartmentUpdateRequest("T1", "수정된 부서");
        List<Member> members = Collections.emptyList();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));
        when(departmentRepository.existsByDepartmentNameAndCompanyId("수정된 부서", 1L))
                .thenReturn(false);
        when(memberRepository.findAllByDepartmentId(1L)).thenReturn(members);

        // department.update() 메서드 호출 후 변경된 이름 반환하도록 설정
        doAnswer(invocation -> {
            when(department.getDepartmentName()).thenReturn("수정된 부서");
            return null;
        }).when(department).update(request);

        // when
        DepartmentInfoResponse result = departmentService.updateDepartment(manager, departmentId, request);

        // then
        assertThat(result.departmentName()).isEqualTo("수정된 부서");
        assertThat(result.numberOfEmployees()).isEqualTo(0);
        verify(departmentRepository).findById(departmentId);
        verify(companyRepository).findByCompanyCode("T1");
        verify(departmentRepository).existsByDepartmentNameAndCompanyId("수정된 부서", 1L);
        verify(memberRepository).findAllByDepartmentId(1L);
        verify(department).update(request);
    }

    @Test
    @DisplayName("등록되지 않은 부서 수정 테스트")
    void updateDepartment_DepartmentNotFound() {
        // given
        Long departmentId = 999L;
        DepartmentUpdateRequest request = new DepartmentUpdateRequest("T1", "테스트 수정 부서");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> departmentService.updateDepartment(manager, departmentId, request))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.DEPARTMENT_NOT_FOUND.getMessage());

        verify(companyRepository, never()).findByCompanyCode(any());
    }

    @Test
    @DisplayName("부서 수정 시 회사를 찾을 수 없는 경우")
    void updateDepartment_CompanyNotFound() {
        // given
        Long departmentId = 1L;
        DepartmentUpdateRequest request = new DepartmentUpdateRequest("INVALID", "수정된 부서");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(companyRepository.findByCompanyCode("INVALID")).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> departmentService.updateDepartment(manager, departmentId, request))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.COMPANY_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("부서 수정 시 권한이 없는 경우")
    void updateDepartment_NotAuthorized() {
        // given
        when(otherManager.getCompany()).thenReturn(otherCompany);
        when(company.getId()).thenReturn(1L);
        when(otherCompany.getId()).thenReturn(2L);


        Long departmentId = 1L;
        DepartmentUpdateRequest request = new DepartmentUpdateRequest("T1", "수정된 부서");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));

        // when & then
        assertThatThrownBy(() -> departmentService.updateDepartment(otherManager, departmentId, request))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.NOT_AUTHORIZED.getMessage());
    }

    @Test
    @DisplayName("부서 수정 시 중복된 부서명인 경우")
    void updateDepartment_DuplicateDepartmentName() {
        // given
        when(company.getId()).thenReturn(1L);
        when(manager.getCompany()).thenReturn(company);

        Long departmentId = 1L;
        DepartmentUpdateRequest request = new DepartmentUpdateRequest("T1", "중복된 부서");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));
        when(departmentRepository.existsByDepartmentNameAndCompanyId("중복된 부서", 1L))
                .thenReturn(true);

        // when & then
        assertThatThrownBy(() -> departmentService.updateDepartment(manager, departmentId, request))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.DUPLICATE_DEPARTMENT_NAME.getMessage());

        verify(department, never()).update(any());
    }

    @Test
    @DisplayName("부서 삭제 성공")
    void delete_Success() {
        // given
        when(company.getId()).thenReturn(1L);
        when(manager.getCompany()).thenReturn(company);
        when(department.getCompany()).thenReturn(company);

        Long departmentId = 1L;
        List<Member> emptyMembers = Collections.emptyList();

        when(memberRepository.findAllByDepartmentId(departmentId)).thenReturn(emptyMembers);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        // when
        departmentService.delete(manager, departmentId);

        // then
        verify(memberRepository).findAllByDepartmentId(departmentId);
        verify(departmentRepository).findById(departmentId);
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
        assertThatThrownBy(() -> departmentService.delete(manager, departmentId))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.ALREADY_MEMBER.getMessage());

        verify(departmentRepository, never()).findById(any());
        verify(departmentRepository, never()).delete(any());
    }

    @Test
    @DisplayName("부서 삭제 시 부서를 찾을 수 없는 경우")
    void delete_DepartmentNotFound() {
        // given
        Long departmentId = 999L;
        List<Member> emptyMembers = Collections.emptyList();

        when(memberRepository.findAllByDepartmentId(departmentId)).thenReturn(emptyMembers);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> departmentService.delete(manager, departmentId))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.DEPARTMENT_NOT_FOUND.getMessage());

        verify(departmentRepository, never()).delete(any());
    }

    @Test
    @DisplayName("부서 삭제 시 권한이 없는 경우")
    void delete_NotAuthorized() {
        // given
        when(company.getId()).thenReturn(1L);
        when(otherCompany.getId()).thenReturn(2L);
        when(otherManager.getCompany()).thenReturn(otherCompany);
        when(department.getCompany()).thenReturn(company);

        Long departmentId = 1L;
        List<Member> emptyMembers = Collections.emptyList();

        when(memberRepository.findAllByDepartmentId(departmentId)).thenReturn(emptyMembers);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        // when & then
        assertThatThrownBy(() -> departmentService.delete(otherManager, departmentId))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.NOT_AUTHORIZED.getMessage());

        verify(departmentRepository, never()).delete(any());
    }
}