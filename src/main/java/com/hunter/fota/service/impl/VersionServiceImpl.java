package com.hunter.fota.service.impl;

import com.hunter.fota.common.utils.MapUtil;
import com.hunter.fota.common.utils.QueryUtil;
import com.hunter.fota.domain.FileResource;
import com.hunter.fota.domain.Project;
import com.hunter.fota.domain.Version;
import com.hunter.fota.exception.EntityExistsException;
import com.hunter.fota.exception.EntityNotFoundException;
import com.hunter.fota.repository.FileResourceRepository;
import com.hunter.fota.repository.ProjectRepository;
import com.hunter.fota.repository.VersionRepository;
import com.hunter.fota.service.VersionService;
import com.hunter.fota.service.query.VersionQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Component
public class VersionServiceImpl implements VersionService {

    private ProjectRepository projectRepository;
    private VersionRepository versionRepository;
    private FileResourceRepository fileResourceRepository;

    public VersionServiceImpl(ProjectRepository projectRepository, VersionRepository versionRepository,
                              FileResourceRepository fileResourceRepository) {
        this.projectRepository = projectRepository;
        this.versionRepository = versionRepository;
        this.fileResourceRepository = fileResourceRepository;
    }

    @Override
    public Version findById(Long id) {
        return versionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Version.class, MapUtil.of("id", id)));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Version create(@Validated(Version.Create.class) Version version) {
        checkProject(version);
        checkCode(version);
        checkLevel(version);
        checkFileResource(version);
        return versionRepository.save(version);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Version update(@Validated(Version.Update.class) Version version) {

        Version versionToUpdate = versionRepository.findById(version.getId())
                .orElseThrow(() -> new EntityNotFoundException(Version.class, MapUtil.of("id", version.getId())));

        versionToUpdate.setFileResource(version.getFileResource());
        checkFileResource(versionToUpdate);

        if (!version.getLevel().equals(versionToUpdate.getLevel())) {
            checkLevel(version);
            versionToUpdate.setLevel(version.getLevel());
        }
        versionToUpdate.setIssueTime(version.getIssueTime());
        versionToUpdate.setIssued(version.getIssued());
        versionToUpdate.setNotes(version.getNotes());

        return versionRepository.save(versionToUpdate);
    }

    private void checkProject(Version version) {
        version.setProject(projectRepository.findById(version.getProject().getId())
                .orElseThrow(() -> new EntityNotFoundException(Project.class, MapUtil.of("id", version.getProject().getId()))));
    }

    private void checkFileResource(Version version) {
        if (version.getFileResource() != null && version.getFileResource().getId() != null) {
            version.setFileResource(fileResourceRepository.findById(version.getFileResource().getId())
                    .orElseThrow(
                            () -> new EntityNotFoundException(FileResource.class, MapUtil.of("id", version.getFileResource().getId()))));
        } else {
            version.setFileResource(null);
        }
    }

    private void checkCode(Version version) {
        if (versionRepository.existsByProjectAndCode(version.getProject(), version.getCode())) {
            throw new EntityExistsException(Version.class, MapUtil.of("project", version.getProject().getId(), "code", version.getCode()));
        }
    }

    private void checkLevel(Version version) {
        if (versionRepository.existsByProjectAndLevel(version.getProject(), version.getLevel())) {
            throw new EntityExistsException(Version.class,
                    MapUtil.of("project", version.getProject().getId(), "level", version.getLevel()));
        }
    }

    @Override
    public List<Version> queryList(VersionQueryCriteria queryCriteria) {
        return versionRepository
                .findAll((root, criteriaQuery, criteriaBuilder) -> QueryUtil.getPredicate(root, queryCriteria, criteriaBuilder));
    }

    @Override
    public Page<Version> queryPage(VersionQueryCriteria queryCriteria, Pageable pageable) {
        return versionRepository
                .findAll((root, criteriaQuery, criteriaBuilder) -> QueryUtil.getPredicate(root, queryCriteria, criteriaBuilder), pageable);
    }
}
