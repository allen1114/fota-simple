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
@Table(name = "t_version", uniqueConstraints = {@UniqueConstraint(name = "project_version", columnNames = {"project_id", " code"}),
        @UniqueConstraint(name = "version_level", columnNames = {"project_id", " level"})})
public class Version {

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

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", updatable = false, nullable = false)
    @NotNull(groups = Create.class)
    private Project project;

    @Column(updatable = false, nullable = false)
    @NotNull(groups = Create.class)
    @Size(max = 255, groups = Create.class)
    private String code;

    @Column(nullable = false)
    @NotNull
    private Long level;

    @Column(name = "issue_time", nullable = false)
    @NotNull
    private Timestamp issueTime;

    @Column(length = 1024)
    @Size(max = 1024)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "file_resource_id", referencedColumnName = "id")
    private FileResource fileResource;

    @Column(nullable = false)
    @NotNull
    private Boolean issued;

    public @interface Update {
    }

    public @interface Create {
    }

    public static final Version empty = new Version();

}
