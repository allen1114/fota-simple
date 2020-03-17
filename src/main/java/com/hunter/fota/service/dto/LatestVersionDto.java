package com.hunter.fota.service.dto;

import com.hunter.fota.domain.Version;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class LatestVersionDto {
    private String code;
    private String downloadUrl;
    private String hashType;
    private String hash;
    private Long size;
    private Timestamp issueTime;
    private String notes;

    public static final LatestVersionDto empty = new LatestVersionDto();

    public static LatestVersionDto fromVerion(Version version) {
        if (version == null) {
            return empty;
        }
        LatestVersionDto latestVersionDto = new LatestVersionDto();
        latestVersionDto.setCode(version.getCode());
        latestVersionDto.setDownloadUrl(version.getFileResource().getUrl());
        latestVersionDto.setHash(version.getFileResource().getHash());
        latestVersionDto.setHashType(version.getFileResource().getHashType());
        latestVersionDto.setSize(version.getFileResource().getSize());
        latestVersionDto.setNotes(version.getNotes());
        latestVersionDto.setIssueTime(version.getIssueTime());
        return latestVersionDto;
    }

}
