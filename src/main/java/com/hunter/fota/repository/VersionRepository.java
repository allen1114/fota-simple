package com.hunter.fota.repository;

import com.hunter.fota.domain.Project;
import com.hunter.fota.domain.Version;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface VersionRepository extends JpaRepository<Version, Long>, JpaSpecificationExecutor<Version> {

    boolean existsByProjectAndCode(Project project, String code);

    boolean existsByProjectAndLevel(Project project, Long level);

    Page<Version> findByProjectAndIssued(Project project, Boolean issued, Pageable pageable);

    Version findByProjectAndCode(Project project, String code);

    Page<Version> findByProject(Project project, Pageable pageable);

    List<Version> findByProjectAndIssuedOrderByLevelDesc(Project project, Boolean issued);

    List<Version> findByProjectOrderByLevelDesc(Project project);
}
