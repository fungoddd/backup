package com.crrcdt.backup.common.base;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * BaseMapper
 * </p>
 *
 * @author lyh
 * @date 2019年11月6日11:09:43
 */
public interface BaseMapper<T extends BaseModel> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * <p>条件查询</p>
     *
     * @param params 查询条件
     * @return List<T>
     */
    List<T> selectByPara(Map<String, Object> params);

    /**
     * <p>主键查询</p>
     *
     * @param id 主键id
     * @return T
     */
    T getByPK(String id);

}
