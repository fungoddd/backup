package com.crrcdt.backup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crrcdt.backup.api.BackupService;
import com.crrcdt.backup.api.ServerService;
import com.crrcdt.backup.common.base.BaseController;
import com.crrcdt.backup.common.base.BaseResult;
import com.crrcdt.backup.model.Backup;
import com.crrcdt.backup.model.Server;
import com.crrcdt.backup.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文件备份 Controller
 * </p>
 *
 * @author LiuYuHang
 * @date 2019年10月30日15:36:18
 */
@Slf4j
@RestController
@Api(value = "文件备份信息接口", description = "文件备份信息接口")
@RequestMapping("/v1/backup/backup")
public class BackupController extends BaseController {

    @Autowired
    private BackupService backupService;
    @Autowired
    private ServerService serverService;

    /**
     * <p>新增文件备份信息</p>
     *
     * @param backup 文件备份对象
     * @return DB更改记录数
     */
    @ApiOperation(value = "新增文件备份信息", response = Backup.class)
    @PostMapping("/")
    public Object add(@RequestBody Backup backup) {
        return backupService.add(backup);
    }

    /**
     * <p>通过主键查询文件备份信息</p>
     *
     * @param backupId 主键
     * @return 文件备份对象
     */
    @ApiOperation(value = "查询文件备份信息", response = Backup.class)
    @GetMapping("/")
    public Object get(@ApiParam(required = true, value = "文件备份主键") @RequestParam(value = "backupId") String backupId) {
        return backupService.getById(backupId);
    }

    /**
     * <p>通过用户id查询文件备份信息列表</p>
     *
     * @return 文件备份对象
     */
    @ApiOperation(value = "查询文件备份信息列表", response = Backup.class)
    @GetMapping("/list")
    public Object list() {
        String userId = WebUtil.getCurrentUserId();
        if (StringUtils.isBlank(userId)) {
            return BaseResult.failResultCreate("获取文件备份信息失败!请先登录!");
        }
        return backupService.listByWrapper(new QueryWrapper<Backup>().eq("userId", userId));
    }

    /**
     * <p>修改文件备份信息</p>
     *
     * @param backup 文件备份对象
     * @return DB更改记录数
     */
    @ApiOperation(value = "修改文件备份信息")
    @PutMapping("/")
    public Object update(@RequestBody Backup backup) {
        return backupService.updateSelective(backup);
    }

    /**
     * <p>通过主键物理删除文件备份信息</p>
     *
     * @param backupId 主键
     * @return DB更改记录数
     */
    @ApiOperation(value = "物理删除文件备份信息")
    @DeleteMapping("/")
    public Object delete(@ApiParam(required = true, value = "文件备份主键") @RequestParam(value = "backupId") String backupId) {
        return backupService.deleteById(backupId);
    }

    /**
     * <p>通过SFTP下载文件</p>
     *
     * @param backup 文件备份信息
     */
    @ApiOperation(value = "SFTP下载文件")
    @GetMapping("/downLoadFile")
    public void downLoadFile(@RequestBody Backup backup) throws Exception {
        String serverId = backup.getServerId();
        Server server = (Server) serverService.getById(serverId).getData();
        backupService.downLoadFile(backup, server);
    }


}
