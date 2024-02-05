package com.zeeyeh.versionmanager.controller;

import cn.hutool.core.util.IdUtil;
import com.zeeyeh.versionmanager.entity.Response;
import com.zeeyeh.versionmanager.entity.Role;
import com.zeeyeh.versionmanager.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理请求控制器
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    RoleService roleService;

    /**
     * 创建角色
     * @param rid 角色Id
     * @param name 角色名称
     * @param title 角色标题
     */
    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestParam(value = "rid", required = false) Long rid, @RequestParam("name") String name, @RequestParam("title") String title) {
        if (rid == null) {
            rid = IdUtil.getSnowflakeNextId();
        }
        Role role = roleService.create(rid, name, title);
        return Response.success(role);
    }

    /**
     * 删除角色
     * @param rid 角色Id
     * @param name 角色名称
     */
    @PostMapping("/delete")
    public ResponseEntity<Response> delete(@RequestParam(value = "rid", required = false) Long rid, @RequestParam(value = "name", required = false) String name) {
        Role role;
        if (rid != null) {
            role = roleService.delete(rid);
        } else if (name != null) {
            role = roleService.delete(name);
        } else {
            return Response.server_error();
        }
        return Response.success(role);
    }

    /**
     * 更新角色名称
     * @param rid 角色Id
     * @param name 角色名称
     */
    @PostMapping("/updateName")
    public ResponseEntity<Response> updateName(@RequestParam("rid") Long rid, @RequestParam("name") String name) {
        Role role = roleService.updateName(rid, name);
        return Response.success(role);
    }

    /**
     * 更新角色标题
     * @param rid 角色id
     * @param title 角色标题
     */
    @PostMapping("/updateTitle")
    public ResponseEntity<Response> updateTitle(@RequestParam("rid") Long rid, @RequestParam("title") String title) {
        Role role = roleService.updateTitle(rid, title);
        return Response.success(role);
    }

    /**
     * 查询角色
     * @param rid 角色id
     * @param name 角色名称
     */
    @PostMapping("/query")
    public ResponseEntity<Response> find(@RequestParam(value = "rid", required = false) Long rid, @RequestParam(value = "name", required = false) String name) {
        Role role;
        if (rid != null) {
            role = roleService.find(rid);
        } else if (name != null) {
            role = roleService.find(name);
        } else {
            return Response.server_error();
        }
        return Response.success(role);
    }

    /**
     * 查询所有角色
     * @param number 页码
     * @param size 每页数量
     */
    @PostMapping("/queryAll")
    public ResponseEntity<Response> findAll(@RequestParam(value = "number", required = false) Integer number, @RequestParam(value = "size", required = false) Integer size) {
        number = number == null ? 1 : number;
        size = size == null ? 15 : size;
        return Response.success(roleService.findAll(number, size));
    }
}
