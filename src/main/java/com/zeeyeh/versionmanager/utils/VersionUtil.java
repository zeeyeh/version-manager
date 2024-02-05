package com.zeeyeh.versionmanager.utils;

import com.zeeyeh.versionmanager.entity.Version;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VersionUtil {

    public static void ascentSort(List<Version> versionEntities) {
        versionEntities.sort(Comparator.comparing(version -> getOsVersionValue(version.getName())));
    }

    public static void descentSort(List<Version> versionEntities) {
        ascentSort(versionEntities);
        Collections.reverse(versionEntities);
    }

    private static Integer getOsVersionValue(String osVersion) {
        if (ObjectUtils.isEmpty(osVersion)) {
            return 0;
        }
        String[] arr = osVersion.split("\\.");
        int value = 0;
        for (int i = 0, j = 2; i < arr.length; i++, j--) {
            double val = Math.pow(10, j);
            value += Integer.parseInt(arr[i]) * (int) val;
        }
        return value;
    }
}
