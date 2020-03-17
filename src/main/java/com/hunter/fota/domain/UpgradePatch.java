package com.hunter.fota.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_upgrade_patch",
        uniqueConstraints = {@UniqueConstraint(name = "base_target", columnNames = {"base_version_id", "target_version_id"})})
public class UpgradePatch {

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
    @JoinColumn(name = "base_version_id", referencedColumnName = "id", nullable = false, updatable = false)
    @NotNull(groups = Create.class)
    private Version baseVersion;

    @ManyToOne
    @JoinColumn(name = "target_version_id", referencedColumnName = "id", nullable = false, updatable = false)
    @NotNull(groups = Create.class)
    private Version targetVersion;

    @ManyToOne
    @JoinColumn(name = "file_resource_id", referencedColumnName = "id")
    private FileResource fileResource;

    public @interface Update {
    }

    public @interface Create {
    }

    public static final UpgradePatch empty = new UpgradePatch();
}
