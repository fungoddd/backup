package com.crrcdt.backup.controller;

import com.crrcdt.backup.api.BackupService;
import com.crrcdt.backup.common.base.BaseController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文件备份 Controller
 * </p>
 *
 * @author lyh
 * @date 2019年10月30日15:36:18
 */
@RestController
@Api(value = "文件备份控制器", description = "文件备份控制器")
@RequestMapping("/v1/backup/backup")
public class BackupController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(BackupController.class);

    @Autowired
    private BackupService backupService;


}
