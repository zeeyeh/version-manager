package com.zeeyeh.versionmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.io.Serializable;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "versions")
@Table(name = "versions")
@Comment("项目表")
public class Version implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "vid")
    @Comment("版本Id")
    private long vid;

    @Column(name = "pid")
    @Comment("项目Id")
    private long pid;

    @Column(name = "name")
    @Comment("版本名称")
    private String name;

    @Column(name = "url")
    @Comment("版本下载地址")
    private String url;

    @Column(name = "status")
    @Comment("版本状态")
    private int status;
}
