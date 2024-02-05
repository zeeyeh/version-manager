package com.zeeyeh.versionmanager.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.zeeyeh.versionmanager.entity.ErrorConstant;
import com.zeeyeh.versionmanager.entity.Response;
import com.zeeyeh.versionmanager.entity.Version;
import com.zeeyeh.versionmanager.entity.VersionStatus;
import com.zeeyeh.versionmanager.service.VersionService;
import com.zeeyeh.versionmanager.utils.JsonUtil;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 版本管理请求控制器
 */
@RestController
@RequestMapping("/version")
public class VersionController {

    @Resource
    VersionService versionService;

    /**
     * 创建版本
     * @param bodyString 创建的版本信息
     * @return 版本信息
     */
    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestBody String bodyString) {
        JSONObject bodyJsonObject = JsonUtil.toJsonObject(bodyString);
        long vid = IdUtil.getSnowflakeNextId();
        long pid = 0;
        String name = "";
        String url = "";
        if (bodyJsonObject.containsKey("vid")) {
            vid = bodyJsonObject.getLongValue("vid");
        }
        if (bodyJsonObject.containsKey("pid")) {
            pid = bodyJsonObject.getLongValue("pid");
        }
        if (bodyJsonObject.containsKey("name")) {
            name = bodyJsonObject.getString("name");
        }
        if (bodyJsonObject.containsKey("url")) {
            url = bodyJsonObject.getString("url");
        }
        Version version = versionService.create(vid, pid, name, url, VersionStatus.ALLOWED);
        if (version == null) {
            Response.server_error(ErrorConstant.VERSION_RELEASE_FAILED);
        }
        return Response.success(version);
    }

    /**
     * 删除版本
     * @param vid 项目Id
     * @param name 版本名称
     * @return 版本信息
     */
    @PostMapping("/delete")
    public ResponseEntity<Response> delete(
            @RequestParam(value = "vid", required = false) Long vid,
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "name", required = false) String name) {
        if (vid == null && name == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (vid != null) {
            Version deleteVersion = versionService.delete(vid);
            if (deleteVersion == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(deleteVersion);
        } else if (name != null) {
            Version deleteVersion = versionService.delete(pid, name);
            if (deleteVersion == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(deleteVersion);
        }
        return Response.server_error();
    }

    /**
     * 获取最新版本号
     * @param pid 项目Id
     * @param name 版本名称
     * @return 版本信息
     */
    @PostMapping("/getLatest")
    public ResponseEntity<Response> getLatest(
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "name", required = false) String name) {
        if (pid == null && name == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (pid != null) {
            Version latestVersion = versionService.getLatest(pid);
            if (latestVersion == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(latestVersion);
        } else if (name != null) {
            Version latestVersion = versionService.getLatest(name);
            if (latestVersion == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(latestVersion);
        }
        return Response.server_error();
    }

    /**
     * 获取版本列表
     * @param number 页码 默认: 1
     * @param size 每页数量 默认: 15
     * @return 版本列表
     */
    @PostMapping("/list")
    public ResponseEntity<Response> list(
            @RequestParam(value = "number", required = false) Integer number,
            @RequestParam(value = "size", required = false) Integer size) {
        number = number == null ? 1 : number;
        size = size == null ? 15 : size;
        Page<Version> page = versionService.page(number, size);
        List<Version> content = page.getContent();
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

    /**
     * 查找版本
     * @param vid 版本Id
     * @param name 版本名称
     * @return 版本信息
     */
    @PostMapping("/query")
    public ResponseEntity<Response> query(
            @RequestParam(value = "vid", required = false) Long vid,
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "name", required = false) String name) {
        if (vid == null && name == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (vid != null) {
            Version version = versionService.query(vid);
            if (version == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(version);
        } else if (name != null) {
            Version version = versionService.query(pid, name);
            if (version == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(version);
        }
        return Response.server_error();
    }

    /**
     * 重命名该版本
     * @param vid 版本Id
     * @param oldName 旧版本名称
     * @param nowName 新版本名称
     * @return 版本信息
     */
    @PostMapping("/rename")
    public ResponseEntity<Response> rename(
            @RequestParam(value = "vid", required = false) Long vid,
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "oldName", required = false) String oldName,
            @RequestParam(value = "nowName") String nowName) {
        if (vid == null && oldName == null && nowName == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (vid != null) {
            Version version = versionService.rename(vid, nowName);
            if (version == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(version);
        } else if (oldName != null) {
            Version version = versionService.rename(pid, oldName, nowName);
            if (version == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(version);
        }
        return Response.server_error();
    }

    /**
     * 允许使用该版本
     * @param vid 版本Id
     * @param name 版本名称
     * @return 版本信息
     */
    @PostMapping("/allow")
    public ResponseEntity<Response> allow(
            @RequestParam(value = "vid", required = false) Long vid,
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "name", required = false) String name) {
        if (vid == null && name == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (vid != null) {
            Version version = versionService.allow(vid);
            if (version == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(version);
        } else if (name != null) {
            Version version = versionService.allow(pid, name);
            if (version == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(version);
        }
        return Response.server_error();
    }

    /**
     * 禁止使用该版本
     * @param vid 版本Id
     * @param name 版本名称
     * @return 版本信息
     */
    @PostMapping("/disallowed")
    public ResponseEntity<Response> disallowed(
            @RequestParam(value = "vid", required = false) Long vid,
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "name", required = false) String name) {
        if (vid == null && name == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (vid != null) {
            Version version = versionService.disallow(vid);
            if (version == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(version);
        } else if (name != null) {
            Version version = versionService.disallow(pid, name);
            if (version == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(version);
        }
        return Response.server_error();
    }

    /**
     * 获取下载地址
     * @param vid 版本id
     * @param name 版本名称
     * @return 下载地址
     */
    @GetMapping("/findDownload")
    public ResponseEntity<String> findDownload(@RequestParam(value = "vid", required = false) Long vid, @RequestParam(value = "pid", required = false) Long pid, @RequestParam(value = "name", required = false) String name) {
        if (vid != null) {
            String url = versionService.findUrl(vid);
            if (url == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(url);
        } else if (name != null) {
            String url = versionService.findUrl(pid, name);
            if (url == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(url);
        }
        return null;
    }

    /**
     * 更新下载地址
     * @param vid 版本Id
     * @param name 版本名称
     * @param url url地址
     * @return 版本信息
     */
    @PostMapping("/updateDownload")
    public ResponseEntity<Response> updateDownload(
            @RequestParam(value = "vid", required = false) Long vid,
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "url") String url) {
        if (vid == null && pid == null && name == null && url == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (vid != null) {
            Version version = versionService.updateUrl(vid, url);
            if (version == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(version);
        } else if (name != null) {
            Version version = versionService.updateUrl(pid, name, url);
            if (version == null) {
                return Response.error(ErrorConstant.VERSION_NOT_FOUND);
            }
            return Response.success(version);
        }
        return Response.server_error();
    }
}
