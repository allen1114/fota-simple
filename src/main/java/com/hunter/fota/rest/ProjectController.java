package com.hunter.fota.rest;

import com.hunter.fota.domain.Project;
import com.hunter.fota.service.ProjectService;
import com.hunter.fota.service.query.ProjectQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    public Project get(@PathVariable("id") Long id) {
        return projectService.findById(id);
    }

    @PostMapping
    public Project create(@RequestBody Project project) {
        return projectService.create(project);
    }

    @PutMapping
    public Project update(@RequestBody Project project) {
        return projectService.update(project);
    }

    @GetMapping("/list")
    public List<Project> list(ProjectQueryCriteria queryCriteria) {
        return projectService.queryAll(queryCriteria);
    }

    @GetMapping("/page")
    public Page<Project> page(ProjectQueryCriteria queryCriteria,
                              @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return projectService
                .queryPage(queryCriteria, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));
    }

}
