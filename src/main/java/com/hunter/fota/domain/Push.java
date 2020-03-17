package com.hunter.fota.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_push")
public class Push {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "start_on", updatable = false, nullable = false)
    @NotNull
    private Timestamp startOn;

    @Column(name = "complete_on", updatable = false, nullable = false)
    @NotNull
    private Timestamp completeOn;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", updatable = false, nullable = false)
    @NotNull
    private Project project;

    public static final Push empty = new Push();
}
