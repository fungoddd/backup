package com.crrcdt.backup.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
     * <p>返回全部数据(不包含逻辑删除数据)</p>
     *
     * @return List<Record>
     */
    List<Record> listAll();

    /**
     * <p>返回全部数据</p>
     *
     * @param hasDelFlag 是否包含delFlag=0字段 限制条件,默认存在:false,则返回全部数据,包括逻辑删除数据
     * @return List<Record>
     */
    List<Record> listAll(boolean hasDelFlag);

    /**
     * <p>根据Map条件,返回数据(只有手动写SQL,才使用.默认推荐使用EntityWrapper)</p>
     *
     * @param para 查询条件
     * @return List<Record>
     */
    List<Record> listByPara(Para para);

    /**
     * <p>根据Wrapper查询条件,返回数据</p>
     *
     * @param ew Wrapper
     * @return List<Record>
     */
    List<Record> listByWrapper(@Param("ew") Wrapper<Record> ew);

    /**
     * <p>分页查询(根据参数列表)</p>
     *
     * @param pagination 分页参数
     * @param para       查询参数
     * @return PageResult<Record>
     */
    PageResult<Record> queryByPage(@Param("pagination") Pagination pagination, @Param("para") Para para);

    /**
     * <p>返回分页数据(Wrapper)</p>
     *
     * @param ew         Wrapper
     * @param pagination 分页参数
     * @return PageResult<Record>
     */
    PageResult<Record> queryByPageWithWrapper(Wrapper<Record> ew, Pagination pagination);

    /**
     * <p>根据条件返回全部数据(不分页)</p>
     *
     * @param record 查询条件
     * @return PageResult<Record>
     */
    PageResult<Record> queryWithAutoWrapper(Record record);

    /**
     * <p>增加记录(插入删除Flag默认值、创建者、创建时间等字段)</p>
     *
     * @param record 实体
     * @return 插入成功的实体
     */
    Record add(Record record);

    /**
     * <p>增加记录,返回BaseResult</p>
     *
     * @param record the record
     * @return the record
     */
    BaseResult addOne(Record record);

    /**
     * <p>批量添加</p>
     *
     * @param entityList 实体列表
     * @return boolean
     */
    boolean insertBatch(List<Record> entityList);

    /**
     * <p>批量添加</p>
     *
     * @param entityList 实体列表
     * @param batchSize  批量插入数量
     * @return 布boolean
     */

    boolean insertBatch(List<Record> entityList, int batchSize);


    /**
     * <p>更新非空字段(null字段不更新)</p>
     *
     * @param record 数据实体
     * @return 成功记录个数
     */
    int updateSelective(Record record);


    /**
     * <p>批量更新 非空字段(null字段不更新)</p>
     *
     * @param entityList 实体对象列表
     * @return boolean
     */
    boolean updateSelectiveBatchById(List<Record> entityList);

    /**
     * <p>批量更新 非空字段(null字段不更新)</p>
     *
     * @param entityList 实体对象列表
     * @param batchSize  更新批次数量
     * @return boolean
     */
    boolean updateSelectiveBatchById(List<Record> entityList, int batchSize);


    /**
     * <p>更新全部字段(自动更新,修改人及修改时间)</p>
     *
     * @param record 数据实体
     * @return 成功记录个数
     */
    int updateAllColumn(Record record);


    /**
     * <p>
     * 根据ID 批量更新全部字段(所有字段都会更新，包括创建人等字段）
     * </p>
     *
     * @param entityList 实体对象列表
     * @return boolean
     */
    boolean updateAllColumnBatchById(List<Record> entityList);

    /**
     * <p>
     * 根据ID 批量更新全部字段(所有字段都会更新,包括创建人等字段）
     * </p>
     *
     * @param entityList 实体对象列表
     * @param batchSize  更新批次数量
     * @return boolean
     */
    boolean updateAllColumnBatchById(List<Record> entityList, int batchSize);


    /**
     * 逻辑删除（更新删除标识、更新者、更新时间）
     *
     * @param id 主键id
     * @return 更新成功记录个数
     */
    int removeByPK(String id);

    /**
     * <p>逻辑删除</p>
     *
     * @param wrapper
     * @return boolean
     */
    boolean removeWithWrapper(Wrapper<Record> wrapper);

    /**
     * <p>逻辑批量删除</p>
     *
     * @param ids    主键集合
     * @param record 条件
     * @return int
     */
    int removeAll(List<String> ids, Record record);

    /**
     * <p>批量物理删除</p>
     *
     * @param ids 主键集合
     * @return
     */
    int deleteByPKs(List<String> ids);

    /**
     * <p>物理删除</p>
     *
     * @param id 主键
     * @return boolean
     */
    boolean deleteById(Serializable id);

    /**
     * <p>条件物理删除</p>
     *
     * @param wrapper 查询条件
     * @return boolean
     */
    boolean delete(Wrapper<Record> wrapper);

    /**
     * <p>批量物理删除</p>
     *
     * @param idList 主键id集合
     * @return boolean
     */
    boolean deleteBatchIds(Collection<? extends Serializable> idList);

    /**
     * <p>主键查询</p>
     *
     * @param id 主键
     * @return 对象实体
     */
    Record getByPK(String id);

    /**
     * <p>根据条件查询数量(主要应用于修改时验证重复)<br>
     * 用法:传入一个数据库字段,和对应的值,返回为该字段该值对应的数据的条数
     * </p>
     *
     * @param key   数据库的字段
     * @param value 要验证是否重复的值
     * @return int
     */
    int selectCount(String key, String value);

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
    int selectCount(String key, String value, String recordId);

}