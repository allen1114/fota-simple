package com.hunter.fota.service;

import com.hunter.fota.service.dto.UpgradeInfoDto;

public interface UpgradeService {
//
//    LatestVersionDto findLatestVersion(String projectCode, Boolean test);

    UpgradeInfoDto getUpgradeInfo(String projectCode, String baseVersionCode, Boolean test);
//
//    UpgradePatchDto findUpgradePatch(String projectCode, String baseVersionCode, String targetVersionCode);
}
