package com.hunter.fota.repository;

import com.hunter.fota.domain.Project;
import com.hunter.fota.domain.Push;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PushRepository extends JpaRepository<Push, Long>, JpaSpecificationExecutor<Push> {

    List<Push> findByProject(Project project);
}
