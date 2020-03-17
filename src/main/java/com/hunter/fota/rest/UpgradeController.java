package com.hunter.fota.rest;

import com.hunter.fota.service.UpgradeService;
import com.hunter.fota.service.dto.LatestVersionDto;
import com.hunter.fota.service.dto.UpgradePatchDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/upgrade")
public class UpgradeController {

    private UpgradeService upgradeService;

    public UpgradeController(UpgradeService upgradeService) {
        this.upgradeService = upgradeService;
    }

    @GetMapping("/getLatestVersion")
    public LatestVersionDto getLatestVersion(@RequestParam(value = "projectCode") String projectCode,
                                             @RequestParam(value = "test", defaultValue = "false") Boolean test) {
        return upgradeService.findLatestVersion(projectCode, test);
    }

    @GetMapping("/getUpgradePatch")
    public UpgradePatchDto getUpgradePatch(@RequestParam(value = "projectCode") String projectCode,
                                           @RequestParam(value = "baseVersionCode") String baseVersionCode,
                                           @RequestParam(value = "targetVersionCode") String targetVersionCode) {
        return upgradeService.findUpgradePatch(projectCode, baseVersionCode, targetVersionCode);
    }
}
