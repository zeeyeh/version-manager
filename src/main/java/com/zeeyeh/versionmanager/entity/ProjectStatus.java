package com.zeeyeh.versionmanager.entity;

/**
 * 项目状态
 */
public class ProjectStatus {
    /**
     * 持续维护
     */
    public static final int ACTIVE = 0;

    /**
     * 不再维护，但仍可使用
     */
    public static final int INACTIVE = 1;
    /**
     * 已抛弃
     */
    public static final int DESERTED = 2;
}
