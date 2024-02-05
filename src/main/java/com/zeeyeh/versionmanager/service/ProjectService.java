package com.zeeyeh.versionmanager.service;

import com.zeeyeh.versionmanager.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProjectService {

    /**
     * 创建项目
     *
     * @param pid 项目pid
     * @return 项目信息
     */
    Project create(long pid, String name, List<String> authors, int status);

    /**
     * 删除项目
     *
     * @param pid 项目pid
     * @return 项目信息
     */
    Project delete(long pid);

    /**
     * 删除项目
     *
     * @param name 项目名称
     * @return 项目信息
     */
    Project delete(String name);

    /**
     * 添加作者
     *
     * @param projectName 项目名称
     * @param authorName  作者名称
     * @return 项目信息
     */
    Project addAuthor(String projectName, String authorName);

    /**
     * 添加作者
     *
     * @param pid        项目pid
     * @param authorName 作者名称
     * @return 项目信息
     */
    Project addAuthor(long pid, String authorName);

    /**
     * 删除作者
     *
     * @param projectName 项目名称
     * @param authorName  作者名称
     * @return 项目信息
     */
    Project deleteAuthor(String projectName, String authorName);

    /**
     * 删除作者
     *
     * @param pid        项目pid
     * @param authorName 作者名称
     * @return 项目信息
     */
    Project deleteAuthor(long pid, String authorName);

    /**
     * 持续维护
     *
     * @param pid 项目pid
     * @return 项目信息
     */
    Project active(long pid);

    /**
     * 持续维护
     *
     * @param name 项目名称
     * @return 项目信息
     */
    Project active(String name);

    /**
     * 暂停维护
     *
     * @param pid 项目pid
     * @return 项目信息
     */
    Project inactive(long pid);

    /**
     * 暂停维护
     *
     * @param name 项目名称
     * @return 项目信息
     */
    Project inactive(String name);

    /**
     * 抛弃项目
     *
     * @param pid 项目pid
     * @return 项目信息
     */
    Project deserted(long pid);

    /**
     * 抛弃项目
     *
     * @param name 项目名称
     * @return 项目信息
     */
    Project deserted(String name);

    /**
     * 分页查询项目
     *
     * @param number 页码
     * @param size   每页显示数量
     * @return 项目信息
     */
    Page<Project> page(int number, int size);

    /**
     * 查找项目
     *
     * @param pid 项目id
     * @return 项目信息
     */
    Project query(long pid);

    /**
     * 查找项目
     *
     * @param name 项目名称
     * @return 项目信息
     */
    Project query(String name);

    /**
     * 获取项目更新提示信息
     * @param pid 项目Id
     */
    String getInfos(long pid);

    /**
     * 获取项目更新提示信息
     * @param name 项目名称
     */
     String getInfos(String name);
}