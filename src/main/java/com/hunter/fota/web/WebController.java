package com.hunter.fota.web;

import com.hunter.fota.conf.QiniuConfig;
import com.hunter.fota.domain.Version;
import com.hunter.fota.service.ProjectService;
import com.hunter.fota.service.UpgradePatchService;
import com.hunter.fota.service.VersionService;
import com.hunter.fota.service.query.VersionQueryCriteria;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    private QiniuConfig qiniuConfig;
    private Auth auth;
    private ProjectService projectService;
    private VersionService versionService;
    private UpgradePatchService upgradePatchService;

    public WebController(QiniuConfig qiniuConfig, Auth auth, ProjectService projectService,
                         VersionService versionService, UpgradePatchService upgradePatchService) {
        this.qiniuConfig = qiniuConfig;
        this.auth = auth;
        this.projectService = projectService;
        this.versionService = versionService;
        this.upgradePatchService = upgradePatchService;
    }

    @RequestMapping
    public String index() {
        return "redirect:/views/index.html";
    }

    @RequestMapping("/views/upload.html")
    public String upload(String uploadKey, Model model) {
        StringMap policy = new StringMap();
        policy.put("returnBody", qiniuConfig.getReturnBody());
        model.addAttribute("uploadToken",
                auth.uploadToken(qiniuConfig.getBucket(), uploadKey, qiniuConfig.getExpires(), policy));
        model.addAttribute("uploadUrl", qiniuConfig.getUploadUrl());
        model.addAttribute("bucket", qiniuConfig.getBucket());
        model.addAttribute("downloadUrl", qiniuConfig.getDownloadUrl());
        return "upload2";
    }

    @RequestMapping("/views/upgradePatch_add.html")
    public String upgragePatchAdd(Long targetVersionId, Model model) {
        Version version = versionService.findById(targetVersionId);
        VersionQueryCriteria versionQueryCriteria = new VersionQueryCriteria();
        versionQueryCriteria.setProjectId(version.getProject().getId());
        Map<Long, String> versionMap = new HashMap<>();
        for (Version ver : versionService.queryList(versionQueryCriteria)) {
            if (!ver.getId().equals(targetVersionId)) {
                versionMap.put(ver.getId(), ver.getCode());
            }
        }
        model.addAttribute("versionMap", versionMap);
        return "upgradePatch_add";
    }

}
