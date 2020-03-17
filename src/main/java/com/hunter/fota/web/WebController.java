package com.hunter.fota.web;

import com.hunter.fota.conf.QiniuConfig;
import com.hunter.fota.domain.Version;
import com.hunter.fota.service.VersionService;
import com.hunter.fota.service.query.VersionQueryCriteria;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebController {

    private QiniuConfig qiniuConfig;
    private Auth auth;
    private VersionService versionService;

    public WebController(QiniuConfig qiniuConfig, Auth auth, VersionService versionService) {
        this.qiniuConfig = qiniuConfig;
        this.auth = auth;
        this.versionService = versionService;
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
        return "upload";
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
