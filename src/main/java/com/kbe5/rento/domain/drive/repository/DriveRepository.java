package com.kbe5.rento.domain.drive.repository;

import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.drive.entity.Drive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriveRepository extends JpaRepository<Drive, Long> {

    List<Drive> findByMember_Company(Company company);
}
