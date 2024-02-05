package com.zeeyeh.versionmanager.entity;

import lombok.Getter;

@Getter
public enum RedisDatabaseType {
    TOKEN("tokenRedis");

    private final String value;

    RedisDatabaseType(String value) {
        this.value = value;
    }

}
