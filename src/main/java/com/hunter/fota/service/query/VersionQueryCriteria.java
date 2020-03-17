package com.hunter.fota.service.query;

import com.hunter.fota.common.annotation.Query;
import lombok.Data;

@Data
public class VersionQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String code;

    @Query(propName = "id", joinName = "project", type = Query.Type.EQUAL)
    private Long projectId;

    @Query(propName = "code", joinName = "project", type = Query.Type.INNER_LIKE)
    private Long projectCode;

    @Query
    private Boolean issued;

}
