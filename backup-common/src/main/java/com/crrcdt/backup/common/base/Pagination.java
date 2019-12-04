package com.crrcdt.backup.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;


/**
 * <p>搜索参数分页器</p>
 *
 * @author lyh
 * @date 2019年11月1日10:21:33
 */
public class Pagination implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DESC = "descend";
    private static final String ASC = "ascend";

    @Getter
    private int pageNum = 1;

    @Getter
    private int pageSize = 10;

    @Getter
    @Setter
    @ApiModelProperty(value = "排序字段,默认创建时间降序")
    private String sortField = "createDate";

    @Getter
    @Setter
    @ApiModelProperty(value = "排序：descend降序，ascend升序")
    private String sortOrder = DESC;

    public Pagination() {
    }

    public Pagination(int pageNum, int pageSize) {
        // 如果PageSize没有设置，0，则默认改为10,  如果是-1 ，则查询全部
        if (pageSize == 0) {
            pageSize = 10;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    /**
     * Sets page num.当前页
     *
     * @param pageNum the page num
     */
    public void setPageNum(int pageNum) {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        this.pageNum = pageNum;
    }

    /**
     * Sets page num.
     */
    public boolean isNeedPagination() {
        return this.pageNum < 0;
    }

    /**
     * Sets page size. 没有设置时，默认为第1页，其中，「-1」指代不分页
     *
     * @param pageSize the page size
     */
    public void setPageSize(int pageSize) {
        if (pageSize < 0) {
            pageSize = 0;
        } else if (pageSize == 0) {
            pageSize = 10;
        }
        this.pageSize = pageSize;
    }

    public boolean needOrderBy() {
        return StringUtils.isNotBlank(sortField);
    }

    public boolean isAsc() {
        return Objects.equals(sortOrder, ASC);
    }

}
