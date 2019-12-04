package com.crrcdt.backup.common.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <p>
 * 封装各种格式的编码解码工具类.<br>
 * 1.Commons-Codec的hex/base64编码<br>
 * 2.自制的base62 编码<br>
 * 3.Commons-Lang的xml/html escape<br>
 * 4.JDK提供的URLEncoder
 * </p>
 *
 * @author lyh
 * @date 2019年11月6日12:44:21
 */
public class Encodes {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * <p>Hex编码</p>
     *
     * @param input input
     * @return String
     */
    public static String encodeHex(byte[] input) {
        return new String(Hex.encodeHex(input));
    }

    /**
     * <p>Hex解码</p>
     *
     * @param input input
     * @return byte[]
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Base64编码</p>
     *
     * @param input
     * @return String
     */
    public static String encodeBase64(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }


    /**
     * <p>Base64编码,默认UTF-8</p>
     *
     * @param input input
     * @return String
     */
    public static String encodeBase64(String input) {
        try {
            return new String(Base64.encodeBase64(input.getBytes(DEFAULT_URL_ENCODING)));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * <p>Base64解码</p>
     *
     * @param input input
     * @return byte[]
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input.getBytes());
    }

    /**
     * <p>Base64解码,默认UTF-8</p>
     *
     * @param input input
     * @return String
     */
    public static String decodeBase64String(String input) {
        try {
            return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * <p>Base62编码</p>
     *
     * @param input input
     * @return String
     */
    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
        }
        return new String(chars);
    }

    /**
     * <p>html编码</p>
     *
     * @param html html
     * @return String
     */
    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    /**
     * <p>html解码</p>
     *
     * @param htmlEscaped
     * @return String
     */
    public static String unescapeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    /**
     * <p>xml编码</p>
     *
     * @param xml
     * @return String
     */
    public static String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml10(xml);
    }

    /**
     * <p>xml解码</p>
     *
     * @param xmlEscaped
     * @return String
     */
    public static String unescapeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * <p>URL编码,,Encode默认为UTF-8</p>
     *
     * @param part part
     * @return String
     */
    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>URL解码,Encode默认为UTF-8</p>
     *
     * @param part part
     * @return String
     */
    public static String urlDecode(String part) {
        try {
            return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
