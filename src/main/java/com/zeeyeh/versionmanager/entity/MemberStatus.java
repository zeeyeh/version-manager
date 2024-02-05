package com.zeeyeh.versionmanager.entity;

import lombok.Getter;

@Getter
public enum MemberStatus {
    NORMAL(0, "正常"),
    BANNED(1, "封禁"),
    ;

    private final int code;
    private final String desc;

    MemberStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MemberStatus getByCode(int code) {
        for (MemberStatus value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }

}
