package com.hunter.fota.service.dto;

import com.hunter.fota.domain.UpgradePatch;
import com.hunter.fota.domain.Version;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpgradeInfoDto {
    private String code;
    private String downloadUrl;
    private String hashType;
    private String hash;
    private Long size;
    private Timestamp issueTime;
    private String notes;
    private Boolean full;

    public static final UpgradeInfoDto empty = new UpgradeInfoDto();

    public static UpgradeInfoDto of(Version version) {
        if (version == null) {
            return empty;
        }
        UpgradeInfoDto upgradeInfoDto = new UpgradeInfoDto();
        upgradeInfoDto.setCode(version.getCode());
        upgradeInfoDto.setDownloadUrl(version.getFileResource().getUrl());
        upgradeInfoDto.setHash(version.getFileResource().getHash());
        upgradeInfoDto.setHashType(version.getFileResource().getHashType());
        upgradeInfoDto.setSize(version.getFileResource().getSize());
        upgradeInfoDto.setNotes(version.getNotes());
        upgradeInfoDto.setIssueTime(version.getIssueTime());
        upgradeInfoDto.setFull(true);
        return upgradeInfoDto;
    }

    public static UpgradeInfoDto of(UpgradePatch upgradePatch) {
        if (upgradePatch == null) {
            return empty;
        }
        UpgradeInfoDto upgradeInfoDto = new UpgradeInfoDto();
        upgradeInfoDto.setCode(upgradePatch.getTargetVersion().getCode());
        upgradeInfoDto.setDownloadUrl(upgradePatch.getFileResource().getUrl());
        upgradeInfoDto.setHash(upgradePatch.getFileResource().getHash());
        upgradeInfoDto.setHashType(upgradePatch.getFileResource().getHashType());
        upgradeInfoDto.setSize(upgradePatch.getFileResource().getSize());
        upgradeInfoDto.setNotes(upgradePatch.getTargetVersion().getNotes());
        upgradeInfoDto.setIssueTime(upgradePatch.getTargetVersion().getIssueTime());
        upgradeInfoDto.setFull(false);
        return upgradeInfoDto;
    }

}
