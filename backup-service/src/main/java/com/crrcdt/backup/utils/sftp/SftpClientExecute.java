package com.crrcdt.backup.utils.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpATTRS;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * <p>
 * SFTP客户端对象工厂
 * </p>
 *
 * @author LiuYuHang
 * @date 2019-12-08 16:44:20
 */
@Slf4j
public class SftpClientExecute {

    private ChannelSftp sftpChannel;

    SftpClientExecute() {
    }

    void sftpChannel(ChannelSftp sftpChannel) {
        this.sftpChannel = sftpChannel;
    }

    /**
     * <p>上传文件</p>
     *
     * @param uploadDir      文件要上传的目录
     * @param uploadFileName 要上传的文件名称
     */
    boolean uploadFile(String uploadDir, String uploadFileName) throws Exception {
        try {
            if (sftpChannel.isClosed()) {
                throw new RuntimeException("SFTP uploadFile 上传文件异常!SFTP未连接!");
            }
            sftpChannel.cd(uploadDir);
            File uploadFile = new File(uploadFileName);
            @Cleanup FileInputStream fileInputStream = new FileInputStream(uploadFile);
            sftpChannel.put(fileInputStream, uploadFile.getName());
            return true;
        } catch (Exception e) {
            log.error("SFTP upload 文件上传异常!", e);
            disconnect();
            throw e;
        }
    }

    /**
     * <p>下载文件或文件夹</p>
     *
     * @param remotePath 要下载的文件路径或文件夹路径
     * @param localDir   本地存储目录所在路径
     * @throws Exception 异常对象
     */
    void download(String remotePath, String localDir) throws Exception {
        download(remotePath, localDir, 0);
    }

    private void download(String remotePath, String localDir, int root) throws Exception {
        long startTime = System.currentTimeMillis();
        try {
            if (sftpChannel.isClosed()) {
                throw new RuntimeException("SFTP downloadFiles 下载文件异常!SFTP未连接!");
            }
            SftpATTRS sftpATTRS = sftpChannel.lstat(remotePath);
            Vector vector;
            // 判断要下载的目标是否为目录
            if (sftpATTRS.isDir()) {
                // 如果是目录则创建本地存储目录
                StringBuilder stringBuilder = new StringBuilder(localDir);
                if (root == 0) {
                    stringBuilder.append(File.separator).append(new File(remotePath).getName()).append("-").append(DateFormatUtils.format(startTime, "yyyyMMdd"));
                    root++;
                }
                File dir = new File(stringBuilder.toString());
                if (!dir.exists()) {
                    log.info("SFTP downloadFiles 本地存储目录{}不存在,创建目录", localDir);
                    dir.mkdirs();
                    localDir = dir.getPath();
                } else {
                    log.info("SFTP downloadFiles 本地存储目录{}已存在,无需创建", localDir);
                }
                log.info("SFTP downloadFiles 开始下载目录{}", remotePath);
                //列出目标目录下所有文件
                vector = sftpChannel.ls(remotePath);
            } else {
                log.info("SFTP downloadFiles 开始下载文件{}", remotePath);
                // 断点续传下载
                sftpChannel.get(remotePath, localDir, new SftpClientProgressMonitor(), ChannelSftp.RESUME);
                long endTime1 = System.currentTimeMillis();
                log.info("SFTP downloadFiles 下载文件完成,用时{}秒", (double) (endTime1 - startTime) / 1000);
                return;
            }
            if (CollectionUtils.isNotEmpty(vector)) {
                for (Object item : vector) {
                    if (item instanceof ChannelSftp.LsEntry) {
                        String fileName = ((ChannelSftp.LsEntry) item).getFilename();
                        if (".".equals(fileName) || "..".equals(fileName)) {
                            continue;
                        }
                        String remoteFileName = remotePath + File.separator + fileName;
                        String localFileName = localDir + File.separator + fileName;
                        download(remoteFileName, localFileName, root);
                    }
                }
            }
            long endTime2 = System.currentTimeMillis();
            log.info("SFTP downloadFiles 下载目录完成,总用时{}秒", (double) (endTime2 - startTime) / 1000);
        } catch (Exception e) {
            log.error("SFTP downloadFiles 通过文件相对路径与文件名称下载文件异常!", e);
            disconnect();
            throw e;
        }
    }

    /**
     * <p>删除文件或文件夹(包括文件夹下所有文件)</p>
     *
     * @param path 要删除的文件路径或文件夹路径
     * @throws Exception 异常对象
     */
    void delete(String path) throws Exception {
        try {
            if (sftpChannel.isClosed()) {
                throw new RuntimeException("SFTP deleteDir 删除" + path + "异常!SFTP未连接!");
            }
            if (!sftpChannel.lstat(path).isDir()) {
                sftpChannel.rm(path);
                return;
            }
            Vector vector = sftpChannel.ls(path);
            if (CollectionUtils.isNotEmpty(vector)) {
                for (Object item : vector) {
                    if (item instanceof ChannelSftp.LsEntry) {
                        String fileName = ((ChannelSftp.LsEntry) item).getFilename();
                        if (".".equals(fileName) || "..".equals(fileName)) {
                            continue;
                        }
                        if (((ChannelSftp.LsEntry) item).getAttrs().isDir()) {
                            delete(path + File.separator + fileName);
                        } else {
                            sftpChannel.rm(path + File.separator + fileName);
                        }
                    }
                }
            }
            sftpChannel.rmdir(path);
            return;
        } catch (Exception e) {
            log.error("SFTP deleteDir 删除" + path + "异常! ", e);
            throw e;
        }
    }

    /**
     * <p>列出目录下的文件</p>
     *
     * @param directory 要列出的目标目录
     * @return 目标目录下的文件列表
     * @throws Exception 异常
     */
    List<String> listFiles(String directory) throws Exception {
        List<String> list;
        try {
            if (sftpChannel.isClosed()) {
                throw new RuntimeException("SFTP listFiles 获取" + directory + "目录下文件异常!SFTP未连接!");
            }
            list = new ArrayList<>();
            //获取文件列表
            Vector vector = sftpChannel.ls(directory);
            log.info("SFTP listFiles 列出" + directory + "目录下文件");
            if (CollectionUtils.isNotEmpty(vector)) {
                for (Object item : vector) {
                    if (item instanceof ChannelSftp.LsEntry) {
                        String fileName = ((ChannelSftp.LsEntry) item).getFilename();
                        if (".".equals(fileName) || "..".equals(fileName)) {
                            continue;
                        }
                        log.info(item.toString());
                        list.add(fileName);
                    }
                }
            }
        } catch (Exception e) {
            log.error("SFTP listFiles 获取" + directory + "目录下文件列表异常!", e);
            disconnect();
            throw e;
        }
        return list;
    }

    /**
     * <p>关闭SFTP连接</p>
     */
    void disconnect() throws Exception {
        try {
            if (sftpChannel != null && sftpChannel.getSession().isConnected()) {
                sftpChannel.getSession().disconnect();
            }
        } catch (JSchException e) {
            log.error("SFTP disconnect 关闭Session连接异常! ", e);
            throw e;
        } finally {
            if (sftpChannel != null) {
                sftpChannel.quit();
                sftpChannel.disconnect();
            }
        }
    }

}
