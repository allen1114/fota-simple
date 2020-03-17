package com.hunter.fota.service;

import com.hunter.fota.domain.FileResource;

public interface FileResourceService {

    FileResource findById(Long id);

    FileResource create(FileResource fileResource);

}
