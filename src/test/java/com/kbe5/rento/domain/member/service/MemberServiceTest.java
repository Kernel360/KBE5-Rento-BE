package com.kbe5.rento.domain.member.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import com.kbe5.rento.domain.department.entity.Department;
import com.kbe5.rento.domain.department.repository.DepartmentRepository;
import com.kbe5.rento.domain.member.dto.request.MemberRegisterRequest;
import com.kbe5.rento.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.rento.domain.member.dto.response.MemberInfoResponse;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.member.entity.Position;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    private Department department;
    private Member member;
    private Company company;

    @BeforeEach
    void setUp() {
        company = Company.builder()
                .name("테스트 회사")
                .bizNumber(123)
                .build();
        company.assignCompanyCode("T1");

        department = Department.builder()
                .departmentName("테스트 부서")
                .company(company)
                .build();

        member = Member.builder()
                .name("테스트 사용자")
                .email("test@example.com")
                .position(Position.CEO)
                .loginId("testuser")
                .password("encodedPassword")
                .phoneNumber("010-1234-5678")
                .department(department)
                .company(company)
                .build();
    }

    @Test
    @DisplayName("회원 등록 성공")
    void register_Success() {
        // given
        MemberRegisterRequest request = new MemberRegisterRequest(
                "테스트 사용자",
                "test@example.com",
                Position.CEO.getDisplayName(),
                "testuser",
                "password123",
                "010-1234-5678",
                1L,
                "T1"
        );

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(memberRepository.existsByPhoneNumberAndCompanyId("010-1234-5678", company.getId()))
                .thenReturn(false);

        // when
        memberService.register(request);

        // then
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(captor.capture());

        Member savedMember = captor.getValue();
        assertThat(savedMember.getName()).isEqualTo("테스트 사용자");
        assertThat(savedMember.getEmail()).isEqualTo("test@example.com");
        assertThat(savedMember.getPhoneNumber()).isEqualTo("010-1234-5678");
        assertThat(savedMember.getDepartment()).isEqualTo(department);
        assertThat(savedMember.getCompany()).isEqualTo(company);
        assertThat(savedMember.getPassword()).isEqualTo("encodedPassword");
        assertThat(savedMember.getLoginId()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("존재하지 않는 부서로 회원 등록 시 예외터지기")
    void register_DepartmentNotFound() {
        //given
        MemberRegisterRequest request = getMemberRegisterRequest();

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        DomainException exception = assertThrows(DomainException.class, () -> {
            memberService.register(request);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ErrorType.DEPARTMENT_NOT_FOUND.getMessage());
        verify(memberRepository, never()).save(any());
    }

    @Test
    @DisplayName("회원 수정 성공")
    void update_Success() {
        // given
        Long memberId = 1L;
        MemberUpdateRequest request = new MemberUpdateRequest(
                "수정된 이름",
                "update@example.com",
                Position.MANAGER.getDisplayName(),
                2L,
                "010-9999-9999",
                "updatedUser",
                "T1"
        );

        Department newDepartment = Department.builder()
                .departmentName("새로운 부서")
                .company(company)
                .build();

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(departmentRepository.findById(2L)).thenReturn(Optional.of(newDepartment));
        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));
        when(memberRepository.existsByPhoneNumberAndCompanyId("010-9999-9999", company.getId()))
                .thenReturn(false);

        // when
        memberService.update(request, memberId);

        // then
        assertThat(member.getName()).isEqualTo("수정된 이름");
        assertThat(member.getEmail()).isEqualTo("update@example.com");
        assertThat(member.getPhoneNumber()).isEqualTo("010-9999-9999");
        assertThat(member.getDepartment()).isEqualTo(newDepartment);
        assertThat(member.getLoginId()).isEqualTo("updatedUser");
    }

    @Test
    @DisplayName("등록되지 않은 사용자 수정 테스트")
    void update_UserNotFound() {
        //given
        Long memberId = 999L;
        MemberUpdateRequest update = new MemberUpdateRequest(
                "수정된 이름",
                "update@example.com",
                Position.CEO.getDisplayName(),
                1L,
                "010-1111-1111",
                "updatedUser",
                "T1"
        );

        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        DomainException exception = assertThrows(DomainException.class, () -> {
            memberService.update(update, memberId);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ErrorType.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    void delete() {
        //given
        Long memberId = 1L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        //when
        memberService.delete(memberId);

        //then
        verify(memberRepository).delete(member);
    }

    @Test
    @DisplayName("등록되지 않은 사용자 삭제 테스트")
    void delete_User_Not_Found(){
        //given
        Long memberId = 999L;

        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        DomainException exception = assertThrows(DomainException.class, () -> {
            memberService.delete(memberId);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ErrorType.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("회원 목록 조회 성공")
    void getMemberList_Success() {
        // given
        String companyCode = "T1";
        List<Member> members = List.of(member);

        when(companyRepository.findByCompanyCode(companyCode)).thenReturn(Optional.of(company));
        when(memberRepository.findAllByCompanyId(company.getId())).thenReturn(members);

        // when
        List<MemberInfoResponse> result = memberService.getMemberList(companyCode);

        // then
        MemberInfoResponse response = result.get(0);
        assertThat(response.name()).isEqualTo(member.getName());
        assertThat(response.email()).isEqualTo(member.getEmail());
        assertThat(response.phoneNumber()).isEqualTo(member.getPhoneNumber());
        assertThat(response.position()).isEqualTo(member.getPosition());
        assertThat(response.login_id()).isEqualTo(member.getLoginId());
        assertThat(response.departmentId()).isEqualTo(member.getDepartment().getId());
        assertThat(response.departmentName()).isEqualTo(member.getDepartment().getDepartmentName());
    }

    @Test
    @DisplayName("사용자 상세 조회")
    void getUser() {
        //given
        Long memberId = 1L;

        department = Mockito.spy(department);

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        //when
        MemberInfoResponse result = memberService.getMember(memberId);

        //then
        assertThat(result.name()).isEqualTo(member.getName());
        assertThat(result.phoneNumber()).isEqualTo(member.getPhoneNumber());
        assertThat(result.position()).isEqualTo(member.getPosition());
        assertThat(result.login_id()).isEqualTo(member.getLoginId());
        assertThat(result.departmentId()).isEqualTo(member.getDepartment().getId());
        assertThat(result.departmentName()).isEqualTo(member.getDepartment().getDepartmentName());
        assertThat(result.email()).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("존재하지 않는 회원 조회")
    void getMember_MemberNotFound() {
        //given
        Long memberId = 999L;
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        DomainException exception = assertThrows(DomainException.class, () -> {
            memberService.getMember(memberId);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ErrorType.MEMBER_NOT_FOUND .getMessage());
        verify(memberRepository, never()).save(any());
    }
    private MemberRegisterRequest getMemberRegisterRequest() {
        return new MemberRegisterRequest(
                "테스트 사용자",
                "test@example.com",
                "테스트 직책",
                "testuser",
                "password123",
                "010-1234-5678",
                1L,
                "T1"
        );
    }
}