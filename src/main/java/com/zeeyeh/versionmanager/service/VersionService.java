package com.zeeyeh.versionmanager.service;

import com.zeeyeh.versionmanager.entity.Version;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public interface VersionService {

    /**
     * 创建版本号
     * @param vid 版本vid
     * @param pid 项目pid
     * @param name 版本名称
     * @return 版本信息
     */
    Version create(long vid, long pid, String name, String url, int status);

    /**
     * 创建版本号
     * @param vid 版本vid
     * @return 版本信息
     */
    Version delete(long vid);

    /**
     * 删除版本号
     * @param name 版本名称
     * @return 版本信息
     */
    Version delete(long pid, String name);

    /**
     * 获取版本下载地址
     * @param vid 版本id
     * @return 下载地址
     */
    String findUrl(long vid);

    /**
     * 获取版本下载地址
     * @param name 版本名称
     * @return 下载地址
     */
    String findUrl(long pid, String name);

    /**
     * 更新版本下载地址
     * @param vid 版本id
     * @return 下载地址
     */
    Version updateUrl(long vid, String nowUrl);

    /**
     * 更新版本下载地址
     * @param name 版本名称
     * @return 下载地址
     */
    Version updateUrl(long pid, String name, String nowUrl);

    /**
     * 重命名版本号
     * @param vid 版本vid
     * @param nowName 心版本号
     * @return 版本信息
     */
    Version rename(long vid, String nowName);

    /**
     * 重命名版本号
     * @param oldName 旧版本号
     * @param nowName 新版本号
     * @return 版本信息
     */
    Version rename(long pid, String oldName, String nowName);

    /**
     * 允许使用
     * @param vid 版本vid
     * @return 版本信息
     */
    Version allow(long vid);

    /**
     * 允许使用
     * @param name 版本名称
     * @return 版本信息
     */
    Version allow(long pid, String name);

    /**
     * 禁止使用
     * @param vid 版本id
     * @return 版本信息
     */
    Version disallow(long vid);

    /**
     * 禁止使用
     * @param name 项目名称
     * @param name 版本名称
     * @return 版本信息
     */
    Version disallow(long pid, String name);

    /**
     * 获取最新版本号信息
     * @param pid 项目id
     * @return 版本信息
     */
    Version getLatest(long pid);

    /**
     * 获取最新版本号信息
     * @param name 项目名称
     * @return 版本信息
     */
    Version getLatest(String name);

    /**
     * 分页查找
     *
     * @param number 页码
     * @param size   每页数量
     */
    Page<Version> page(int number, int size);

    /**
     * 查找版本
     * @param vid 版本id
     * @return 版本信息
     */
    Version query(long vid);

    /**
     * 查找版本
     * @param name 版本名称
     * @return 版本信息
     */
    Version query(long pid, String name);
}
