package com.hunter.fota.rest;

import com.hunter.fota.domain.UpgradePatch;
import com.hunter.fota.service.UpgradePatchService;
import com.hunter.fota.service.query.UpgradePathQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/upgradePatch")
public class UpgradePatchController {

    @Autowired
    private UpgradePatchService upgradePatchService;

    @GetMapping("/{id}")
    public UpgradePatch get(@PathVariable("id") Long id) {
        return upgradePatchService.findById(id);
    }

    @PostMapping
    public UpgradePatch create(@RequestBody UpgradePatch upgradePatch) {
        return upgradePatchService.create(upgradePatch);
    }

    @PutMapping
    public UpgradePatch update(@RequestBody UpgradePatch upgradePatch) {
        return upgradePatchService.update(upgradePatch);
    }

    @GetMapping("/list")
    public List<UpgradePatch> list(UpgradePathQueryCriteria queryCriteria) {
        return upgradePatchService.queryList(queryCriteria);
    }

    @GetMapping("/page")
    public Page<UpgradePatch> page(UpgradePathQueryCriteria queryCriteria,
                                   @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return upgradePatchService
                .queryPage(queryCriteria, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));
    }

}
