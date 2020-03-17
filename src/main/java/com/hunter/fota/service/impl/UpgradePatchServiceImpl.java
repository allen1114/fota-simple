package com.hunter.fota.service.impl;

import com.hunter.fota.common.utils.QueryUtil;
import com.hunter.fota.domain.FileResource;
import com.hunter.fota.domain.UpgradePatch;
import com.hunter.fota.domain.Version;
import com.hunter.fota.exception.EntityExistsException;
import com.hunter.fota.exception.EntityNotFoundException;
import com.hunter.fota.exception.LogicalErrorException;
import com.hunter.fota.repository.FileResourceRepository;
import com.hunter.fota.repository.UpgradePatchRepository;
import com.hunter.fota.repository.VersionRepository;
import com.hunter.fota.service.UpgradePatchService;
import com.hunter.fota.service.query.UpgradePathQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Component
public class UpgradePatchServiceImpl implements UpgradePatchService {

    private UpgradePatchRepository upgradePatchRepository;
    private VersionRepository versionRepository;
    private FileResourceRepository fileResourceRepository;

    public UpgradePatchServiceImpl(UpgradePatchRepository upgradePatchRepository, VersionRepository versionRepository,
                                   FileResourceRepository fileResourceRepository) {
        this.upgradePatchRepository = upgradePatchRepository;
        this.versionRepository = versionRepository;
        this.fileResourceRepository = fileResourceRepository;
    }

    @Override
    public UpgradePatch findById(Long id) {
        return upgradePatchRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(UpgradePatch.class, Map.of("id", id)));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UpgradePatch create(@Validated(UpgradePatch.Create.class) UpgradePatch upgradePatch) {

        checkVersion(upgradePatch);
        checkBaseTarget(upgradePatch);
        checkFileResource(upgradePatch);
        return upgradePatchRepository.save(upgradePatch);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UpgradePatch update(@Validated(UpgradePatch.Update.class) UpgradePatch upgradePatch) {
        if (!upgradePatchRepository.existsById(upgradePatch.getId())) {
            throw new EntityNotFoundException(UpgradePatch.class, Map.of("id", upgradePatch.getId()));
        }
        checkFileResource(upgradePatch);
        return upgradePatchRepository.save(upgradePatch);
    }

    private void checkVersion(UpgradePatch upgradePatch) {
        upgradePatch.setBaseVersion(versionRepository.findById(upgradePatch.getBaseVersion().getId())
                .orElseThrow(() -> new EntityNotFoundException(Version.class, Map.of("id", upgradePatch.getBaseVersion().getId()))));
        upgradePatch.setTargetVersion(versionRepository.findById(upgradePatch.getTargetVersion().getId())
                .orElseThrow(() -> new EntityNotFoundException(Version.class, Map.of("id", upgradePatch.getTargetVersion().getId()))));
    }

    private void checkBaseTarget(UpgradePatch upgradePatch) {
        if (upgradePatchRepository.existsByBaseVersionAndTargetVersion(upgradePatch.getBaseVersion(), upgradePatch.getTargetVersion())) {
            throw new EntityExistsException(UpgradePatch.class,
                    Map.of("baseVersionId", upgradePatch.getBaseVersion().getId(), "targetVersionId",
                            upgradePatch.getTargetVersion().getId()));
        }
        if (!upgradePatch.getBaseVersion().getProject().getId().equals(upgradePatch.getTargetVersion().getProject().getId())) {
            throw new LogicalErrorException("Base and target with different project!");
        }
    }

    private void checkFileResource(UpgradePatch upgradePatch) {
        if (upgradePatch.getFileResource() != null && upgradePatch.getFileResource().getId() != null) {
            upgradePatch.setFileResource(fileResourceRepository.findById(upgradePatch.getFileResource().getId()).orElseThrow(
                    () -> new EntityNotFoundException(FileResource.class, Map.of("id", upgradePatch.getFileResource().getId()))));
        } else {
            upgradePatch.setFileResource(null);
        }
    }

    @Override
    public List<UpgradePatch> queryList(UpgradePathQueryCriteria queryCriteria) {
        return upgradePatchRepository
                .findAll((root, criteriaQuery, criteriaBuilder) -> QueryUtil.getPredicate(root, queryCriteria, criteriaBuilder));

    }

    @Override
    public Page<UpgradePatch> queryPage(UpgradePathQueryCriteria queryCriteria, Pageable pageable) {
        return upgradePatchRepository
                .findAll((root, criteriaQuery, criteriaBuilder) -> QueryUtil.getPredicate(root, queryCriteria, criteriaBuilder), pageable);
    }
}
