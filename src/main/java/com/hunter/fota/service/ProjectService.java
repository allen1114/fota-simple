package com.hunter.fota.service;

import com.hunter.fota.domain.Project;
import com.hunter.fota.service.query.ProjectQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {

    Project findById(Long id);

    Project create(Project project);

    Project update(Project project);

    List<Project> queryAll(ProjectQueryCriteria queryCriteria);

    Page<Project> queryPage(ProjectQueryCriteria queryCriteria, Pageable pageable);

}
