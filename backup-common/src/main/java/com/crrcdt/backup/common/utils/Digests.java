/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package com.crrcdt.backup.common.utils;

import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * <p>
 * 支持SHA-1/MD5消息摘要的工具类<br>
 * 返回ByteSource，可进一步被编码为Hex, Base64或UrlSafeBase64
 * </p>
 *
 * @author lyh
 * @date 2019年11月6日12:45:18
 */
public class Digests {

    private static final String SHA1 = "SHA-1";
    private static final String MD5 = "MD5";
    private static SecureRandom random = new SecureRandom();

    /**
     * <p>对输入字符串进行MD5散列</p>
     *
     * @param input input
     * @return byte[]
     */
    public static byte[] md5(byte[] input) {
        return digest(input, MD5, null, 1);
    }

    /**
     * <p>对输入字符串进行MD5散列</p>
     *
     * @param input      input
     * @param iterations
     * @return byte[]
     */
    public static byte[] md5(byte[] input, int iterations) {
        return digest(input, MD5, null, iterations);
    }

    /**
     * <p>对输入字符串进行SHA-1散列</p>
     *
     * @param input input
     * @return byte[]
     */
    public static byte[] sha1(byte[] input) {
        return digest(input, SHA1, null, 1);
    }

    /**
     * <p>对输入字符串进行SHA-1散列</p>
     *
     * @param input input
     * @param salt  salt
     * @return byte[]
     */
    public static byte[] sha1(byte[] input, byte[] salt) {
        return digest(input, SHA1, salt, 1);
    }

    /**
     * <p>对输入字符串进行SHA-1散列</p>
     *
     * @param input      input
     * @param salt       salt
     * @param iterations
     * @return byte[]
     */
    public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
        return digest(input, SHA1, salt, iterations);
    }

    /**
     * <p>对字符串进行散列,支持MD5与SHA-1算法</p>
     *
     * @param input      input
     * @param algorithm  algorithm
     * @param salt       salt
     * @param iterations iterations
     * @return byte[]
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>生成随机字节数组作为salt</p>
     *
     * @param numBytes byte数组的大小
     * @return byte[]
     */
    public static byte[] generateSalt(int numBytes) {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * <p> 对文件进行MD5散列</p>
     *
     * @param input
     * @return byte[]
     * @throws IOException
     */
    public static byte[] md5(InputStream input) throws IOException {
        return digest(input, MD5);
    }

    /**
     * <p>对文件进行SHA-1散列</p>
     *
     * @param input
     * @return byte[]
     * @throws IOException
     */
    public static byte[] sha1(InputStream input) throws IOException {
        return digest(input, SHA1);
    }

    private static byte[] digest(InputStream input, String algorithm) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8 * 1024;
            byte[] buffer = new byte[bufferLength];
            int read = input.read(buffer, 0, bufferLength);

            while (read > -1) {
                messageDigest.update(buffer, 0, read);
                read = input.read(buffer, 0, bufferLength);
            }

            return messageDigest.digest();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
