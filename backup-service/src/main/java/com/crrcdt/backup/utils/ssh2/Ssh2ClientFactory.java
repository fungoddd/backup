package com.crrcdt.backup.utils.ssh2;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * <p>
 * SSH2客户端对象工厂
 * </p>
 *
 * @author LiuYuHang
 * @date 2019-12-08 16:44:20
 */
@Slf4j
public class Ssh2ClientFactory {

    private Ssh2ClientFactory() {
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
    public static Ssh2Client openSftpChannel(String host, Integer port, String username, String password) throws Exception {
        return createConn(host, port, username, password, false);
    }

    /**
     * <p>与服务器建立EXEC连接</p>
     *
     * @param host     服务器ip地址
     * @param port     服务器ssh访问端口
     * @param username 服务器用户名
     * @param password 服务器密码
     * @return Sftp客户端
     * @throws Exception 异常
     */
    public static Ssh2Client openExecChannel(String host, Integer port, String username, String password) throws Exception {
        return createConn(host, port, username, password, true);
    }

    /**
     * <p>与服务器建立连接</p>
     *
     * @param host     服务器ip地址
     * @param port     服务器ssh访问端口
     * @param username 服务器用户名
     * @param password 服务器密码
     * @param isExec   是否是Exec连接
     * @return Sftp客户端
     * @throws Exception 异常
     */
    private static Ssh2Client createConn(String host, Integer port, String username, String password, boolean isExec) throws Exception {
        Ssh2Client ssh2Client = new Ssh2Client();
        ChannelSftp sftpChannel = null;
        ChannelExec execChannel = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            log.info("SSH2 Session connected 会话链接建立");
            if (isExec) {
                execChannel = (ChannelExec) sshSession.openChannel("exec");
                execChannel.connect();
                log.info("SSH2-EXEC 连接至 {} 服务器 ", host);
                ssh2Client.execChannel(execChannel);
            } else {
                sftpChannel = (ChannelSftp) sshSession.openChannel("sftp");
                sftpChannel.connect();
                log.info("SSH2-SFTP 连接至 {} 服务器 ", host);
                ssh2Client.sftpChannel(sftpChannel);
            }
        } catch (Exception e) {
            log.error("SSH2 连接远程服务器异常!", e);
            if (null != sftpChannel) {
                sftpChannel.quit();
                sftpChannel.disconnect();
            }
            if (null != execChannel) {
                execChannel.disconnect();
            }
            throw e;
        }
        return ssh2Client;
    }

}
