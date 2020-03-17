package com.hunter.fota.service.dto;

import com.hunter.fota.domain.UpgradePatch;
import lombok.Data;

@Data
public class UpgradePatchDto {

    private String baseVersionCode;
    private String targetVersionCode;
    private String downloadUrl;
    private String hashType;
    private String hash;
    private Long size;

    public static final UpgradePatchDto empty = new UpgradePatchDto();

    public static UpgradePatchDto fromUpgradePatch(UpgradePatch upgradePatch) {
        if (upgradePatch == null) {
            return empty;
        } else {
            UpgradePatchDto upgradePatchDto = new UpgradePatchDto();
            upgradePatchDto.setBaseVersionCode(upgradePatch.getBaseVersion().getCode());
            upgradePatchDto.setTargetVersionCode(upgradePatch.getTargetVersion().getCode());
            upgradePatchDto.setDownloadUrl(upgradePatch.getFileResource().getUrl());
            upgradePatchDto.setSize(upgradePatch.getFileResource().getSize());
            upgradePatchDto.setHash(upgradePatch.getFileResource().getHash());
            upgradePatchDto.setHashType(upgradePatch.getFileResource().getHashType());
            return upgradePatchDto;
        }
    }

}
