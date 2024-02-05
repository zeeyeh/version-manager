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
@Entity(name = "projects")
@Table(name = "projects")
@Comment("项目表")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "pid")
    @Comment("项目Id")
    private long pid;

    @Column(name = "name")
    @Comment("项目名称")
    private String name;

    @Column(name = "authors")
    @Comment("项目作者")
    private String authors;

    @Column(name = "status")
    @Comment("项目状态")
    private int status;
}
