package com.wvkity.mybatis.code.make.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

public final class KeyUtil {
    private KeyUtil(){}
    
    private static final int MAX = 16;
    
    public static String generated(final LocalDateTime time) {
        final String value = Long.toString(time.toEpochSecond(ZoneOffset.of("+8")));
        final int length = value.length();
        if (length == MAX) {
            return value;
        } else if (length > MAX) {
            return value.substring(0, MAX - 1);
        }
        final int size = MAX - length;
        if (size == 1) {
            return value + "0";
        } else if (size == 2) {
            return value + "00";
        }
        return value + String.join("", Collections.nCopies(size, "0"));
    }
}
