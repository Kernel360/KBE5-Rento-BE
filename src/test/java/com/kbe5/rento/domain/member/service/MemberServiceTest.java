package com.kbe5.rento.domain.member.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import com.kbe5.rento.domain.department.entity.Department;
import com.kbe5.rento.domain.department.repository.DepartmentRepository;
import com.kbe5.rento.domain.manager.entity.Manager;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private MemberService memberService;

    private Department department;
    private Member member;
    private Company company;
    private Manager manager;

    @BeforeEach
    void setUp() {
        company = mock(Company.class);
        department = mock(Department.class);
        member = mock(Member.class);
        manager = mock(Manager.class);
    }

    @Test
    @DisplayName("회원 등록 성공")
    void register_Success() {
        // given
        when(company.getId()).thenReturn(1L);

        // register에 실제 전달하는 member에 대한 stub 필요
        when(member.getCompanyCode()).thenReturn("T1");
        when(member.getPhoneNumber()).thenReturn("010-1234-5678");

        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        Member result = memberService.register(member, 1L);

        // then
        assertThat(result.getCompanyCode()).isEqualTo("T1");
        verify(companyRepository, times(2)).findByCompanyCode("T1");
        verify(memberRepository, times(1)).save(any(Member.class));
    }


    @Test
    @DisplayName("존재하지 않는 부서로 회원 등록 시 예외터지기")
    void register_DepartmentNotFound() {
        //given
        when(member.getCompanyCode()).thenReturn("T1");
        when(member.getPhoneNumber()).thenReturn("010-1234-5678");

        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));
        when(departmentRepository.findById(any())).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> memberService.register(member, 999L))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.DEPARTMENT_NOT_FOUND.getMessage());

        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("회원 등록 시 전화번호 중복인 경우")
    void register_Duplicate_Phone_Number(){
        //given
        when(member.getCompanyCode()).thenReturn("T1");
        when(member.getPhoneNumber()).thenReturn("010-1234-5678");

        when(company.getId()).thenReturn(1L);
        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));
        when(memberRepository.existsByPhoneNumberAndCompanyId("010-1234-5678", 1L)).thenReturn(true);

        //when & then
        assertThatThrownBy(() -> memberService.register(member, 1L))
        .isInstanceOf(DomainException.class)
                .hasMessage(ErrorType.DUPLICATE_PHONE_NUMBER.getMessage());

        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("회원 수정 성공")
    void update_Success() {
        // given
        Long memberId = 1L;
        Long departmentId = 2L;
        MemberUpdateRequest request = mock(MemberUpdateRequest.class);

        when(manager.getCompany()).thenReturn(company);
        when(company.getId()).thenReturn(1L);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        when(request.departmentId()).thenReturn(departmentId);
        when(request.phoneNumber()).thenReturn("010-9999-8888");
        when(request.companyCode()).thenReturn("T1");

        // 기타 업데이트 필드도 mock
        when(request.name()).thenReturn("수정된이름");
        when(request.email()).thenReturn("edit@example.com");
        when(request.getPosition()).thenReturn(Position.PRESIDENT);
        when(request.loginId()).thenReturn("newLogin");

        when(member.getCompany()).thenReturn(company);

        when(member.getDepartment()).thenReturn(department);
        when(department.getId()).thenReturn(2L);

        // 중복 아님
        when(companyRepository.findByCompanyCode("T1")).thenReturn(Optional.of(company));
        when(memberRepository.existsByPhoneNumberAndCompanyId("010-9999-8888", 1L)).thenReturn(false);

        // when
        MemberInfoResponse result = memberService.update(manager, request, memberId);

        // then
        assertThat(result).isNotNull();
        verify(memberRepository, times(1)).findById(memberId);
        verify(departmentRepository, times(1)).findById(departmentId);
        verify(member, times(1)).update(any(), any(), any(), any(), any(), any());
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
            memberService.update(manager, update, memberId);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ErrorType.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    void delete() {
        //given
        Long memberId = 1L;

        when(manager.getCompany()).thenReturn(company);
        when(company.getId()).thenReturn(1L);
        when(member.getCompany()).thenReturn(company);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        //when
        memberService.delete(manager, memberId);

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
            memberService.delete(manager, memberId);
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
        List<Member> result = memberService.getMemberList(companyCode);

        // then
        Member response = result.get(0);
        assertThat(response.getName()).isEqualTo(member.getName());
        assertThat(response.getEmail()).isEqualTo(member.getEmail());
        assertThat(response.getPhoneNumber()).isEqualTo(member.getPhoneNumber());
        assertThat(response.getPosition()).isEqualTo(member.getPosition());
        assertThat(response.getLoginId()).isEqualTo(member.getLoginId());
    }

    @Test
    @DisplayName("사용자 상세 조회")
    void getUser() {
        // given
        Long memberId = 1L;

        when(department.getId()).thenReturn(10L);
        when(member.getDepartment()).thenReturn(department);
        when(member.getName()).thenReturn("박소윤");
        when(member.getPhoneNumber()).thenReturn("010-1111-2222");
        when(member.getPosition()).thenReturn(String.valueOf(Position.CEO));
        when(member.getLoginId()).thenReturn("test");
        when(member.getEmail()).thenReturn("test@test.com");

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        Member result = memberService.getMember(memberId);

        // then
        assertThat(result.getName()).isEqualTo("박소윤");
        assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222");
        assertThat(result.getPosition()).isEqualTo(String.valueOf(Position.CEO));
        assertThat(result.getLoginId()).isEqualTo("test");
        assertThat(result.getDepartment().getId()).isEqualTo(10L);
        assertThat(result.getEmail()).isEqualTo("test@test.com");
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