package com.crrcdt.backup.utils.sftp;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * <p>
 * SFTP客户端对象工厂
 * </p>
 *
 * @author LiuYuHang
 * @date 2019-12-08 16:44:20
 */
@Slf4j
public class SftpClientFactory {

    private SftpClientFactory() {
    }

    /**
     * <p>与服务器建立SFTP连接</p>
     *
     * @param host     服务器ip地址
     * @param port     服务器ssh访问端口
     * @param username 服务器用户名
     * @param password 服务器密码
     * @return Sftp客户端
     * @throws Exception 异常
     */
    public static SftpClient createConn(String host, Integer port, String username, String password) throws Exception {
        ChannelSftp sftp = null;
        SftpClient sftpClient = new SftpClient();
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            log.info("SFTP Session connected 会话链接建立");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            log.info("连接至 {} 服务器 ", host);
            sftpClient.sftpChannel(sftp);
        } catch (Exception e) {
            log.error("SFTP 连接远程服务器异常!", e);
            if (null != sftp) {
                sftp.quit();
                sftp.disconnect();
            }
            throw e;
        }
        return sftpClient;
    }

}
