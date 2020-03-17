package com.hunter.fota.service.impl;

import com.hunter.fota.domain.FileResource;
import com.hunter.fota.exception.EntityNotFoundException;
import com.hunter.fota.repository.FileResourceRepository;
import com.hunter.fota.service.FileResourceService;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Map;

@Component
public class FileResourceServiceImpl implements FileResourceService {

    private FileResourceRepository fileResourceRepository;

    public FileResourceServiceImpl(FileResourceRepository fileResourceRepository) {
        this.fileResourceRepository = fileResourceRepository;
    }

    @Override
    public FileResource findById(Long id) {
        return fileResourceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(FileResource.class, Map.of("id", id)));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public FileResource create(@Validated FileResource fileResource) {
        return fileResourceRepository.save(fileResource);
    }
}
