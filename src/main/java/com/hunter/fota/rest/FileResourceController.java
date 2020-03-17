package com.hunter.fota.rest;

import com.hunter.fota.domain.FileResource;
import com.hunter.fota.service.FileResourceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/fileResource")
public class FileResourceController {

    private FileResourceService fileResourceService;

    public FileResourceController(FileResourceService fileResourceService) {
        this.fileResourceService = fileResourceService;
    }

    @GetMapping("/{id}")
    public FileResource findById(@PathVariable("id") Long id) {
        return fileResourceService.findById(id);
    }

    @PostMapping
    private FileResource create(@RequestBody FileResource fileResource) {
        return fileResourceService.create(fileResource);
    }
    
}
