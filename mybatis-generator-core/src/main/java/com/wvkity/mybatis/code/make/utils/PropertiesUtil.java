package com.wvkity.mybatis.code.make.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * properties文件工具
 * @author wvkity
 */
public final class PropertiesUtil {

    private PropertiesUtil() {
    }

    /**
     * 加载properties文件
     * @param file properties文件
     * @return 配置
     */
    public static Map<String, String> load(final File file) {
        try (final InputStream fs = new FileInputStream(file)) {
            return load(fs);
        } catch (Exception ignore) {
            // ignore
        }
        return new HashMap<>(0);
    }

    /**
     * 加载properties文件
     * @param inputStream properties文件流
     * @return 配置
     */
    public static Map<String, String> load(final InputStream inputStream) {
        return Optional.ofNullable(inputStream).map(it -> {
            final Map<String, String> map = new HashMap<>();
            try (final InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                final Properties props = new Properties();
                props.load(isr);
                props.stringPropertyNames().forEach(k ->
                        map.put(k, props.getProperty(k, "")));
            } catch (Exception ignore) {
                // ignore
            }
            return map;
        }).orElse(new HashMap<>(0));
    }
}
