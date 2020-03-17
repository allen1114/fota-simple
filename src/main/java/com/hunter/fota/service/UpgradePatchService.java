package com.hunter.fota.service;

import com.hunter.fota.domain.UpgradePatch;
import com.hunter.fota.service.query.UpgradePathQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UpgradePatchService {

    UpgradePatch findById(Long id);

    UpgradePatch create(UpgradePatch upgradePatch);

    UpgradePatch update(UpgradePatch upgradePatch);

    List<UpgradePatch> queryList(UpgradePathQueryCriteria queryCriteria);

    Page<UpgradePatch> queryPage(UpgradePathQueryCriteria queryCriteria, Pageable pageable);
}
