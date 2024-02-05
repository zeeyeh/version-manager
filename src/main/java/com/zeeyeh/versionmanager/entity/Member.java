package com.zeeyeh.versionmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "members")
@Table(name = "members")
@Comment("用户表")
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Comment("用户id")
    private Long uid;

    @Comment("用户名")
    private String username;

    @Comment("用户昵称")
    private String nickname;

    @Comment("用户密码")
    @Column(name = "password", columnDefinition = "text")
    private String password;

    @Comment("用户邮箱")
    private String email;

    @Comment("用户头像")
    private String avatar;

    @Comment("用户继承的所有角色")
    private String roles;

    @Comment("允许的设备ip")
    private String allowIps;

    @Comment("用户状态")
    private int status;

    @Comment("注册时间")
    private LocalDateTime createTime;

    @Comment("最后修改时间")
    private LocalDateTime updateTime;
}
