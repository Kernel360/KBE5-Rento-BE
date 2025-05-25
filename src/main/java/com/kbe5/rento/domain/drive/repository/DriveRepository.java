package com.kbe5.rento.domain.drive.repository;

import com.kbe5.rento.domain.company.Company;
import com.kbe5.rento.domain.drive.entity.Drive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriveRepository extends JpaRepository<Drive, Long> {

    List<Drive> findByMember_Company(Company company);
}
