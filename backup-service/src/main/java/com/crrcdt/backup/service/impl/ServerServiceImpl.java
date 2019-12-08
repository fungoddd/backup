package com.crrcdt.backup.service.impl;

import com.crrcdt.backup.api.ServerService;
import com.crrcdt.backup.common.base.BaseServiceImpl;
import com.crrcdt.backup.mapper.ServerMapper;
import com.crrcdt.backup.model.Server;
import lombok.extern.slf4j.Slf4j;
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

}
