package com.project.springapistudy.core;

import org.springframework.util.ObjectUtils;

public class VerifyUtils {
    public static void verifyEmptySource(Object source, String message) {
        if (ObjectUtils.isEmpty(source)) {
            throw new IllegalArgumentException(message);
        }
    }
}
