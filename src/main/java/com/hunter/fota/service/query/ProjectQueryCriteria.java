package com.hunter.fota.service.query;

import com.hunter.fota.common.annotation.Query;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectQueryCriteria implements Serializable {

    @Query(blurry = "name,code")
    private String blurry;

    @Query
    private Boolean display;

}
