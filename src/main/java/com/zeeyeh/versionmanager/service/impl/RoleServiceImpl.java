package com.zeeyeh.versionmanager.service.impl;

import com.zeeyeh.versionmanager.entity.ErrorConstant;
import com.zeeyeh.versionmanager.entity.RequestHandlerException;
import com.zeeyeh.versionmanager.entity.Role;
import com.zeeyeh.versionmanager.repository.RoleRepository;
import com.zeeyeh.versionmanager.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Override
    public Role create(Long rid, String name, String title) {
        Role role = roleRepository.findByRid(rid);
        if (role != null) {
            throw new RequestHandlerException(ErrorConstant.ROLE_ALREADY_EXISTS);
        }
        return roleRepository.saveAndFlush(Role.builder()
                .rid(rid)
                .name(name)
                .title(title)
                .build());
    }

    @Override
    public Role delete(Long rid) {
        Role role = roleRepository.findByRid(rid);
        if (role == null) {
            throw new RequestHandlerException(ErrorConstant.ROLE_NOT_FOUND);
        }
        roleRepository.delete(Role.builder()
                .rid(rid)
                .build());
        roleRepository.flush();
        return role;
    }

    @Override
    public Role delete(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            throw new RequestHandlerException(ErrorConstant.ROLE_NOT_FOUND);
        }
        roleRepository.delete(Role.builder()
                .name(name)
                .build());
        roleRepository.flush();
        return role;
    }

    @Override
    public Role updateName(Long rid, String name) {
        Role role = roleRepository.findByRid(rid);
        if (role == null) {
            throw new RequestHandlerException(ErrorConstant.ROLE_NOT_FOUND);
        }
        role.setName(name);
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role updateTitle(Long rid, String title) {
        Role role = roleRepository.findByRid(rid);
        if (role == null) {
            throw new RequestHandlerException(ErrorConstant.ROLE_NOT_FOUND);
        }
        role.setTitle(title);
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role find(Long rid) {
        Role role = roleRepository.findByRid(rid);
        if (role == null) {
            throw new RequestHandlerException(ErrorConstant.ROLE_NOT_FOUND);
        }
        return role;
    }

    @Override
    public Role find(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            throw new RequestHandlerException(ErrorConstant.ROLE_NOT_FOUND);
        }
        return role;
    }

    @Override
    public Page<Role> findAll(Integer number, Integer size) {
        number = number <= 0 ? 0 : --number;
        return roleRepository.findAll(PageRequest.of(number, size));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
