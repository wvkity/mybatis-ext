package com.wvkity.mybatis.starter.example.utils;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtil {

    private RandomUtil() {
    }
    
    public static int nextInt(final int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }

    /**
     * 随机范围整数
     * @param min 最小数
     * @param max 最大数
     * @return 整数
     */
    public static int nextInt(final int min, final int max) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt((max - min + 1)) + min;
    }
}
