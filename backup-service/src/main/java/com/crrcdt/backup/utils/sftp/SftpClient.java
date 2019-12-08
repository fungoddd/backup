package com.crrcdt.backup.utils.sftp;

import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p>
 * SFTP客户端
 * </p>
 *
 * @author LiuYuHang
 * @date 2019-12-08 16:45:23
 */
@Slf4j
public class SftpClient {

    private SftpClientExecute sftpExecute;

    SftpClient() {
        this.sftpExecute = new SftpClientExecute();
    }

    SftpClient sftpChannel(ChannelSftp sftpChannel) {
        this.sftpExecute.sftpChannel(sftpChannel);
        return this;
    }

    /**
     * <p>上传文件</p>
     *
     * @param uploadDir      文件要上传的目录
     * @param uploadFileName 要上传的文件名称
     */
    public boolean uploadFile(String uploadDir, String uploadFileName) throws Exception {
        return sftpExecute.uploadFile(uploadDir, uploadFileName);
    }

    /**
     * <p>下载文件或文件夹</p>
     *
     * @param remotePath 要下载的文件路径或文件夹路径
     * @param localDir   本地存储目录所在路径
     * @throws Exception 异常对象
     */
    public void download(String remotePath, String localDir) throws Exception {
        sftpExecute.download(remotePath, localDir);
    }

    /**
     * <p>删除文件或文件夹(包括文件夹下所有文件)</p>
     *
     * @param path 要删除的文件路径或文件夹路径
     * @throws Exception 异常对象
     */
    public void delete(String path) throws Exception {
        sftpExecute.delete(path);
    }

    /**
     * <p>列出指定目录下的所有文件</p>
     *
     * @param directory 要列出的目标目录
     * @return 目标目录下的文件列表
     * @throws Exception 异常对象
     */
    public List<String> listFiles(String directory) throws Exception {
        return sftpExecute.listFiles(directory);
    }

    /**
     * <p>关闭SFTP连接</p>
     */
    public void disconnect() throws Exception {
        this.sftpExecute.disconnect();
    }

}
