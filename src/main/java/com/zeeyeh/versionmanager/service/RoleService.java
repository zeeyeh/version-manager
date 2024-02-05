package com.zeeyeh.versionmanager.service;

import com.zeeyeh.versionmanager.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleService {

    /**
     * 创建角色
     * @param rid 角色Id
     * @param name 角色名称
     * @param title 角色标题
     */
    Role create(Long rid, String name, String title);

    /**
     * 删除角色
     * @param rid 角色Id
     */
    Role delete(Long rid);

    /**
     * 删除角色
     * @param name 角色名称
     */
    Role delete(String name);

    /**
     * 更新角色名称
     * @param rid 角色Id
     * @param name 角色名称
     */
    Role updateName(Long rid, String name);

    /**
     * 更新角色标题
     * @param rid 角色id
     * @param title 角色标题
     */
    Role updateTitle(Long rid, String title);

    /**
     * 更新角色名称
     * @param rid 角色Id
     */
    Role find(Long rid);

    /**
     * 更新角色名称
     * @param name 角色名称
     */
    Role find(String name);

    /**
     * 更新角色名称
     */
    Page<Role> findAll(Integer number, Integer size);

    /**
     * 更新角色名称
     */
    List<Role> findAll();
}
