package com.hunter.fota.service.impl;

import com.hunter.fota.common.utils.QueryUtil;
import com.hunter.fota.domain.Project;
import com.hunter.fota.exception.EntityExistsException;
import com.hunter.fota.exception.EntityNotFoundException;
import com.hunter.fota.repository.ProjectRepository;
import com.hunter.fota.service.ProjectService;
import com.hunter.fota.service.query.ProjectQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Component
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Project.class, Map.of("id", id)));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Project create(@Validated(Project.Create.class) Project project) {
        if (projectRepository.existsByCode(project.getCode())) {
            throw new EntityExistsException(Project.class, Map.of("code", project.getCode()));
        }
        return projectRepository.save(project);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Project update(@Validated(Project.Update.class) Project project) {
        if (projectRepository.existsById(project.getId())) {
            return  projectRepository.save(project);
        } else {
            throw new EntityNotFoundException(Project.class, Map.of("id", project.getId()));
        }
    }

    @Override
    public List<Project> queryAll(ProjectQueryCriteria queryCriteria) {
        return projectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryUtil.getPredicate(root, queryCriteria, criteriaBuilder));
    }

    @Override
    public Page<Project> queryPage(ProjectQueryCriteria queryCriteria, Pageable pageable) {
        return projectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryUtil.getPredicate(root, queryCriteria, criteriaBuilder), pageable);

    }
}
