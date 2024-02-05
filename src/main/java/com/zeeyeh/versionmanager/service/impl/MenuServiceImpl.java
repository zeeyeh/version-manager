package com.zeeyeh.versionmanager.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.zeeyeh.versionmanager.entity.*;
import com.zeeyeh.versionmanager.repository.MenuRepository;
import com.zeeyeh.versionmanager.service.MemberService;
import com.zeeyeh.versionmanager.service.MenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;
    @Resource
    private MemberService memberService;
    @Resource
    private RoleServiceImpl roleService;

    @Override
    public Menu create(Long mid, String name, Long parent, Integer order, String path, String component, String params, Integer frame, Integer cache, Integer type, Integer visible, Integer status, String permission, String icon, String creator, LocalDateTime createTime, String updater, LocalDateTime updateTime, String remark) {
        Menu menu = menuRepository.findByMid(mid);
        if (menu != null) {
            throw new RequestHandlerException(ErrorConstant.MENU_ALREADY_EXISTS);
        }
        return menuRepository.saveAndFlush(Menu.builder()
                .mid(mid)
                .name(name)
                .parent(parent)
                .order(order)
                .path(path)
                .component(component)
                .params(params)
                .frame(frame)
                .cache(cache)
                .type(type)
                .visible(visible)
                .status(status)
                .permission(permission)
                .icon(icon)
                .creator(creator)
                .createTime(createTime)
                .updater(updater)
                .updateTime(updateTime)
                .remark(remark)
                .build());
    }

    @Override
    public Menu delete(Long mid) {
        Menu menu = menuRepository.findByMid(mid);
        if (menu == null) {
            throw new RequestHandlerException(ErrorConstant.MENU_NOT_FOUND);
        }
        menuRepository.delete(menu);
        menuRepository.flush();
        return menu;
    }

    @Override
    public Menu delete(String name) {
        Menu menu = menuRepository.findByName(name);
        if (menu == null) {
            throw new RequestHandlerException(ErrorConstant.MENU_NOT_FOUND);
        }
        menuRepository.delete(menu);
        menuRepository.flush();
        return menu;
    }

    @Override
    public Menu update(Long mid, String name, Long parent, Integer order, String path, String component, String params, Integer frame, Integer cache, Integer type, Integer visible, Integer status, String permission, String icon, String updater, String remark) {
        Menu menu = menuRepository.findByMid(mid);
        if (menu == null) {
            throw new RequestHandlerException(ErrorConstant.MENU_NOT_FOUND);
        }
        if (name != null) {
            menu.setName(name);
        }
        if (parent != null) {
            menu.setParent(parent);
        }
        if (order != null) {
            menu.setOrder(order);
        }
        if (path != null) {
            menu.setPath(path);
        }
        if (component != null) {
            menu.setComponent(component);
        }
        if (params != null) {
            menu.setParams(params);
        }
        if (frame != null) {
            menu.setFrame(frame);
        }
        if (cache != null) {
            menu.setCache(cache);
        }
        if (type != null) {
            menu.setType(type);
        }
        if (visible != null) {
            menu.setVisible(visible);
        }
        if (status != null) {
            menu.setStatus(status);
        }
        if (permission != null) {
            menu.setPermission(permission);
        }
        if (icon != null) {
            menu.setIcon(icon);
        }
        if (updater != null) {
            menu.setUpdater(updater);
        }
        if (remark != null) {
            menu.setRemark(remark);
        }
        return menuRepository.saveAndFlush(menu);
    }

    @Override
    public List<Menu> findAll(Long uid) {
        Member member = memberService.find(uid);
        List<Menu> menus = new ArrayList<>();
        JSONArray roles = JSONArray.parseArray(member.getRoles());
        for (Object role : roles) {
            menus.addAll(findAllByRoleId((Long) role));
        }
        return menus;
    }

    @Override
    public List<Menu> findAll(String username) {
        Member member = memberService.find(username);
        List<Menu> menus = new ArrayList<>();
        JSONArray roles = JSONArray.parseArray(member.getRoles());
        for (Object role : roles) {
            menus.addAll(findAllByRoleId((Long) role));
        }
        return menus;
    }

    @Override
    public List<Menu> findAllByRoleId(Long roleId) {
        List<Role> roles = roleService.findAll();
        Role targetRole = null;
        for (Role role : roles) {
            if (role.getRid().equals(roleId)) {
                targetRole = role;
            }
        }
        return getMenus(targetRole);
    }

    @Override
    public List<Menu> findAllByRoleName(String roleName) {
        List<Role> roles = roleService.findAll();
        Role targetRole = null;
        for (Role role : roles) {
            if (role.getName().equals(roleName)) {
                targetRole = role;
            }
        }
        return getMenus(targetRole);
    }

    protected List<Menu> getMenus(Role targetRole) {
        List<Menu> menus = menuRepository.findAll();
        List<Menu> targetMenus = new ArrayList<>();
        if (targetRole != null) {
            for (Menu menu : menus) {
                String permission = menu.getPermission();
                JSONArray permissions = JSONArray.parseArray(permission);
                if (permissions.contains(targetRole.getRid())) {
                    targetMenus.add(menu);
                }
            }
        }
        return targetMenus;
    }

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
