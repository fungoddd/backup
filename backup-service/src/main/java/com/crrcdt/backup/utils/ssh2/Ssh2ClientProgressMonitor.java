package com.crrcdt.backup.utils.ssh2;

import com.jcraft.jsch.SftpProgressMonitor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;


/**
 * <p>SFTP断点续传</p>
 *
 * @author LiuYuHang
 * @date 2019-12-09 21:42:12
 */
@Slf4j
public class Ssh2ClientProgressMonitor implements SftpProgressMonitor {

    private ProgressMonitor monitor;

    /**
     * 当前接收的总字节数
     */
    private long count = 0;

    /**
     * 最终文件大小
     */
    private long max = 0;

    /**
     * 进度
     */
    private long percent = -1;

    @Override
    public void init(int op, String remotePath, String localDir, long max) {
        String message;
        if (op == SftpProgressMonitor.PUT) {
            message = "SFTP 断点续传上传文件开始";
            log.info(message);
        } else {
            message = "SFTP 断点续传下载文件开始";
            log.info(message);
        }
        monitor = new ProgressMonitor(null, message + ": " + remotePath, "", 0, (int) max);
        this.max = max;
        this.count = 0;
        this.percent = -1;
    }

    @Override
    public boolean count(long count) {
        this.count += count;
        if (percent >= this.count * 100 / max) {
            return true;
        }
        percent = this.count * 100 / max;
        String rateSize = handleFileSize(this.count);
        String maxSize = handleFileSize(max);
        log.info("SFTP 文件已传输 {} ({}%） 文件总大小 {} ", rateSize, percent, maxSize);
        monitor.setNote("SFTP 文件已传输 " + rateSize + " (" + percent + "%） 文件总大小 " + maxSize);
        monitor.setProgress((int) this.count);
        return !(monitor.isCanceled());
    }

    private String handleFileSize(long fileSize) {
        double kbSize = (double) fileSize / 1024;
        int m = 1024;
        int gb = 1024 * m;
        String fileSizeStr;
        if (kbSize > gb) {
            kbSize = kbSize / gb;
            fileSizeStr = String.format("%.1f", kbSize) + "GB";
        } else if (kbSize > m) {
            kbSize = kbSize / m;
            fileSizeStr = String.format("%.1f", kbSize) + "M";
        } else {
            fileSizeStr = String.format("%.0f", kbSize) + "KB";
        }
        return fileSizeStr;
    }

    @Override
    public void end() {
        monitor.close();
        log.info("SFTP 文件传输结束.");
    }

}
