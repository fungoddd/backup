package com.crrcdt.backup.api;

import com.crrcdt.backup.common.base.BaseResult;
import com.crrcdt.backup.common.base.BaseService;
import com.crrcdt.backup.model.Server;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务器信息接口
 * </p>
 *
 * @author LiuYuHang
 * @date 2019年12月06日14:32:53
 */
public interface ServerService extends BaseService<Server, Map<String, Object>> {

    /**
     * <p>新增一条备份服务器信息信息记录</p>
     *
     * @param server 服务器对象
     * @return 操作结果
     */
    BaseResult addServer(Server server);

}
