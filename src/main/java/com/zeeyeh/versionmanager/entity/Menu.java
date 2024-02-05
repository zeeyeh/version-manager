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
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "menus")
@Table(name = "menus")
@Comment("菜单表")
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "mid")
    @Comment("菜单ID")
    private Long mid;

    @Column(name = "name")
    @Comment("菜单名称")
    private String name;

    @Column(name = "parent")
    @Comment("父级菜单Id")
    private Long parent;

    @Column(name = "menu_order")
    @Comment("显示顺序")
    private Integer order;

    @Column(name = "menu_path")
    @Comment("菜单路由")
    private String path;

    @Column(name = "component")
    @Comment("菜单组件")
    private String component;

    @Column(name = "params")
    @Comment("菜单参数")
    private String params;

    @Column(name = "frame")
    @Comment("是否外链")
    private Integer frame;

    @Column(name = "cache")
    @Comment("是否缓存")
    private Integer cache;

    @Column(name = "menu_type")
    @Comment("菜单类型(0目录 1菜单 2按钮)")
    private Integer type;

    @Column(name = "visible")
    @Comment("菜单状态(0隐藏 1显示)")
    private Integer visible;

    @Column(name = "status")
    @Comment("菜单状态(0正常 1停用)")
    private Integer status;

    @Column(name = "permission")
    @Comment("菜单权限")
    private String permission;

    @Column(name = "icon")
    @Comment("菜单图标")
    private String icon;

    @Column(name = "creator")
    @Comment("创建者")
    private String creator;

    @Column(name = "create_time")
    @Comment("创建时间")
    private LocalDateTime createTime;

    @Column(name = "updater")
    @Comment("更新者")
    private String updater;

    @Column(name = "update_time")
    @Comment("更新时间")
    private LocalDateTime updateTime;

    @Column(name = "remark")
    @Comment("备注")
    private String remark;
}
