package com.crrcdt.backup.service.impl;

import com.crrcdt.backup.api.BackupService;
import com.crrcdt.backup.common.base.BaseResult;
import com.crrcdt.backup.common.base.BaseServiceImpl;
import com.crrcdt.backup.mapper.BackupMapper;
import com.crrcdt.backup.model.Backup;
import com.crrcdt.backup.model.Server;
import com.crrcdt.backup.utils.ssh2.Ssh2Client;
import com.crrcdt.backup.utils.WebUtil;
import com.crrcdt.backup.utils.ssh2.Ssh2ClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public void downLoadFile(Backup backup, Server server) throws Exception {
        String host = server.getHost();
        int port = server.getPort();
        String user = server.getUser();
        String password = server.getPassword();
        Ssh2Client ssh2Client = Ssh2ClientFactory.openSftpChannel(host, port, user, password);
        ssh2Client.download(backup.getSourceDir(), backup.getTargetDir());
    }

    @Override
    public void outputFile(Backup backup, Server server) throws Exception {
        String host = server.getHost();
        int port = server.getPort();
        String user = server.getUser();
        String password = server.getPassword();
        Ssh2Client ssh2Client = Ssh2ClientFactory.openSftpChannel(host, port, user, password);
        ssh2Client.output(backup.getSourceDir(), backup.getTargetDir());
    }
}
