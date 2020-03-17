package com.hunter.fota.service;

import com.hunter.fota.domain.Version;
import com.hunter.fota.service.query.VersionQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VersionService {

    Version findById(Long id);

    Version create(Version version);

    Version update(Version version);

    List<Version> queryList(VersionQueryCriteria queryCriteria);

    Page<Version> queryPage(VersionQueryCriteria queryCriteria, Pageable pageable);

}
