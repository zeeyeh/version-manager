package com.zeeyeh.versionmanager.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSONObject;
import com.zeeyeh.versionmanager.entity.ErrorConstant;
import com.zeeyeh.versionmanager.entity.Menu;
import com.zeeyeh.versionmanager.entity.RequestHandlerException;
import com.zeeyeh.versionmanager.entity.Response;
import com.zeeyeh.versionmanager.service.MenuService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单管理请求控制器
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    MenuService menuService;

    /**
     * 创建菜单
     * @param bodyString 请求体
     */
    @PostMapping("/create")
    private ResponseEntity<Response> create(@RequestBody String bodyString) {
        JSONObject jsonObject = JSONObject.parseObject(bodyString);
        Long mid = IdUtil.getSnowflakeNextId();
        if (!jsonObject.containsKey("name")) {
            return Response.forbidden();
        }
        String name = jsonObject.getString("name");
        if (!jsonObject.containsKey("parent")) {
            return Response.forbidden();
        }
        Long parent = jsonObject.getLong("parent");
        if (!jsonObject.containsKey("order")) {
            return Response.forbidden();
        }
        Integer order = jsonObject.getInteger("order");
        if (!jsonObject.containsKey("path")) {
            return Response.forbidden();
        }
        String path = jsonObject.getString("path");
        if (!jsonObject.containsKey("component")) {
            return Response.forbidden();
        }
        String component = jsonObject.getString("component");
        if (!jsonObject.containsKey("params")) {
            return Response.forbidden();
        }
        String params = jsonObject.getString("params");
        if (!jsonObject.containsKey("frame")) {
            return Response.forbidden();
        }
        Integer frame = jsonObject.getInteger("frame");
        if (!jsonObject.containsKey("cache")) {
            return Response.forbidden();
        }
        Integer cache = jsonObject.getInteger("cache");
        if (!jsonObject.containsKey("type")) {
            return Response.forbidden();
        }
        Integer type = jsonObject.getInteger("type");
        if (!jsonObject.containsKey("visible")) {
            return Response.forbidden();
        }
        Integer visible = jsonObject.getInteger("visible");
        if (!jsonObject.containsKey("status")) {
            return Response.forbidden();
        }
        Integer status = jsonObject.getInteger("status");
        if (!jsonObject.containsKey("permission")) {
            return Response.forbidden();
        }
        String permission = jsonObject.getString("permission");
        if (!jsonObject.containsKey("icon")) {
            return Response.forbidden();
        }
        String icon = jsonObject.getString("icon");
        if (!jsonObject.containsKey("creator")) {
            return Response.forbidden();
        }
        String creator = jsonObject.getString("creator");
        if (!jsonObject.containsKey("updater")) {
            return Response.forbidden();
        }
        String updater = jsonObject.getString("updater");
        if (!jsonObject.containsKey("remark")) {
            return Response.forbidden();
        }
        String remark = jsonObject.getString("remark");
        Menu menu = menuService.create(mid, name, parent, order, path, component, params, frame, cache, type, visible, status, permission, icon, creator, LocalDateTime.now(), updater, LocalDateTime.now(), remark);
        return Response.success(menu);
    }

    /**
     * 删除菜单
     * @param mid 菜单ID
     * @param name 菜单名称
     */
    @PostMapping("/delete")
    private ResponseEntity<Response> delete(@RequestParam("mid") Long mid,
                                            @RequestParam("name") String name) {
        if (mid == null && name == null) {
            throw new RequestHandlerException(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (mid != null) {
            Menu menu = menuService.delete(mid);
            return Response.success(menu);
        } else if (name != null) {
            Menu menu = menuService.delete(name);
            return Response.success(menu);
        }
        return Response.server_error();
    }

    /**
     * 更新菜单
     * @param mid 菜单ID
     * @param name 菜单名称
     * @param parent 父级菜单ID
     * @param order 排序
     * @param path 路径
     * @param component 组件
     * @param params 参数
     * @param frame 是否内嵌
     * @param cache 是否缓存
     * @param type 类型
     * @param visible 是否可见
     * @param status 状态
     * @param permission 权限
     * @param icon 图标
     * @param updater 更新人
     * @param remark 备注
     */
    @PostMapping("/update")
    private ResponseEntity<Response> update(
            @RequestParam("mid") Long mid,
            @RequestParam("name") String name,
            @RequestParam("parent") Long parent,
            @RequestParam("order") Integer order,
            @RequestParam("path") String path,
            @RequestParam("component") String component,
            @RequestParam("params") String params,
            @RequestParam("frame") Integer frame,
            @RequestParam("cache") Integer cache,
            @RequestParam("type") Integer type,
            @RequestParam("visible") Integer visible,
            @RequestParam("status") Integer status,
            @RequestParam("permission") String permission,
            @RequestParam("icon") String icon,
            @RequestParam("updater") String updater,
            @RequestParam("remark") String remark) {
        if (mid == null) {
            return Response.forbidden();
        }
        return Response.success(menuService.update(mid, name, parent, order, path, component, params, frame, cache, type, visible, status, permission, icon, updater, remark));
    }

    /**
     * 查询菜单
     * @param token 登录Token
     */
    @PostMapping("/queryList")
    private ResponseEntity<Response> queryList(@RequestHeader(value = "Authorization", required = false) String token) {
        if (StringUtils.isBlank(token)){
            return Response.forbidden();
        }
        JWT jwt = JWTUtil.parseToken(token);
        String uidString = String.valueOf(jwt.getPayload("uid"));
        if (StringUtils.isBlank(uidString)) {
            return Response.forbidden();
        }
        long uid = Long.parseLong(uidString);
        List<Menu> menus = menuService.findAll(uid);
        return Response.success(menus);
    }
}
