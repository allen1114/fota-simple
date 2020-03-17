package com.hunter.fota.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_file_resource")
public class FileResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time", updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp createTime;

    @Column(updatable = false, nullable = false)
    @NotBlank
    @Size(max = 255)
    private String name;

    @Column(updatable = false, nullable = false)
    @Pattern(regexp = "^(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]$")
    private String url;

    @Column(updatable = false, nullable = false)
    @NotNull
    private Long size;

    @Column(name = "hash_type", updatable = false)
    private String hashType;

    @Column(updatable = false)
    private String hash;

    public static final FileResource empty = new FileResource();
}
