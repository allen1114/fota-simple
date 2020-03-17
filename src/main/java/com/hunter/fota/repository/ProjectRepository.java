package com.hunter.fota.repository;

import com.hunter.fota.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    boolean existsByCode(String code);

    Project findByCode(String code);

}
