package com.zeeyeh.versionmanager.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.zeeyeh.versionmanager.annotation.FilterPassword;
import com.zeeyeh.versionmanager.entity.*;
import com.zeeyeh.versionmanager.service.MemberService;
import com.zeeyeh.versionmanager.utils.JsonUtil;
import com.zeeyeh.versionmanager.utils.MemberUtils;
import com.zeeyeh.versionmanager.utils.RedisFactory;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户管理请求控制器
 */
@RestController
@RequestMapping("/member")
@FilterPassword
public class MemberController {

    @Resource
    MemberService memberService;
    @Resource
    RedisFactory redisFactory;
    @Value("${token.key:123456}")
    private String tokenKey;

    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestBody String bodyString) {
        JSONObject jsonObject = JSONObject.parseObject(bodyString);
        long uid = IdUtil.getSnowflakeNextId();
        if (jsonObject.containsKey("uid")) {
            uid = jsonObject.getLongValue("uid");
        }
        if (!jsonObject.containsKey("username")) {
            return Response.forbidden();
        }
        String username = jsonObject.getString("username");
        if (!jsonObject.containsKey("nickname")) {
            return Response.forbidden();
        }
        String nickname = jsonObject.getString("nickname");
        if (!jsonObject.containsKey("password")) {
            return Response.forbidden();
        }
        String password = jsonObject.getString("password");
        if (!jsonObject.containsKey("email")) {
            return Response.forbidden();
        }
        String email = jsonObject.getString("email");
        String avatar = "";
        if (jsonObject.containsKey("avatar")) {
            avatar = jsonObject.getString("avatar");
        }
        String roles = new JSONArray().toJSONString();
        if (jsonObject.containsKey("roles")) {
            roles = jsonObject.getJSONArray("roles").toJSONString();
        }
        String ips = new JSONArray().toJSONString();
        if (jsonObject.containsKey("ips")) {
            ips = jsonObject.getJSONArray("ips").toJSONString();
        }
        Member member = memberService.create(uid, username, nickname, password, email, avatar, roles, ips);
        return Response.success(member);
    }

    @PostMapping("/register")
    @FilterPassword
    public ResponseEntity<Response> register(@RequestBody String bodyString) {
        JSONObject jsonObject = JSONObject.parseObject(bodyString);
        long uid = IdUtil.getSnowflakeNextId();
        if (jsonObject.containsKey("uid")) {
            uid = jsonObject.getLongValue("uid");
        }
        if (!jsonObject.containsKey("username")) {
            return Response.forbidden();
        }
        String username = jsonObject.getString("username");
        if (!jsonObject.containsKey("nickname")) {
            return Response.forbidden();
        }
        String nickname = jsonObject.getString("nickname");
        if (!jsonObject.containsKey("password")) {
            return Response.forbidden();
        }
        String password = jsonObject.getString("password");
        if (!jsonObject.containsKey("email")) {
            return Response.forbidden();
        }
        String email = jsonObject.getString("email");
        String avatar = "";
        if (jsonObject.containsKey("avatar")) {
            avatar = jsonObject.getString("avatar");
        }
        String roles = new JSONArray().toJSONString();
        if (jsonObject.containsKey("roles")) {
            roles = jsonObject.getJSONArray("roles").toJSONString();
        }
        Member member = memberService.create(uid, username, nickname, password, email, avatar, roles, "[]");
        HttpHeaders httpHeaders = new HttpHeaders();

        String token = JWTUtil.createToken(new HashMap<>() {
            {
//                put("issued_at", LocalDateTime.now());
//                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 30);
//                put("not_before", LocalDateTime.now());
                put(JWTPayload.ISSUED_AT, LocalDateTime.now());
                put(JWTPayload.EXPIRES_AT, System.currentTimeMillis() + 1000 * 60 * 30);
                put(JWTPayload.NOT_BEFORE, LocalDateTime.now());
                put("eventId", UUID.fastUUID().toString());
                put("uid", String.valueOf(member.getUid()));
                put("username", member.getUsername());
                put("nickname", member.getNickname());
                put("email", member.getEmail());
                put("status", member.getStatus());
            }
        }, tokenKey.getBytes(StandardCharsets.UTF_8));
        redisFactory.use(RedisDatabaseType.TOKEN).opsForValue().set(String.valueOf(member.getUid()), token, 1000 * 60 * 30, TimeUnit.MILLISECONDS);
        httpHeaders.add("Authorization", token);
        return Response.success(member, httpHeaders);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam("password") String password) {
        if (username == null && email == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        Member member;
        if (username != null) {
            member = memberService.loginUsername(username, password);
        } else if (email != null) {
            member = memberService.loginEmail(email, password);
        } else {
            return Response.server_error();
        }
        if (member == null) {
            return Response.server_error();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = "";
        if (member.getUsername() != null) {
            token = JWTUtil.createToken(new HashMap<>() {
                {
                    put(JWTPayload.ISSUED_AT, LocalDateTime.now());
                    put(JWTPayload.EXPIRES_AT, System.currentTimeMillis() + 1000 * 60 * 30);
                    put(JWTPayload.NOT_BEFORE, LocalDateTime.now());
                    put("eventId", UUID.fastUUID().toString());
                    put("uid", String.valueOf(member.getUid()));
                    put("username", member.getUsername());
                    put("nickname", member.getNickname());
                    put("email", member.getEmail());
                    put("status", member.getStatus());
                }
            }, tokenKey.getBytes(StandardCharsets.UTF_8));
            redisFactory.use(RedisDatabaseType.TOKEN).opsForValue().set(String.valueOf(member.getUid()), token, 1000 * 60 * 30, TimeUnit.MILLISECONDS);
        } else {
            token = (String) redisFactory.use(RedisDatabaseType.TOKEN).opsForValue().get(String.valueOf(member.getUid()));
        }
        httpHeaders.add("Authorization", token);
        return Response.success(member, httpHeaders);
    }

    @PostMapping("/delete")
    public ResponseEntity<Response> delete(@RequestParam(value = "uid", required = false) Long uid, @RequestParam(value = "username", required = false) String username) {
        if (uid == null && username == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        Member member = null;
        if (uid != null) {
            member = memberService.delete(uid);
        } else if (username != null) {
            member = memberService.delete(username);
        }
        return Response.success(member);
    }

    @PostMapping("/update")
    public ResponseEntity<Response> update(
            @RequestParam(value = "uid", required = false) Long uid,
            @RequestParam(value = "oldUsername", required = false) String oldUsername,
            @RequestParam(value = "nowUsername", required = false) String nowUsername,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "avatar", required = false) String avatar,
            @RequestParam(value = "roles", required = false) String roles,
            @RequestParam(value = "ips", required = false) String ipsBase
    ) {
        if (uid == null && oldUsername == null && nowUsername == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (StringUtils.isNotBlank(ipsBase)) {
            String ips = Base64.decodeStr(ipsBase);
            try {
                JsonUtil.toJsonArray(ips);
                ipsBase = ips;
            } catch (Exception e) {
                ipsBase = null;
            }
        }
        Member member;
        if (uid != null) {
            member = memberService.update(uid, username, nickname, password, email, avatar, roles, ipsBase);
        } else if (oldUsername != null && nowUsername != null) {
            member = memberService.update(oldUsername, nowUsername, nickname, password, email, avatar, roles, ipsBase);
        } else {
            return Response.server_error();
        }
        return Response.success(member);
    }

    @PostMapping("/addRole")
    public ResponseEntity<Response> addRole(
            @RequestParam(value = "uid", required = false) Long uid,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "roleId", required = false) Long roleId,
            @RequestParam(value = "roleName", required = false) String roleName) {
        if (uid == null && username == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (roleId == null && roleName == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (uid != null) {
            if (roleId != null) {
                Member member = memberService.addRole(uid, roleId);
                return Response.success(member);
            } else if (roleName != null) {
                Member member = memberService.addRole(uid, roleName);
                return Response.success(member);
            } else {
                return Response.server_error();
            }
        } else if (username != null) {
            if (roleId != null) {
                Member member = memberService.addRole(username, roleId);
                return Response.success(member);
            } else if (roleName != null) {
                Member member = memberService.addRole(username, roleName);
                return Response.success(member);
            } else {
                return Response.server_error();
            }
        }
        return Response.server_error();
    }

    @PostMapping("/removeRole")
    public ResponseEntity<Response> removeRole(
            @RequestParam(value = "uid", required = false) Long uid,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "roleId", required = false) Long roleId,
            @RequestParam(value = "roleName", required = false) String roleName) {
        if (uid == null && username == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (roleId == null && roleName == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (uid != null) {
            if (roleId != null) {
                Member member = memberService.deleteRole(uid, roleId);
                return Response.success(member);
            } else if (roleName != null) {
                Member member = memberService.deleteRole(uid, roleName);
                return Response.success(member);
            } else {
                return Response.server_error();
            }
        } else if (username != null) {
            if (roleId != null) {
                Member member = memberService.deleteRole(username, roleId);
                return Response.success(member);
            } else if (roleName != null) {
                Member member = memberService.deleteRole(username, roleName);
                return Response.success(member);
            } else {
                return Response.server_error();
            }
        }
        return Response.server_error();
    }

    @PostMapping("/addAllowIp")
    public ResponseEntity<Response> addAllowIp(
            @RequestParam(value = "uid", required = false) Long uid,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "ip", required = false) String ip) {
        if (uid == null && username == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (ip == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        ip = Base64.decodeStr(ip);
        if (uid != null) {
            if (ip != null) {
                Member member = memberService.addAllowIp(uid, ip);
                return Response.success(member);
            } else {
                return Response.server_error();
            }
        } else if (username != null) {
            if (ip != null) {
                Member member = memberService.addAllowIp(username, ip);
                return Response.success(member);
            } else {
                return Response.server_error();
            }
        }
        return Response.server_error();
    }

    @PostMapping("/deleteAllowIp")
    public ResponseEntity<Response> deleteAllowIp(
            @RequestParam(value = "uid", required = false) Long uid,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "ip", required = false) String ip) {
        if (uid == null && username == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (ip == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        ip = Base64.decodeStr(ip);
        if (uid != null) {
            if (ip != null) {
                Member member = memberService.deleteAllowIp(uid, ip);
                return Response.success(member);
            } else {
                return Response.server_error();
            }
        } else if (username != null) {
            if (ip != null) {
                Member member = memberService.deleteAllowIp(username, ip);
                return Response.success(member);
            } else {
                return Response.server_error();
            }
        }
        return Response.server_error();
    }

    @PostMapping("/queryLogged")
    public ResponseEntity<Response> find(@RequestHeader("Authorization") String token) {
        if (token == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        JWT jwt = JWTUtil.parseToken(token);
        Object uid1 = jwt.getPayload("uid");
        Long uid = Long.parseLong(String.valueOf(uid1));
        if (uid == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        Member member = memberService.find(uid);
        if (member == null) {
            throw new RequestHandlerException(ErrorConstant.MEMBER_NOT_FOUND);
        }
        return Response.success(member);
//        redisFactory.use(RedisDatabaseType.TOKEN).hasKey()
    }

    @PostMapping("/query")
    public ResponseEntity<Response> find(@RequestParam(value = "uid", required = false) Long uid, @RequestParam(value = "username", required = false) String username) {
        if (uid == null && username == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        Member member = null;
        if (uid != null) {
            member = memberService.find(uid);
        } else if (username != null) {
            member = memberService.find(username);
        } else {
            return Response.server_error();
        }
        return Response.success(member);
    }

    @PostMapping("/queryList")
    public ResponseEntity<Response> queryList(@RequestParam(value = "number", required = false) Integer number, @RequestParam(value = "size", required = false) Integer size) {
        number = number == null ? 1 : number;
        size = size == null ? 15 : size;
        Page<Member> page = memberService.findAll(number, size);
        List<Member> content = page.getContent();
        if (content.isEmpty()) {
            return Response.error(ErrorConstant.VERSION_NOT_FOUND);
        }
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        return Response.success(new JSONObject()
                .fluentPut("pages", totalPages)
                .fluentPut("number", number)
                .fluentPut("totals", totalElements)
                .fluentPut("list", content));
    }
}
