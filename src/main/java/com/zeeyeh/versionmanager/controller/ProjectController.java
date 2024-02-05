package com.zeeyeh.versionmanager.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.zeeyeh.versionmanager.entity.ErrorConstant;
import com.zeeyeh.versionmanager.entity.Project;
import com.zeeyeh.versionmanager.entity.ProjectStatus;
import com.zeeyeh.versionmanager.entity.Response;
import com.zeeyeh.versionmanager.service.ProjectService;
import com.zeeyeh.versionmanager.utils.JsonUtil;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目管理请求控制器
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    ProjectService projectService;

    /**
     * 创建项目
     * @param bodyString 创建的项目信息
     */
    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestBody String bodyString) {
        JSONObject bodyJsonObject = JsonUtil.toJsonObject(bodyString);
        long pid = IdUtil.getSnowflakeNextId();
        String name = "";
        List<String> authors = JsonUtil.toStringList(new JSONArray());
        if (bodyJsonObject.containsKey("pid")) {
            pid = bodyJsonObject.getLongValue("pid");
        }
        if (bodyJsonObject.containsKey("name")) {
            name = bodyJsonObject.getString("name");
        }
        if (bodyJsonObject.containsKey("authors")) {
            authors = JsonUtil.toStringList(bodyJsonObject.getJSONArray("authors"));
        }
        Project project = projectService.create(pid, name, authors, ProjectStatus.ACTIVE);
        if (project == null) {
            Response.server_error(ErrorConstant.PROJECT_CREATE_FAILED);
        }
        return Response.success(project);
    }

    /**
     * 删除项目
     * @param pid 项目Id
     * @param name 项目名称
     */
    @PostMapping("/delete")
    public ResponseEntity<Response> delete(
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "name", required = false) String name) {
        if (pid == null && name == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (pid != null) {
            Project deleteProject = projectService.delete(pid);
            if (deleteProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(deleteProject);
        } else if (name != null) {
            Project deleteProject = projectService.delete(name);
            if (deleteProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(deleteProject);
        }
        return Response.server_error();
    }

    /**
     * 添加作者
     * @param pid 项目Id
     * @param name 项目名称
     * @param authorName 作者名称
     * @return 项目信息
     */
    @PostMapping("/addAuthor")
    public ResponseEntity<Response> addAuthor(
            @RequestParam(value = "pid") Long pid,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "authorName") String authorName) {
        if (pid == null && name == null && authorName == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (pid != null) {
            Project addAuthorProject = projectService.addAuthor(pid, authorName);
            if (addAuthorProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(addAuthorProject);
        } else if (name != null) {
            Project addAuthorProject = projectService.addAuthor(name, authorName);
            if (addAuthorProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(addAuthorProject);
        }
        return Response.server_error();
    }

    /**
     * 删除作者
     * @param pid 项目Id
     * @param name 项目名称
     * @param authorName 作者名称
     * @return 项目信息
     */
    @PostMapping("/deleteAuthor")
    public ResponseEntity<Response> deleteAuthor(
            @RequestParam(value = "pid") Long pid,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "authorName") String authorName) {
        if (pid == null && name == null && authorName == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (pid != null) {
            Project deleteAuthorProject = projectService.deleteAuthor(pid, authorName);
            if (deleteAuthorProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(deleteAuthorProject);
        } else if (name != null) {
            Project deleteAuthorProject = projectService.deleteAuthor(name, authorName);
            if (deleteAuthorProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(deleteAuthorProject);
        }
        return Response.server_error();
    }

    /**
     * 持续维护
     * @param pid 项目Id
     * @param name 项目名称
     */
    @PostMapping("/sustain")
    public ResponseEntity<Response> sustain(
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "name", required = false) String name) {
        if (pid == null && name == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (pid != null) {
            Project sustainProject = projectService.active(pid);
            if (sustainProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(sustainProject);
        } else if (name != null) {
            Project sustainProject = projectService.active(name);
            if (sustainProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(sustainProject);
        }
        return Response.server_error();
    }

    /**
     * 抛弃该项目
     * @return 项目信息
     */
    @PostMapping("/deserted")
    public ResponseEntity<Response> deserted(
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "name", required = false) String name) {
        if (pid == null && name == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (pid != null) {
            Project desertedProject = projectService.deserted(pid);
            if (desertedProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(desertedProject);
        } else if (name != null) {
            Project desertedProject = projectService.deserted(name);
            if (desertedProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(desertedProject);
        }
        return Response.server_error();
    }

    /**
     * 暂停维护
     * @param pid 项目Id
     * @param name 项目名称
     * @return 项目信息
     */
    @PostMapping("/negate")
    public ResponseEntity<Response> negate(
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "name", required = false) String name) {
        if (pid == null && name == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (pid != null) {
            Project negateProject = projectService.inactive(pid);
            if (negateProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(negateProject);
        } else if (name != null) {
            Project negateProject = projectService.inactive(name);
            if (negateProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(negateProject);
        }
        return Response.server_error();
    }

    /**
     * 获取项目列表
     * @param number 页码
     * @param size 每页数量
     * @return 项目列表
     */
    @PostMapping("/list")
    public ResponseEntity<Response> list(@RequestParam(value = "number", required = false) Integer number, @RequestParam(value = "size", required = false) Integer size) {
        number = number == null ? 1 : number;
        size = size == null ? 15 : size;
        Page<Project> page = projectService.page(number, size);
        List<Project> content = page.getContent();
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
     * 查找项目
     * @param pid 项目ID
     * @param name 项目名称
     * @return 项目信息
     */
    @PostMapping("/query")
    public ResponseEntity<Response> query(
            @RequestParam(value = "pid", required = false) Long pid,
            @RequestParam(value = "name", required = false) String name) {
        if (pid == null && name == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (pid != null) {
            Project queryProject = projectService.query(pid);
            if (queryProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(queryProject);
        } else if (name != null) {
            Project queryProject = projectService.query(name);
            if (queryProject == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(queryProject);
        }
        return Response.server_error();
    }

    /**
     * 获取项目更新提示信息
     * @param pid 项目Id
     * @param name 项目名称
     */
    @PostMapping("/getInfos")
    public ResponseEntity<Response> getInfos(
            @RequestParam(value = "pid", required = false) String pid,
            @RequestParam(value = "name", required = false) String name) {
        if (pid == null && name == null) {
            return Response.error(ErrorConstant.PARAMETER_MISMATCH);
        }
        if (pid != null) {
            String infos = projectService.getInfos(pid);
            if (infos == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(infos);
        } else if (name != null) {
            String infos = projectService.getInfos(name);
            if (infos == null) {
                return Response.error(ErrorConstant.PROJECT_NOT_FOUND);
            }
            return Response.success(infos);
        }
        return Response.server_error();
    }
}
