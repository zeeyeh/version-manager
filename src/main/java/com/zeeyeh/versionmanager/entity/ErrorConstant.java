package com.zeeyeh.versionmanager.entity;

import lombok.Getter;

@Getter
public enum ErrorConstant {
    PROJECT_ALREADY_EXISTS(1001, "项目已存在"),
    PROJECT_NOT_FOUND(1002, "项目不存在"),
    VERSION_ALREADY_EXISTS(1003, "版本已存在"),
    VERSION_NOT_FOUND(1004, "版本不存在"),
    PROJECT_NAME_ALREADY_EXISTS(1005, "项目名称已存在"),
    PROJECT_NAME_NOT_FOUND(1006, "项目名称不存在"),
    PROJECT_AUTHOR_ALREADY_EXISTS(1007, "项目作者已存在"),
    PROJECT_AUTHOR_NOT_FOUND(1008, "项目作者不存在"),
    PROJECT_CREATE_FAILED(1010, "项目创建失败"),
    VERSION_RELEASE_FAILED(1012, "版本发布失败"),
    PARAMETER_MISMATCH(1013, "参数异常"),
    MEMBER_ALREADY_EXIST(1014, "用户已存在"),
    MEMBER_NOT_FOUND(1015, "用户不存在"),
    MEMBER_CREATE_FAILED(1016, "用户创建失败"),
    MEMBER_DELETE_FAILED(1017, "用户删除失败"),
    MEMBER_UPDATE_FAILED(1018, "用户更新失败"),
    MEMBER_NOT_REPEAT_LOGIN(1018, "不能重复登录"),
    MEMBER_USERNAME_NOT_NULL(1018, "用户名已被使用"),
    MEMBER_NICKNAME_NOT_NULL(1018, "昵称已被使用"),
    MEMBER_EMAIL_NOT_NULL(1018, "邮箱已被使用"),
    MENU_ALREADY_EXISTS(1019, "菜单已存在"),
    MENU_NOT_FOUND(1020, "菜单不存在"),
    MENU_CREATE_FAILED(1021, "菜单创建失败"),
    MENU_DELETE_FAILED(1022, "菜单删除失败"),
    MENU_UPDATE_FAILED(1023, "菜单更新失败"),
    ROLE_ALREADY_EXISTS(1024, "角色已存在"),
    ROLE_NOT_FOUND(1025, "角色不存在"),
    ROLE_CREATE_FAILED(1026, "角色创建失败"),
    ROLE_DELETE_FAILED(1027, "角色删除失败"),
    ROLE_UPDATE_FAILED(1028, "角色更新失败"),
    ;
    private final int code;
    private final String message;

    ErrorConstant(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
