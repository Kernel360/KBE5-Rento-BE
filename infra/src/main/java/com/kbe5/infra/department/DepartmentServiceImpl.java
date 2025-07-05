package com.kbe5.infra.department;

import com.kbe5.domain.department.dto.DepartmentCommand;
import com.kbe5.domain.department.dto.DepartmentInfo;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.department.service.DepartmentReader;
import com.kbe5.domain.department.service.DepartmentService;
import com.kbe5.domain.department.service.DepartmentStore;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentStore departmentStore;
    private final DepartmentReader departmentReader;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public DepartmentInfo registerDepartment(DepartmentCommand.Register command){
      Department initDepartment = command.toEntity();
      Department department = departmentStore.store(initDepartment);

      return DepartmentInfo.fromEntity(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentInfo> getDepartments(Long companyId) {
        List<Department> departmentList = departmentReader.getDepartmentsByCompanyId(companyId);

        return departmentList.stream()
                .map(this::createDepartment)
                .toList();
    }

    @Override
    @Transactional
    public DepartmentInfo updateDepartment(Long departmentId, DepartmentCommand.Update command) {
        Department department = departmentReader.getDepartmentById(departmentId);
        departmentStore.update(command, departmentId);

        return DepartmentInfo.fromEntity(department);
    }

    @Override
    @Transactional
    public boolean delete(Long departmentId) {
        //todo: member ddd로 변환하기
        List<Member> members = memberRepository.findAllByDepartmentId(departmentId);

        if(!members.isEmpty()) {
            throw new DomainException(ErrorType.ALREADY_MEMBER);
        }

        Department department = departmentReader.getDepartmentById(departmentId);
        departmentStore.delete(department);

        return true;
    }

    private DepartmentInfo createDepartment(Department department) {
        //todo: Member ddd로 변환
        List<Member> members = memberRepository.findAllByDepartmentId(department.getId());

        return DepartmentInfo.builder()
                .departmentId(department.getId())
                .departmentName(department.getDepartmentName())
                .numberOfEmployees(members.size())
                .build();
    }
}
