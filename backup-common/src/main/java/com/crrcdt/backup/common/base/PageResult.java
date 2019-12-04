package com.crrcdt.backup.common.base;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 分页结果
 * </p>
 *
 * @param <T> the type parameter
 * @author lyh
 * @date 2019年11月1日09:58:42
 */
@Data
public class PageResult<T> extends Pagination {

    private static final long serialVersionUID = 1L;

    private List<T> dataList = new ArrayList<>();

    private long total;

    private int pages;

    /**
     * To page result page result.
     *
     * @param <T>      the type parameter
     * @param dataList the dataList
     * @return the page result
     */
    public static <T> PageResult<T> toPageResult(List<T> dataList) {
        PageResult<T> result = new PageResult<>();

        if (dataList instanceof Page) {
            Page<T> page = (Page<T>) dataList;
            result.setPageNum(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setDataList(page.getResult());
            result.setTotal(page.getTotal());
            result.setPages(page.getPages());
        } else {
            result.setPageNum(1);
            result.setPageSize(dataList.size());
            result.setDataList(dataList);
            result.setTotal(dataList.size());
        }

        return result;
    }

    /**
     * Get offset index int.
     *
     * @return the int
     */
    public int getOffsetIndex() {
        int offset = (getPageNum() - 1) * getPageSize();
        if (offset >= getTotal()) {
            offset = 0;
        }
        return offset;
    }

}
