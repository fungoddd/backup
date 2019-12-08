package com.crrcdt.backup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crrcdt.backup.api.ServerService;
import com.crrcdt.backup.common.base.BaseResult;
import com.crrcdt.backup.common.utils.EncryUtil;
import com.crrcdt.backup.common.validator.NotNullValidator;
import com.crrcdt.backup.common.validator.Valid;
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
 * 服务器 Controller
 * </p>
 *
 * @author LiuYuHang
 * @date 2019年10月30日15:36:18
 */
@Slf4j
@RestController
@Api(value = "服务信息接口", description = "服务器信息接口")
@RequestMapping("/v1/backup/server")
public class ServerController {

    @Autowired
    private ServerService serverService;

    /**
     * <p>新增服务器信息</p>
     *
     * @param server 服务器对象
     * @return Object
     */
    @ApiOperation(value = "新增服务器信息", response = Server.class)
    @PostMapping("/")
    public Object add(@RequestBody Server server) {
        Valid valid = new Valid().on(server.getHost(), new NotNullValidator("服务器地址"))
                .on(server.getPort(), new NotNullValidator("端口号"))
                .on(server.getUser(), new NotNullValidator("用户名"))
                .on(server.getPassword(), new NotNullValidator("密码"));
        if (valid.isError()) {
            return valid.errorInfo();
        }
        String userId = WebUtil.getCurrentUserId();
        if (StringUtils.isBlank(userId)) {
            return BaseResult.failResultCreate("添加服务器信息失败!请先登录!");
        }
        server.setHost(EncryUtil.entryptPassword(server.getHost()));
        server.setPassword(EncryUtil.entryptPassword(server.getPassword()));
        server.setUserId(userId);
        return serverService.add(server);
    }

    /**
     * <p>通过主键查询服务器信息</p>
     *
     * @param serverId 主键
     * @return 服务器对象
     */
    @ApiOperation(value = "查询服务器信息", response = Server.class)
    @GetMapping("/")
    public Object get(@ApiParam(required = true, value = "备份服务器主键") @RequestParam(value = "serverId") String serverId) {
        String userId = WebUtil.getCurrentUserId();
        if (StringUtils.isBlank(userId)) {
            return BaseResult.failResultCreate("获取服务器信息失败!请先登录!");
        }
        QueryWrapper<Server> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("id", serverId);
        Server server = serverService.getOne(queryWrapper, false);
        return BaseResult.successResultCreate(server);
    }

    /**
     * <p>通过用户id查询服务器信息列表</p>
     *
     * @return 服务器信息列表
     */
    @ApiOperation(value = "查询服务器信息列表", response = Server.class)
    @GetMapping("/list")
    public Object list() {
        String userId = WebUtil.getCurrentUserId();
        if (StringUtils.isBlank(userId)) {
            return BaseResult.failResultCreate("获取服务器信息失败!请先登录!");
        }
        return BaseResult.successResultCreate(serverService.listByWrapper(new QueryWrapper<Server>().eq("userId", userId)));
    }

    /**
     * <p>修改服务器信息</p>
     *
     * @param server 服务器对象
     * @return Object
     */
    @ApiOperation(value = "修改服务器信息")
    @PutMapping("/")
    public Object update(@RequestBody Server server) {
        Valid valid = new Valid().on(server.getHost(), new NotNullValidator("服务器地址"))
                .on(server.getPort(), new NotNullValidator("端口号"))
                .on(server.getUser(), new NotNullValidator("用户名"))
                .on(server.getPassword(), new NotNullValidator("密码"));
        if (valid.isError()) {
            return valid.errorInfo();
        }
        return serverService.updateSelective(server);
    }

    /**
     * <p>通过主键物理删除服务器信息</p>
     *
     * @param serverId 主键
     * @return Object
     */
    @ApiOperation(value = "物理删除服务器信息")
    @DeleteMapping("/")
    public Object delete(@ApiParam(required = true, value = "备份服务器主键") @RequestParam(value = "serverId") String serverId) {
        return serverService.deleteById(serverId);
    }

}
