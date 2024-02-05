package com.zeeyeh.versionmanager.service.impl;

import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson2.JSONArray;
import com.zeeyeh.versionmanager.entity.*;
import com.zeeyeh.versionmanager.repository.MemberRepository;
import com.zeeyeh.versionmanager.service.MemberService;
import com.zeeyeh.versionmanager.service.RoleService;
import com.zeeyeh.versionmanager.utils.MemberUtils;
import com.zeeyeh.versionmanager.utils.RedisFactory;
import com.zeeyeh.versionmanager.utils.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberRepository memberRepository;
    @Resource
    private RoleService roleService;
    @Resource
    RedisFactory redisFactory;
    @Value("${key.private}")
    private String privateKey;
    @Value("${key.public}")
    private String publicKey;

    @Override
    public Member create(Long uid, String username, String nickname, String password, String email, String avatar, String roles, String allowIps) {
        Member member = memberRepository.findByUsername(username);
        if (member != null) {
            throw new RequestHandlerException(ErrorConstant.MEMBER_USERNAME_NOT_NULL);
        }
        Member memberEmail = memberRepository.findByEmail(email);
        if (memberEmail != null) {
            throw new RequestHandlerException(ErrorConstant.MEMBER_EMAIL_NOT_NULL);
        }
        Member memberNickname = memberRepository.findByNickname(nickname);
        if (memberNickname != null) {
            throw new RequestHandlerException(ErrorConstant.MEMBER_NICKNAME_NOT_NULL);
        }
        String encodedPassword = MemberUtils.encode(password, privateKey);
        return memberRepository.saveAndFlush(Member.builder()
                .uid(uid)
                .username(username)
                .nickname(nickname)
                .password(encodedPassword)
                .email(email)
                .avatar(avatar)
                .roles(roles)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .status(MemberStatus.NORMAL.getCode())
                .allowIps(allowIps)
                .build());
    }

    @Override
    public Member loginUsername(String username, String password) {
        if (!StringUtils.checkUsername(username)) {
            return null;
        }
        Member member = find(username);
        return loginMember(password, member);
    }

    @Override
    public Member loginEmail(String email, String password) {
        if (!StringUtils.checkEmail(email)) {
            return null;
        }
        Member member = memberRepository.findByEmail(email);
        return loginMember(password, member);
    }

    protected Member loginMember(String password, Member member) {
        if (member == null) {
            throw new RequestHandlerException(ErrorConstant.MEMBER_NOT_FOUND);
        }
        if (Boolean.TRUE.equals(redisFactory.use(RedisDatabaseType.TOKEN).hasKey(String.valueOf(member.getUid())))) {
//            throw new RequestHandlerException(ErrorConstant.MEMBER_NOT_REPEAT_LOGIN);
            return Member.builder()
                    .uid(member.getUid())
                    .build();
        }
        String memberPassword = member.getPassword();
        if (MemberUtils.isPasswordsEquals(memberPassword, password, publicKey)) {
            return member;
        }
        return null;
    }

    @Override
    public Member delete(Long uid) {
        Member member = memberRepository.findByUid(uid);
        if (member == null) {
            throw new RequestHandlerException(ErrorConstant.MEMBER_NOT_FOUND);
        }
        memberRepository.delete(member);
        memberRepository.flush();
        return member;
    }

    @Override
    public Member delete(String username) {
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            throw new RequestHandlerException(ErrorConstant.MEMBER_NOT_FOUND);
        }
        memberRepository.delete(member);
        memberRepository.flush();
        return member;
    }

    @Override
    public Member update(Long uid, String username, String nickname, String password, String email, String avatar, String roles, String allowIps) {
        Member member = memberRepository.findByUid(uid);
        if (member == null) {
            throw new RequestHandlerException(ErrorConstant.MEMBER_NOT_FOUND);
        }
        return getNowMember(username, nickname, MemberUtils.encode(password, privateKey), email, avatar, roles, allowIps, member);
    }

    @Override
    public Member update(String oldUsername, String nowUsername, String nickname, String password, String email, String avatar, String roles, String allowIps) {
        Member member = memberRepository.findByUsername(oldUsername);
        if (member == null) {
            throw new RequestHandlerException(ErrorConstant.MEMBER_NOT_FOUND);
        }
        return getNowMember(nowUsername, nickname, MemberUtils.encode(password, privateKey), email, avatar, roles, allowIps, member);
    }

    protected Member getNowMember(String nowUsername, String nickname, String password, String email, String avatar, String roles, String allowIps, Member member) {
        if (nowUsername != null) {
            member.setUsername(nowUsername);
        }
        if (nickname != null) {
            member.setNickname(nickname);
        }
        if (password != null) {
            member.setPassword(password);
        }
        if (email != null) {
            member.setEmail(email);
        }
        if (avatar != null) {
            member.setAvatar(avatar);
        }
        if (roles != null) {
            member.setRoles(roles);
        }
        if (allowIps != null) {
            member.setAllowIps(allowIps);
        }
        return memberRepository.saveAndFlush(member);
    }

    @Override
    public Member addRole(Long uid, Long roleId) {
        Member member = find(uid);
        JSONArray roles = JSONArray.parseArray(member.getRoles());
        if (roles.contains(roleId)) {
            return member;
        }
        roles.add(roleId);
        member.setRoles(roles.toJSONString());
        return memberRepository.saveAndFlush(member);
    }

    @Override
    public Member addRole(Long uid, String roleName) {
        Role role = roleService.find(roleName);
        if (role == null) {
            throw new RequestHandlerException(ErrorConstant.ROLE_NOT_FOUND);
        }
        return addRole(uid, role.getRid());
    }

    @Override
    public Member addRole(String username, Long roleId) {
        Member member = find(username);
        JSONArray roles = JSONArray.parseArray(member.getRoles());
        if (roles.contains(roleId)) {
            return member;
        }
        roles.add(roleId);
        member.setRoles(roles.toJSONString());
        return memberRepository.saveAndFlush(member);
    }

    @Override
    public Member addRole(String username, String roleName) {
        Role role = roleService.find(roleName);
        if (role == null) {
            throw new RequestHandlerException(ErrorConstant.ROLE_NOT_FOUND);
        }
        return addRole(username, role.getRid());
    }

    @Override
    public Member deleteRole(Long uid, Long roleId) {
        Member member = find(uid);
        JSONArray roles = JSONArray.parseArray(member.getRoles());
        if (!roles.contains(roleId)) {
            return member;
        }
        roles.remove(roleId);
        member.setRoles(roles.toJSONString());
        return memberRepository.saveAndFlush(member);
    }

    @Override
    public Member deleteRole(Long uid, String roleName) {
        Role role = roleService.find(roleName);
        if (role == null) {
            throw new RequestHandlerException(ErrorConstant.ROLE_NOT_FOUND);
        }
        return deleteRole(uid, role.getRid());
    }

    @Override
    public Member deleteRole(String username, Long roleId) {
        Member member = find(username);
        JSONArray roles = JSONArray.parseArray(member.getRoles());
        if (!roles.contains(roleId)) {
            return member;
        }
        roles.remove(roleId);
        member.setRoles(roles.toJSONString());
        return memberRepository.saveAndFlush(member);
    }

    @Override
    public Member deleteRole(String username, String roleName) {
        Role role = roleService.find(roleName);
        if (role == null) {
            throw new RequestHandlerException(ErrorConstant.ROLE_NOT_FOUND);
        }
        return deleteRole(username, role.getRid());
    }

    @Override
    public Member addAllowIp(Long uid, String ip) {
        Member member = find(uid);
        JSONArray allowIps = JSONArray.parseArray(member.getAllowIps());
        if (allowIps.contains(ip)) {
            return member;
        }
        allowIps.add(ip);
        member.setAllowIps(allowIps.toJSONString());
        return memberRepository.saveAndFlush(member);
    }

    @Override
    public Member addAllowIp(String username, String ip) {
        return addAllowIp(find(username).getUid(), ip);
    }

    @Override
    public Member deleteAllowIp(Long uid, String ip) {
        Member member = find(uid);
        JSONArray allowIps = JSONArray.parseArray(member.getAllowIps());
        if (!allowIps.contains(ip)) {
            return member;
        }
        allowIps.remove(ip);
        member.setAllowIps(allowIps.toJSONString());
        return memberRepository.saveAndFlush(member);
    }

    @Override
    public Member deleteAllowIp(String username, String ip) {
        return deleteAllowIp(find(username).getUid(), ip);
    }

    @Override
    public Member find(Long uid) {
        Member member = memberRepository.findByUid(uid);
        if (member == null) {
            throw new RequestHandlerException(ErrorConstant.MEMBER_NOT_FOUND);
        }
        return member;
    }

    @Override
    public Member find(String username) {
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            throw new RequestHandlerException(ErrorConstant.MEMBER_NOT_FOUND);
        }
        return member;
    }

    @Override
    public Page<Member> findAll(Integer number, Integer size) {
        number = number <= 0 ? 0 : --number;
        return memberRepository.findAll(PageRequest.of(number, size));
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
