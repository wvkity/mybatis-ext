package com.wvkity.mybatis.code.make.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class AESEncrypt {

    /**
     * 默认偏移量
     */
    private static final String DEFAULT_IV = "DaqSDJvSHqBhtXtO";
    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHAR_SET = StandardCharsets.UTF_8;
    /**
     * 填充方式
     */
    private static final String DEFAULT_PADDING = "AES/CBC/PKCS5Padding";
    /**
     * 加密算法
     */
    private static final String DEFAULT_ALGORITHM = "AES";

    /**
     * 加解密操作
     * @param iv       偏移量字节数组
     * @param password 密码字节数组
     * @param mode     加密/解密
     * @param content  待加密/解密内容字节数组
     * @return 加密/解密后的字节数组
     * @throws Exception 异常信息
     */
    private static byte[] build(byte[] iv, byte[] password, final int mode, final byte[] content) throws Exception {
        Key key = new SecretKeySpec(password, DEFAULT_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(DEFAULT_PADDING);
        cipher.init(mode, key, ivSpec);
        return cipher.doFinal(content);
    }

    /**
     * 加密
     * @param password 密码
     * @param content  内容
     * @return 加密后的base64编码内容
     * @throws Exception 异常信息
     */
    public static String encrypt(final String password, final String content) throws Exception {
        return encrypt(DEFAULT_IV, password, content);
    }

    /**
     * 加密
     * @param iv       偏移量字符串
     * @param password 密码
     * @param content  内容
     * @return 加密后的base64编码内容
     * @throws Exception 异常信息
     */
    public static String encrypt(final String iv, final String password, final String content) throws Exception {
        return Base64.getEncoder().encodeToString(build(iv.getBytes(DEFAULT_CHAR_SET), password.getBytes(DEFAULT_CHAR_SET),
                Cipher.ENCRYPT_MODE, content.getBytes(DEFAULT_CHAR_SET)));
    }

    /**
     * 解密
     * @param password 密码
     * @param content  加密内容
     * @return 解密后的内容
     * @throws Exception 异常信息
     */
    public static String decrypt(final String password, final String content) throws Exception {
        return decrypt(DEFAULT_IV, password, content);
    }

    /**
     * 解密
     * @param iv       偏移量字符串
     * @param password 密码
     * @param content  加密内容
     * @return 解密后的内容
     * @throws Exception 异常信息
     */
    public static String decrypt(final String iv, final String password, final String content) throws Exception {
        return new String(build(iv.getBytes(DEFAULT_CHAR_SET), password.getBytes(DEFAULT_CHAR_SET),
                Cipher.DECRYPT_MODE, Base64.getDecoder().decode(content)));
    }
    
}
