package com.hunter.fota.service;

import com.hunter.fota.domain.UpgradePatch;
import com.hunter.fota.service.dto.LatestVersionDto;
import com.hunter.fota.service.dto.UpgradePatchDto;

public interface UpgradeService {

    LatestVersionDto findLatestVersion(String projectCode, Boolean test);

    UpgradePatchDto findUpgradePatch(String projectCode, String baseVersionCode, String targetVersionCode);
}
