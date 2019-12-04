package com.crrcdt.backup.service.impl;

import com.crrcdt.backup.api.BackupService;
import com.crrcdt.backup.common.base.BaseServiceImpl;
import com.crrcdt.backup.mapper.BackupMapper;
import com.crrcdt.backup.model.Backup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 文件备份服务实现类
 * </p>
 *
 * @author lyh
 * @date 2019年10月30日15:36:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BackupServiceImpl extends BaseServiceImpl<BackupMapper, Backup, Map<String, Object>>
        implements BackupService{

    private static Logger logger = LoggerFactory.getLogger(BackupServiceImpl.class);

}
