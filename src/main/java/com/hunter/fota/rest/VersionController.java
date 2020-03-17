package com.hunter.fota.rest;

import com.hunter.fota.domain.Version;
import com.hunter.fota.service.VersionService;
import com.hunter.fota.service.query.VersionQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/version")
public class VersionController {

    private VersionService versionService;

    public VersionController(VersionService versionService) {
        this.versionService = versionService;
    }

    @GetMapping("/{id}")
    public Version get(@PathVariable("id") Long id) {
        return versionService.findById(id);
    }

    @PostMapping
    public Version create(@RequestBody Version version) {
        return versionService.create(version);
    }

    @PutMapping
    public Version update(@RequestBody Version version) {
        return versionService.update(version);
    }

    @GetMapping("/list")
    public List<Version> list(VersionQueryCriteria queryCriteria) {
        return versionService.queryList(queryCriteria);
    }

    @GetMapping("/page")
    public Page<Version> page(VersionQueryCriteria queryCriteria,
                              @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return versionService
                .queryPage(queryCriteria, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));
    }
}
