package com.crrcdt.backup.service.impl;

import com.crrcdt.backup.api.ServerService;
import com.crrcdt.backup.common.base.BaseResult;
import com.crrcdt.backup.common.base.BaseServiceImpl;
import com.crrcdt.backup.mapper.ServerMapper;
import com.crrcdt.backup.model.Server;
import com.crrcdt.backup.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 服务器信息接口实现
 * </p>
 *
 * @author LiuYuHang
 * @date 2019年12月6日16:42:12
 */
@Slf4j
@Service
public class ServerServiceImpl extends BaseServiceImpl<ServerMapper, Server, Map<String, Object>> implements ServerService {

    /**
     * <p>新增一条备份服务器信息信息记录</p>
     *
     * @param server 服务器对象
     * @return 操作结果
     */
    @Override
    public BaseResult addServer(Server server) {
        String userId = WebUtil.getCurrentUserId();
        if (StringUtils.isBlank(userId)) {
            return BaseResult.failResultCreate("添加服务器信息失败!请先登录!");
        }
        server.setUserId(userId);
        return super.add(server);
    }
}
