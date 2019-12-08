package com.crrcdt.backup.utils.sftp;

import com.jcraft.jsch.SftpProgressMonitor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LiuYuHang
 */
@Slf4j
public class ProgressMonitor implements SftpProgressMonitor {

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
    public void init(int op, String src, String dest, long max) {
        if (op == SftpProgressMonitor.PUT) {
            log.info("SFTP 断点续传上传文件开始.");
        } else {
            log.info("SFTP 断点续传下载文件开始.");
        }
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

        int kb = 1024;
        double rate = (double) (this.count / kb);
        double maxSize = (double) (max) / kb;
        String rateStr = String.format("%.0f", rate) + "kb";
        String maxStr = String.format("%.0f", maxSize) + "kb";
        if (rate > kb) {
            rate = rate / kb;
            rateStr = String.format("%.1f", rate) + "m";
        }
        if (max > kb) {
            maxSize = maxSize / kb;
            maxStr = String.format("%.1f", maxSize) + "m";
        }
        log.info("SFTP 文件已传输 {} ({}%） 文件总大小 {} ", rateStr, percent, maxStr);
        return true;
    }

    @Override
    public void end() {
        log.info("SFTP 文件传输结束.");
    }
}
