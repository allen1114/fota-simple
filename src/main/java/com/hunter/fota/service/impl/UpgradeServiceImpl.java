package com.hunter.fota.service.impl;

import com.hunter.fota.domain.Project;
import com.hunter.fota.domain.UpgradePatch;
import com.hunter.fota.domain.Version;
import com.hunter.fota.repository.ProjectRepository;
import com.hunter.fota.repository.UpgradePatchRepository;
import com.hunter.fota.repository.VersionRepository;
import com.hunter.fota.service.UpgradeService;
import com.hunter.fota.service.dto.LatestVersionDto;
import com.hunter.fota.service.dto.UpgradePatchDto;
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

    @Override
    public LatestVersionDto findLatestVersion(String projectCode, Boolean test) {
        Project project = projectRepository.findByCode(projectCode);
        if (project == null) {
            return LatestVersionDto.empty;
        }
        List<Version> list;
        if (test) {
            list = versionRepository.findByProjectOrderByLevelDesc(project);
        } else {
            list = versionRepository.findByProjectAndIssuedOrderByLevelDesc(project, true);
        }
        for (Version version : list) {
            if (version.getFileResource() != null) {
                return LatestVersionDto.fromVerion(version);
            }
        }
        return LatestVersionDto.empty;
    }

    @Override
    public UpgradePatchDto findUpgradePatch(String projectCode, String baseVersionCode, String targetVersionCode) {

        Project project = projectRepository.findByCode(projectCode);
        if (project == null) {
            return UpgradePatchDto.empty;
        }

        Version baseVersion = versionRepository.findByProjectAndCode(project, baseVersionCode);
        Version targetVersion = versionRepository.findByProjectAndCode(project, targetVersionCode);
        if (baseVersion == null || targetVersion == null) {
            return UpgradePatchDto.empty;
        }

        UpgradePatch upgradePatch = upgradePatchRepository.findByBaseVersionAndTargetVersion(baseVersion, targetVersion);
        return upgradePatch != null && upgradePatch.getFileResource() != null ? UpgradePatchDto.fromUpgradePatch(upgradePatch) :
                UpgradePatchDto.empty;
    }
}
