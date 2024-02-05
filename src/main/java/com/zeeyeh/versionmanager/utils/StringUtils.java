package com.zeeyeh.versionmanager.utils;

import java.util.regex.Pattern;

public class StringUtils {
    public static final String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z0-9_]{4,}[a-zA-Z]$";
    public static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * 校验字符串是否为用户名
     * @param username 字符串
     */
    public static boolean checkUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * 校验字符串是否为邮箱
     * @param email 字符串
     */
    public static boolean checkEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
