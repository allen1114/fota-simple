package com.hunter.fota.repository;

import com.hunter.fota.domain.UpgradePatch;
import com.hunter.fota.domain.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UpgradePatchRepository extends JpaRepository<UpgradePatch, Long>, JpaSpecificationExecutor<UpgradePatch> {

    List<UpgradePatch> findAllByBaseVersion(Version baseVersion);

    boolean existsByBaseVersionAndTargetVersion(Version baseVersion, Version targetVersion);

    UpgradePatch findByBaseVersionAndTargetVersion(Version baseVersion, Version targetVersion);
}
