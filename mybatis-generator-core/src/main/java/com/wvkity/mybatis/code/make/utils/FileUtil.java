package com.wvkity.mybatis.code.make.utils;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件工具
 * @author wvkity
 */
@Log4j2
public final class FileUtil {
    private FileUtil() {
    }

    /**
     * 文件路径分隔符
     */
    public static final String FILE_SEPARATOR = File.separator;
    /**
     * 标识读取到文件尾部
     */
    public static final int EOF = -1;

    /**
     * 检查文件路径是文件夹还是文件
     * @param filePath 文件路径
     * @return boolean
     */
    public static boolean isDir(final String filePath) {
        return StringUtil.isNotEmpty(filePath) && isDir(new File(filePath));
    }

    /**
     * 检查文件是文件夹还是文件
     * @param file 文件
     * @return boolean
     */
    public static boolean isDir(final File file) {
        return exists(file) && file.isDirectory();
    }

    /**
     * 检查文件是否存在
     * @param filePath 文件路径
     * @return boolean
     */
    public static boolean exists(final String filePath) {
        return StringUtil.isNotEmpty(filePath) && exists(new File(filePath));
    }

    /**
     * 检查文件是否存在
     * @param file 文件
     * @return boolean
     */
    public static boolean exists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 创建文件夹
     * @param filePath 文件路径
     * @return boolean
     */
    public static boolean mkdir(final String filePath) {
        return StringUtil.isNotEmpty(filePath) && mkdir(new File(filePath));
    }

    /**
     * 创建文件夹
     * @param file 文件
     * @return boolean
     */
    public static boolean mkdir(final File file) {
        if (!exists(file)) {
            String absolutePath = null;
            try {
                if (file.isFile()) {
                    absolutePath = file.getAbsolutePath();
                    final File parentFile = file.getParentFile();
                    if (!exists(parentFile)) {
                        return parentFile.mkdirs();
                    }
                } else {
                    absolutePath = file.getPath();
                    return file.mkdirs();
                }
            } catch (Exception e) {
                log.error("`{}` Folder creation failed: {}", absolutePath, e.getMessage(), e);
            }
        }
        return false;
    }

    /**
     * 创建文件
     * <p>文件不存在则从源文件中加载内容</p>
     * @param filePath   目标文件路径
     * @param sourcePath 源文件 路径
     * @return boolean
     */
    public static boolean touch(final String filePath, final String sourcePath) {
        return StringUtil.isNotEmpty(filePath) && touch(new File(filePath), new File(sourcePath));
    }

    /**
     * 创建文件
     * <p>文件不存在则从源文件中加载内容</p>
     * @param file   目标文件
     * @param source 源文件
     * @return boolean
     */
    public static boolean touch(final File file, final File source) {
        try {
            if (!exists(file)) {
                mkdir(file.getParent());
                return copy(source, file);
            }
        } catch (Exception e) {
            log.error("`{}` file creation failed: {}", file.getName(), e.getMessage(), e);
        }
        return false;
    }

    /**
     * 创建文件
     * <p>文件不存在则从源文件中加载内容</p>
     * @param file        目标文件
     * @param inputStream 源文件流
     * @return boolean
     */
    public static boolean touch(final File file, final InputStream inputStream) {
        try {
            if (!exists(file)) {
                mkdir(file.getParent());
                return copy(inputStream, file);
            }
        } catch (Exception e) {
            log.error("`{}` file creation failed: {}", file.getName(), e.getMessage(), e);
        }
        return false;
    }

    /**
     * 复制文件
     * @param source 源文件
     * @param target 目标文件
     * @return boolean
     */
    public static boolean copy(final File source, final File target) {
        if (source != null && target != null) {
            try (final InputStream is = new FileInputStream(source);
                 final BufferedInputStream bis = new BufferedInputStream(is);
                 final OutputStream os = new FileOutputStream(target);
                 final BufferedOutputStream bos = new BufferedOutputStream(os)) {
                final int readLength = 4096;
                final byte[] buffer = new byte[readLength];
                int read;
                while (EOF != (read = bis.read(buffer, 0, readLength))) {
                    bos.write(buffer, 0, read);
                }
                return true;
            } catch (Exception e) {
                log.error("File copy failed: {}", e.getMessage(), e);
            }
        }
        return false;
    }

    /**
     * 复制文件
     * @param inputStream 源文件流
     * @param target      目标文件
     * @return boolean
     */
    public static boolean copy(final InputStream inputStream, final File target) {
        if (inputStream != null && target != null) {
            try (final BufferedInputStream bis = new BufferedInputStream(inputStream);
                 final OutputStream os = new FileOutputStream(target);
                 final BufferedOutputStream bos = new BufferedOutputStream(os)) {
                final int readLength = 4096;
                final byte[] buffer = new byte[readLength];
                int read;
                while (EOF != (read = bis.read(buffer, 0, readLength))) {
                    bos.write(buffer, 0, read);
                }
                return true;
            } catch (Exception e) {
                log.error("File copy failed: {}", e.getMessage(), e);
            }
        }
        return false;
    }
}
