package com.crrcdt.backup.api;

import com.crrcdt.backup.common.base.BaseResult;
import com.crrcdt.backup.common.base.BaseService;
import com.crrcdt.backup.model.Backup;
import com.crrcdt.backup.model.Server;

import java.util.Map;

/**
 * <p>
 * 文件备份服务接口
 * </p>
 *
 * @author LiuYuHang
 * @date 2019年10月30日15:38:43
 */
public interface BackupService extends BaseService<Backup, Map<String, Object>> {

    /**
     * <p>新增一条文件备份信息记录</p>
     *
     * @param backup 文件备份对象
     * @return 操作结果
     */
    BaseResult addBackup(Backup backup);

    /**
     * <p>通过SFTP下载文件</p>
     *
     * @param backup 文件备份信息
     * @param server 服务器信息
     * @return 备份文件信息
     * @throws Exception 异常
     */
    void downLoadFile(Backup backup, Server server) throws Exception;

}
