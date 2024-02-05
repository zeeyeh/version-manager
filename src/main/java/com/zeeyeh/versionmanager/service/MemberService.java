package com.zeeyeh.versionmanager.service;

import com.zeeyeh.versionmanager.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MemberService {

    /**
     * 创建用户
     * @param uid 用户Id
     * @param username 用户名
     * @param nickname 用户昵称
     * @param password 用户密码
     * @param email 用户邮箱
     * @param avatar 用户头像
     * @param roles 用户继承的所有角色
     * @param allowIps 同意运行ip
     */
    Member create(Long uid, String username, String nickname, String password, String email, String avatar, String roles, String allowIps);

    /**
     * 使用用户名登录
     * @param username 用户名
     * @param password 密码
     */
    Member loginUsername(String username, String password);

    /**
     * 使用邮箱地址登录
     * @param email 邮箱地址
     * @param password 密码
     */
    Member loginEmail(String email, String password);

    /**
     * 删除用户
     * @param uid 用户id
     */
    Member delete(Long uid);

    /**
     * 删除用户
     * @param username 用户名
     */
    Member delete(String username);

    /**
     * 更新用户
     * @param uid 用户id
     * @param username 用户名
     * @param nickname 用户昵称
     * @param password 用户密码
     * @param email 用户邮箱
     * @param avatar 用户头像
     * @param roles 用户继承的所有角色
     */
    Member update(Long uid, String username, String nickname, String password, String email, String avatar, String roles, String allowIps);

    /**
     * 更新用户
     * @param oldUsername 旧用户名
     * @param nowUsername 新用户名
     * @param nickname 用户昵称
     * @param password 用户密码
     * @param email 邮箱地址
     * @param avatar 用户头像
     * @param roles 用户继承的所有角色
     */
    Member update(String oldUsername, String nowUsername, String nickname, String password, String email, String avatar, String roles, String allowIps);

    /**
     * 添加用户角色
     * @param uid 用户id
     * @param roleId 角色id
     */
    Member addRole(Long uid, Long roleId);

    /**
     * 添加用户角色
     * @param uid 用户Id
     * @param roleName 角色名
     */
    Member addRole(Long uid, String roleName);

    /**
     * 添加用户角色
     * @param username 用户名
     * @param roleId 角色Id
     */
    Member addRole(String username, Long roleId);

    /**
     * 添加用户角色
     * @param username 用户名
     * @param roleName 角色名
     */
    Member addRole(String username, String roleName);

    /**
     * 删除用户角色
     * @param uid 用户Id
     * @param roleId 角色Id
     */
    Member deleteRole(Long uid, Long roleId);

    /**
     * 删除用户角色
     * @param uid 用户Id
     * @param roleName 角色名
     */
    Member deleteRole(Long uid, String roleName);

    /**
     * 删除用户角色
     * @param username 用户名
     * @param roleId 角色Id
     */
    Member deleteRole(String username, Long roleId);

    /**
     * 删除用户角色
     * @param username 用户名
     * @param roleName 角色名
     */
    Member deleteRole(String username, String roleName);

    /**
     * 添加用户角色
     * @param uid 用户Id
     * @param ip ip地址
     */
    Member addAllowIp(Long uid, String ip);

    /**
     * 添加用户角色
     * @param username 用户名
     * @param ip ip地址
     */
    Member addAllowIp(String username, String ip);

    /**
     * 删除用户角色
     * @param uid 用户Id
     * @param ip ip地址
     */
    Member deleteAllowIp(Long uid, String ip);

    /**
     * 删除用户角色
     * @param username 用户名
     * @param ip ip地址
     */
    Member deleteAllowIp(String username, String ip);

    /**
     * 查找用户
     * @param uid 用户Id
     */
    Member find(Long uid);

    /**
     * 查过用户
     * @param username 用户名
     */
    Member find(String username);

    /**
     * 查找所有用户
     * @param number 页码
     * @param size 每页展示大小
     */
    Page<Member> findAll(Integer number, Integer size);

    /**
     * 查找所有用户
     */
    List<Member> findAll();
}
