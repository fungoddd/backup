package com.crrcdt.backup.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crrcdt.backup.common.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * BaseService实现类
 * </p>
 *
 * @author lyh
 * @date 2019年11月6日11:45:15
 */
@Slf4j
@Service
public abstract class BaseServiceImpl<Mapper extends BaseMapper<Record>, Record extends BaseModel,
        Para extends Map<String, Object>> extends ServiceImpl<Mapper, Record> implements BaseService<Record, Para> {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResult add(Record record) {
        try {
            String userId = ThreadLocalUtil.getCurrentUserId();
            if (StringUtils.isBlank(userId)) {
                return BaseResult.failResultCreate("请先登录!");
            }
            if (record != null) {
                if (StringUtils.isBlank(record.getId())) {
                    record.setId(UUID.randomUUID().toString());
                }
                Date nowDate = new Date();
                record.setCreateDate(nowDate);
                record.setUpdateDate(nowDate);
                record.setCreateBy(userId);
                record.setUpdateBy(userId);
                record.setDelFlag("0");
                int result = baseMapper.insert(record);
                if (result > 0) {
                    return BaseResult.successResultCreate(record);
                }
            }
        } catch (Exception e) {
            log.error("add error:", e);
            throw e;
        }
        return BaseResult.failResultCreate("请刷新重试!");
    }

    /**
     * <p>通过主键查询</p>
     *
     * @param id 主键
     * @return 操作结果
     */
    @Override
    public BaseResult getById(String id) {
        try {
            Object result = baseMapper.selectById(id);
            return BaseResult.successResultCreate(result);
        } catch (Exception e) {
            log.error("getByPK error:", e);
            throw e;
        }
    }

    /**
     * <p>通过条件查询列表</p>
     *
     * @param wrapper 条件
     * @return 操作结果
     */
    @Override
    public BaseResult listByWrapper(Wrapper<Record> wrapper) {
        try {
            Object result = baseMapper.selectList(wrapper);
            return BaseResult.successResultCreate(result);
        } catch (Exception e) {
            log.error("listByWrapper error:", e);
            throw e;
        }
    }

    /**
     * <p>更新一条记录(实体类内字段为null该字段不更新)</p>
     *
     * @param record 实体类
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResult updateSelective(Record record) {
        try {
            String userId = ThreadLocalUtil.getCurrentUserId();
            if (StringUtils.isBlank(userId)) {
                return BaseResult.failResultCreate("请先登录!");
            }
            record.setUpdateBy(userId);
            record.setUpdateDate(new Date());
            // 不更新创建人及创建时间
            record.setCreateBy(null);
            record.setCreateDate(null);
            int result = baseMapper.updateById(record);
            if (result > 0) {
                return BaseResult.successResultCreate(record);
            }
        } catch (Exception e) {
            log.error("updateSelective error:", e);
            throw e;
        }
        return BaseResult.failResultCreate("请刷新重试!");
    }

    /**
     * <p>通过主键逻辑删除一条记录</p>
     *
     * @param id 主键
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResult removeById(String id) {
        try {
            String userId = ThreadLocalUtil.getCurrentUserId();
            if (StringUtils.isBlank(userId)) {
                return BaseResult.failResultCreate("请先登录!");
            }
            Record record = baseMapper.selectById(id);
            if (null == record) {
                return BaseResult.failResultCreate("未找到该记录!");
            }
            record.setDelFlag("1");
            return updateSelective(record);
        } catch (Exception e) {
            log.error("removeById error:", e);
            throw e;
        }
    }

    /**
     * <p>通过条件逻辑删除一条记录</p>
     *
     * @param wrapper 条件
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResult removeByWrapper(Wrapper<Record> wrapper) {
        try {
            String userId = ThreadLocalUtil.getCurrentUserId();
            if (StringUtils.isBlank(userId)) {
                return BaseResult.failResultCreate("请先登录!");
            }
            Record record = baseMapper.selectOne(wrapper);
            if (null == record) {
                return BaseResult.failResultCreate("未找到该记录!");
            }
            record.setDelFlag("1");
            return updateSelective(record);
        } catch (Exception e) {
            log.error("removeByWrapper error:", e);
            throw e;
        }
    }

    /**
     * <p>通过主键物理删除一条记录</p>
     *
     * @param id 主键
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResult deleteById(String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return BaseResult.failResultCreate("请选择要删除的记录!");
            }
            int result = baseMapper.deleteById(id);
            if (result > 0) {
                return BaseResult.successResultCreate("删除成功!");
            }
        } catch (Exception e) {
            log.error("deleteByPK error:", e);
            throw e;
        }
        return BaseResult.failResultCreate("请刷新重试!");
    }

    /**
     * <p>通过条件物理删除一条记录</p>
     *
     * @param wrapper 条件
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResult deleteByWrapper(Wrapper<Record> wrapper) {
        try {
            boolean result = super.remove(wrapper);
            if (result) {
                return BaseResult.successResultCreate("删除成功!");
            }
            return BaseResult.successResultCreate("删除失败!");
        } catch (Exception e) {
            log.error("deleteByWrapper error:", e);
            throw e;
        }
    }
}