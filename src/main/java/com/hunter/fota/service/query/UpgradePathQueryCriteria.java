package com.hunter.fota.service.query;

import com.hunter.fota.common.annotation.Query;
import lombok.Data;

@Data
public class UpgradePathQueryCriteria {

    @Query(propName = "id", joinName = "baseVersion", type = Query.Type.EQUAL)
    private Long baseVersionId;

    @Query(propName = "id", joinName = "targetVersion", type = Query.Type.EQUAL)
    private Long targetVersionId;
}
