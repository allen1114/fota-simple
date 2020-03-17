package com.hunter.fota.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Long id;

    @Column(name = "create_time", updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @Column(unique = true, nullable = false, updatable = false)
    @NotNull(groups = Create.class)
    @Size(max = 255, groups = Create.class)
    private String code;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String remark;

    @Column(nullable = false)
    @NotNull
    private Boolean display;

    public @interface Update {
    }

    public @interface Create {
    }

    public static final Project empty = new Project();
}
