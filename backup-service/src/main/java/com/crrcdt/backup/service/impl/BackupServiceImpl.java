package com.crrcdt.backup.service.impl;

import com.crrcdt.backup.api.BackupService;
import com.crrcdt.backup.common.base.BaseResult;
import com.crrcdt.backup.common.base.BaseServiceImpl;
import com.crrcdt.backup.mapper.BackupMapper;
import com.crrcdt.backup.model.Backup;
import com.crrcdt.backup.model.Server;
import com.crrcdt.backup.utils.sftp.SftpClient;
import com.crrcdt.backup.utils.WebUtil;
import com.crrcdt.backup.utils.sftp.SftpClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Map;

/**
 * <p>
 * 文件备份服务实现类
 * </p>
 *
 * @author LiuYuHang
 * @date 2019年10月30日15:36:52
 */
@Slf4j
@Service
public class BackupServiceImpl extends BaseServiceImpl<BackupMapper, Backup, Map<String, Object>> implements BackupService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResult addBackup(Backup backup) {
        String userId = WebUtil.getCurrentUserId();
        if (StringUtils.isBlank(userId)) {
            return BaseResult.failResultCreate("添加备份信息失败!请先登录!");
        }
        backup.setUserId(userId);
        return super.add(backup);
    }

    @Override
    public void downLoadFile(Backup backup, Server server) throws Exception {
        String host = server.getHost();
        int port = server.getPort();
        String user = server.getUser();
        String password = server.getPassword();
        SftpClient sftpClient = SftpClientFactory.createConn(host, port, user, password);
        sftpClient.download(backup.getSourceDir(), backup.getTargetDir());
    }
}
