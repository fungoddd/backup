package com.crrcdt.backup.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * BaseService接口
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日10:02:26
 */
public interface BaseService<Record, Para> extends IService<Record> {

    /**
     * <p>插入一条记录</p>
     *
     * @param record 实体类
     * @return 操作结果
     */
    BaseResult add(Record record);

    /**
     * <p>通过主键查询</p>
     *
     * @param id 主键
     * @return 操作结果
     */
    BaseResult getById(String id);

    /**
     * <p>通过条件查询列表</p>
     *
     * @param wrapper 条件
     * @return 操作结果
     */
    BaseResult listByWrapper(Wrapper<Record> wrapper);

    /**
     * <p>更新一条记录(实体类内字段为null该字段不更新)</p>
     *
     * @param record 实体类
     * @return 操作结果
     */
    BaseResult updateSelective(Record record);

    /**
     * <p>通过主键逻辑删除一条记录</p>
     *
     * @param id 主键
     * @return 操作结果
     */
    BaseResult removeById(String id);

    /**
     * <p>通过条件逻辑删除一条记录</p>
     *
     * @param wrapper 条件
     * @return 操作结果
     */
    BaseResult removeByWrapper(Wrapper<Record> wrapper);

    /**
     * <p>通过主键物理删除一条记录</p>
     *
     * @param id 主键
     * @return 操作结果
     */
    BaseResult deleteById(String id);

    /**
     * <p>通过条件物理删除记录</p>
     *
     * @param wrapper 条件
     * @return 操作结果
     */
    BaseResult deleteByWrapper(Wrapper<Record> wrapper);

}