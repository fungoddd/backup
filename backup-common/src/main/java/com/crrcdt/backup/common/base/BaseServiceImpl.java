package com.crrcdt.backup.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.BindingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * BaseService实现类
 * </p>
 *
 * @author lyh
 * @date 2019年11月6日11:45:15
 */
@Service
public abstract class BaseServiceImpl<Mapper extends BaseMapper<Record>, Record extends BaseModel,
        Para extends Map<String, Object>> extends ServiceImpl<Mapper, Record> implements BaseService<Record, Para> {

    private static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);


    /**
     * <p>返回全部数据(不包含逻辑删除数据)</p>
     *
     * @return List<Record>
     */
    @Override
    public List<Record> listAll() {
        return null;
    }

    /**
     * <p>返回全部数据</p>
     *
     * @param hasDelFlag 是否包含delFlag=0字段 限制条件,默认存在:false,则返回全部数据,包括逻辑删除数据
     * @return List<Record>
     */
    @Override
    public List<Record> listAll(boolean hasDelFlag) {
        return null;
    }

    /**
     * <p>根据Map条件,返回数据(只有手动写SQL,才使用.默认推荐使用EntityWrapper)</p>
     *
     * @param para 查询条件
     * @return List<Record>
     */
    @Override
    public List<Record> listByPara(Para para) {
        return null;
    }

    /**
     * <p>根据Wrapper查询条件,返回数据</p>
     *
     * @param ew Wrapper
     * @return List<Record>
     */
    @Override
    public List<Record> listByWrapper(@Param("ew") Wrapper<Record> ew) {
        return null;
    }

    /**
     * <p>分页查询(根据参数列表)</p>
     *
     * @param pagination 分页参数
     * @param para       查询参数
     * @return PageResult<Record>
     */
    @Override
    public PageResult<Record> queryByPage(@Param("pagination") javafx.scene.control.Pagination pagination, @Param("para") Para para) {
        return null;
    }

    /**
     * <p>返回分页数据(Wrapper)</p>
     *
     * @param ew         Wrapper
     * @param pagination 分页参数
     * @return PageResult<Record>
     */
    @Override
    public PageResult<Record> queryByPageWithWrapper(Wrapper<Record> ew, Pagination pagination) {
        return null;
    }

    /**
     * <p>根据条件返回全部数据(不分页)</p>
     *
     * @param record 查询条件
     * @return PageResult<Record>
     */
    @Override
    public PageResult<Record> queryWithAutoWrapper(Record record) {
        return null;
    }

    /**
     * <p>增加记录(插入删除Flag默认值、创建者、创建时间等字段)</p>
     *
     * @param record 实体
     * @return 插入成功的实体
     */
    @Override
    public Record add(Record record) {
        return null;
    }

    /**
     * <p>增加记录,返回BaseResult</p>
     *
     * @param record the record
     * @return the record
     */
    @Override
    public BaseResult addOne(Record record) {
        return null;
    }

    /**
     * <p>批量添加</p>
     *
     * @param entityList 实体列表
     * @return boolean
     */
    @Override
    public boolean insertBatch(List<Record> entityList) {
        return false;
    }

    /**
     * <p>批量添加</p>
     *
     * @param entityList 实体列表
     * @param batchSize  批量插入数量
     * @return 布boolean
     */

    @Override
    public boolean insertBatch(List<Record> entityList, int batchSize) {
        return false;
    }


    /**
     * <p>更新非空字段(null字段不更新)</p>
     *
     * @param record 数据实体
     * @return 成功记录个数
     */
    @Override
    public int updateSelective(Record record) {
        return 0;
    }


    /**
     * <p>批量更新 非空字段(null字段不更新)</p>
     *
     * @param entityList 实体对象列表
     * @return boolean
     */
    @Override
    public boolean updateSelectiveBatchById(List<Record> entityList) {
        return false;
    }

    /**
     * <p>批量更新 非空字段(null字段不更新)</p>
     *
     * @param entityList 实体对象列表
     * @param batchSize  更新批次数量
     * @return boolean
     */
    @Override
    public boolean updateSelectiveBatchById(List<Record> entityList, int batchSize) {
        return false;
    }


    /**
     * <p>更新全部字段(自动更新,修改人及修改时间)</p>
     *
     * @param record 数据实体
     * @return 成功记录个数
     */
    @Override
    public int updateAllColumn(Record record) {
        return 0;
    }


    /**
     * <p>
     * 根据ID 批量更新全部字段(所有字段都会更新，包括创建人等字段）
     * </p>
     *
     * @param entityList 实体对象列表
     * @return boolean
     */
    @Override
    public boolean updateAllColumnBatchById(List<Record> entityList) {
        return false;
    }

    /**
     * <p>
     * 根据ID 批量更新全部字段(所有字段都会更新,包括创建人等字段）
     * </p>
     *
     * @param entityList 实体对象列表
     * @param batchSize  更新批次数量
     * @return boolean
     */
    @Override
    public boolean updateAllColumnBatchById(List<Record> entityList, int batchSize) {
        return false;
    }


    /**
     * 逻辑删除（更新删除标识、更新者、更新时间）
     *
     * @param id 主键id
     * @return 更新成功记录个数
     */
    @Override
    public int removeByPK(String id) {
        return 0;
    }

    /**
     * <p>逻辑删除</p>
     *
     * @param wrapper
     * @return boolean
     */
    @Override
    public boolean removeWithWrapper(Wrapper<Record> wrapper) {
        return false;
    }

    /**
     * <p>逻辑批量删除</p>
     *
     * @param ids    主键集合
     * @param record 条件
     * @return int
     */
    @Override
    public int removeAll(List<String> ids, Record record) {
        return 0;
    }

    /**
     * <p>批量物理删除</p>
     *
     * @param ids 主键集合
     * @return
     */
    @Override
    public int deleteByPKs(List<String> ids) {
        return 0;
    }

    /**
     * <p>物理删除</p>
     *
     * @param id 主键
     * @return boolean
     */
    @Override
    public boolean deleteById(Serializable id) {
        return false;
    }

    /**
     * <p>条件物理删除</p>
     *
     * @param wrapper 查询条件
     * @return boolean
     */
    @Override
    public boolean delete(Wrapper<Record> wrapper) {
        return false;
    }

    /**
     * <p>批量物理删除</p>
     *
     * @param idList 主键id集合
     * @return boolean
     */
    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        return false;
    }

    /**
     * <p>主键查询</p>
     *
     * @param id 主键id
     * @return T
     */
    @Override
    public Record getByPK(String id) {
        try {
            Object result = baseMapper.getByPK(id);
            return (Record) result;
        } catch (BindingException e) {
            //如果没有getByPK对应sql，则执行默认sql
            Object result = baseMapper.selectById(id);
            return (Record) result;
        } catch (Exception e) {
            logger.error("error:", e);
            throw e;
        }
    }

    /**
     * <p>根据条件查询数量(主要应用于修改时验证重复)<br>
     * 用法:传入一个数据库字段,和对应的值,返回为该字段该值对应的数据的条数
     * </p>
     *
     * @param key   数据库的字段
     * @param value 要验证是否重复的值
     * @return int
     */
    @Override
    public int selectCount(String key, String value) {
        return 0;
    }

    /**
     * <p>根据条件查询数量(主要应用于修改时验证重复)<br>
     * 用法:传入一个数据库字段,和对应的值,返回为该字段该值对应的数据的条数
     * </p>
     *
     * @param key      数据库的字段
     * @param value    要验证是否重复的值
     * @param recordId 要验证重复的实体主键
     * @return int
     */
    @Override
    public int selectCount(String key, String value, String recordId) {
        return 0;
    }

}