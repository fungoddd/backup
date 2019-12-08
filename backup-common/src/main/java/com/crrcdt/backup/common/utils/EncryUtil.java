package com.crrcdt.backup.common.utils;

/**
 * <p>加密验证工具类</p>
 *
 * @author lyh
 * @date 2019年11月6日12:50:34
 */
public class EncryUtil {

    private static final String HASH_ALGORITHM = "SHA-1";
    private static final int HASH_INTEGRATIONS = 1024;
    private static final int SALT_SIZE = 8;

    /**
     * <p>生成安全的密码，生成随机的16位salt并经过1024次 SHA-1 Hash</p>
     *
     * @param plainPassword 明文密码
     * @return 加密后的密码
     */
    public static String entryptPassword(String plainPassword) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTEGRATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    /**
     * <p>验证密码</p>
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTEGRATIONS);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

    public static void main(String[] args) throws Exception {
        String encrypt = entryptPassword("admin");
        System.out.println("密文：" + encrypt);
    }

}
