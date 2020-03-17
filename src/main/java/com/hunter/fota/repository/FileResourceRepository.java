package com.hunter.fota.repository;

import com.hunter.fota.domain.FileResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileResourceRepository  extends JpaRepository<FileResource, Long>, JpaSpecificationExecutor<FileResource> {
}
