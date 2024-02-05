package com.zeeyeh.versionmanager.service;

import com.zeeyeh.versionmanager.entity.Menu;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface MenuService {

    /**
     * 创建菜单
     * @param mid 菜单Id
     * @param name 菜单名称
     * @param parent 父级菜单Id
     * @param order 显示顺序
     * @param path  菜单路由
     * @param component 菜单组件
     * @param params 菜单参数
     * @param frame 是否外链
     * @param cache 是否缓存
     * @param type 菜单类型(0目录 1菜单 2按钮)
     * @param visible 是否可见
     * @param status 菜单状态(0正常 1停用)
     * @param permission 权限标识
     * @param icon 菜单图标
     * @param creator 创建者
     * @param createTime 创建时间
     * @param updater 更新者
     * @param updateTime 更新时间
     * @param remark 备注
     */
    Menu create(Long mid, String name, Long parent, Integer order, String path, String component, String params, Integer frame, Integer cache, Integer type, Integer visible, Integer status, String permission, String icon, String creator, LocalDateTime createTime, String updater, LocalDateTime updateTime, String remark);

    /**
     * 删除菜单
     * @param mid 菜单id
     */
    Menu delete(Long mid);

    /**
     * 删除菜单
     * @param name 菜单名称
     */
    Menu delete(String name);

    /**
     * 创建菜单
     * @param mid 菜单Id
     * @param name 菜单名称
     * @param parent 父级菜单Id
     * @param order 显示顺序
     * @param path  菜单路由
     * @param component 菜单组件
     * @param params 菜单参数
     * @param frame 是否外链
     * @param cache 是否缓存
     * @param type 菜单类型(0目录 1菜单 2按钮)
     * @param visible 是否可见
     * @param status 菜单状态(0正常 1停用)
     * @param permission 权限标识
     * @param icon 菜单图标
     * @param updater 更新者
     * @param remark 备注
     */
    Menu update(Long mid, String name, Long parent, Integer order, String path, String component, String params, Integer frame, Integer cache, Integer type, Integer visible, Integer status, String permission, String icon, String updater, String remark);

    /**
     * 创建菜单
     * @param uid 用户Id
     */
    List<Menu> findAll(Long uid);

    /**
     * 根据用户名查询菜单
     * @param username 用户名
     */
    List<Menu> findAll(String username);

    /**
     * 根据角色Id查询菜单
     * @param roleId 角色Id
     */
    List<Menu> findAllByRoleId(Long roleId);

    /**
     * 根据角色名称查询菜单
     * @param roleName 角色名称
     */
    List<Menu> findAllByRoleName(String roleName);

    /**
     * 查询所有菜单
     */
    List<Menu> findAll();
}
