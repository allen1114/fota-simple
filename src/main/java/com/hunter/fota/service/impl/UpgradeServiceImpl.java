package com.hunter.fota.service.impl;

import com.hunter.fota.domain.Project;
import com.hunter.fota.domain.UpgradePatch;
import com.hunter.fota.domain.Version;
import com.hunter.fota.repository.ProjectRepository;
import com.hunter.fota.repository.UpgradePatchRepository;
import com.hunter.fota.repository.VersionRepository;
import com.hunter.fota.service.UpgradeService;
import com.hunter.fota.service.dto.UpgradeInfoDto;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UpgradeServiceImpl implements UpgradeService {

    private ProjectRepository projectRepository;
    private VersionRepository versionRepository;
    private UpgradePatchRepository upgradePatchRepository;

    public UpgradeServiceImpl(ProjectRepository projectRepository, VersionRepository versionRepository,
                              UpgradePatchRepository upgradePatchRepository) {
        this.projectRepository = projectRepository;
        this.versionRepository = versionRepository;
        this.upgradePatchRepository = upgradePatchRepository;
    }


    private Version findLatestFullVersion(Project project, Boolean test) {
        List<Version> list;
        if (test) {
            list = versionRepository.findByProjectOrderByLevelDesc(project);
        } else {
            list = versionRepository.findByProjectAndIssuedOrderByLevelDesc(project, true);
        }
        for (Version version : list) {
            if (version.getFileResource() != null) {
                return version;
            }
        }
        return null;
    }

//    @Override
//    public LatestVersionDto findLatestVersion(String projectCode, Boolean test) {
//        Project project = projectRepository.findByCode(projectCode);
//        if (project == null) {
//            return LatestVersionDto.empty;
//        }
//        return findLatestFullVersion(project, test);
//    }

    @Override
    public UpgradeInfoDto getUpgradeInfo(String projectCode, String baseVersionCode, Boolean test) {

        Project project = projectRepository.findByCode(projectCode);
        if (project == null) {
            return UpgradeInfoDto.empty;
        }

        Version baseVersion = versionRepository.findByProjectAndCode(project, baseVersionCode);
        if (baseVersion == null) {
            if (project.getFullUpgrade()) {
                return UpgradeInfoDto.of(findLatestFullVersion(project, test));
            }
            return UpgradeInfoDto.empty;
        }
        return getUpgradeInfo(project, baseVersion, test);
    }

    private UpgradeInfoDto getUpgradeInfo(Project project, Version baseVersion, Boolean test) {

        Version latestFullVersion = findLatestFullVersion(project, test);
        if (latestFullVersion != null && latestFullVersion.getLevel() <= baseVersion.getLevel()) {
            // 版本级别需大于基础版本
            latestFullVersion = null;
        }

        List<UpgradePatch> upgradePatches = upgradePatchRepository.findByBaseVersion(baseVersion);

        upgradePatches.sort((u1, u2) -> u1.getTargetVersion().getLevel().compareTo(u2.getTargetVersion().getLevel()) * -1);

        UpgradePatch latestUpgradePatch = null;

        for (UpgradePatch upgradePatch : upgradePatches) {
            if (upgradePatch.getFileResource() != null
                    && (test || upgradePatch.getTargetVersion().getIssued())) {
                if (upgradePatch.getTargetVersion().getLevel() > baseVersion.getLevel()) {
                    // 版本级别需大于基础版本
                    latestUpgradePatch = upgradePatch;
                }
                break;
            }
        }

        if (project.getFullUpgrade()) {

            if (latestUpgradePatch == null) {
                return UpgradeInfoDto.of(latestFullVersion);
            }
            if (latestFullVersion == null) {
                return UpgradeInfoDto.of(latestUpgradePatch);
            }

            if (latestUpgradePatch.getTargetVersion().getLevel() >= latestFullVersion.getLevel()) {
                return UpgradeInfoDto.of(latestUpgradePatch);
            } else {
                return UpgradeInfoDto.of(latestFullVersion);
            }

        } else if (latestUpgradePatch != null) {
            return UpgradeInfoDto.of(latestUpgradePatch);
        }

        return UpgradeInfoDto.empty;
    }
//
//    @Override
//    public UpgradePatchDto findUpgradePatch(String projectCode, String baseVersionCode, String targetVersionCode) {
//
//        Project project = projectRepository.findByCode(projectCode);
//        if (project == null) {
//            return UpgradePatchDto.empty;
//        }
//
//        Version baseVersion = versionRepository.findByProjectAndCode(project, baseVersionCode);
//        Version targetVersion = versionRepository.findByProjectAndCode(project, targetVersionCode);
//        if (baseVersion == null || targetVersion == null) {
//            return UpgradePatchDto.empty;
//        }
//
//        UpgradePatch upgradePatch = upgradePatchRepository.findByBaseVersionAndTargetVersion(baseVersion, targetVersion);
//        return upgradePatch != null && upgradePatch.getFileResource() != null ? UpgradePatchDto.fromUpgradePatch(upgradePatch) :
//                UpgradePatchDto.empty;
//    }
}
