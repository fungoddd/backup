package com.crrcdt.backup.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 文件备份工具类
 * </p>
 *
 * @author lyh
 * @date 2019年10月30日16:15:26
 */
public class BackupUtil {

    private static final Logger logger = LoggerFactory.getLogger(BackupUtil.class);


    /**
     * 与服务器建立SSH连接
     *
     * @param user     登录用户
     * @param password 登录密码
     * @param host     服务器ip地址
     * @param port     服务器SSH连接端口号
     * @return
     */
    public static Session createConn(String user, String password, String host, int port) {
        Session session = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            Properties config = new Properties();
            //设置第一次登陆的时候提示，可选值:(ask | yes | no)
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(password);
            //session.setTimeout(10000);
            session.connect();
            logger.info("Session connected. session会话链接建立");

        } catch (JSchException e) {
            e.printStackTrace();
            logger.error("SSH连接远程服务器异常!{}", e.toString());
        }
        return session;
    }


    /**
     * 压缩成ZIP 方法1
     *
     * @param srcDir           压缩文件夹路径
     * @param outputStream     压缩文件输出流
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, OutputStream outputStream, boolean KeepDirStructure)
            throws RuntimeException {

        long start = System.currentTimeMillis();
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(outputStream);
            File sourceFile = new File(srcDir);
            //compress(sourceFile, zipOutputStream, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("finish zip " + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("压缩文件异常!", e);
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("关闭压缩文件输出流异常!{}", e.toString());
                }
            }
        }
    }
}
