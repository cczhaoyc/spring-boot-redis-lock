package com.suxia.cc.mybatis.base.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.suxia.cc.mybatis.base.query.QueryParam;

import java.io.Serializable;
import java.util.List;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 分页统一返回格式
 * @date 2020/4/22 10:35
 */
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 7148752873240683463L;

    /**
     * 总记录数
     */
    private long totalRecordCount;
    /**
     * 总页数
     */
    private int totalPageCount;
    /**
     * 分页数据
     */
    private List<T> data;
    /**
     * 当前是第几页，从1开始记数
     */
    private int pageNo = 1;
    /**
     * 每页多少条
     */
    private int pageSize;
    /**
     * 分页查询接口
     */
    private QueryParam query;

    private PageResult() {
    }

    public static <T> PageResult<T> create(QueryParam query, List<T> data, long totalRecordCount) {
        PageResult<T> result = new PageResult<>(query);
        result.setData(data);
        result.setTotalRecordCount(totalRecordCount);
        return result;
    }

    private PageResult(QueryParam query) {
        pageNo = query.getPageNo();
        pageSize = query.getPageSize();
        this.query = query;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    @JSONField(serialize = false, deserialize = false)
    public int getFirst() {
        return (pageNo - 1) * pageSize + 1;
    }

    /**
     * 记录总数
     */
    public long getTotalRecordCount() {
        return this.totalRecordCount;
    }

    /**
     * 记录总数
     */
    public void setTotalRecordCount(long totalRecordCount) {
        this.totalRecordCount = totalRecordCount;

        if (totalPageCount == 0 && pageSize > 0) {
            totalPageCount = (int) (totalRecordCount / pageSize);
            totalPageCount += totalRecordCount % pageSize > 0 ? 1 : 0;
        }
    }

    /**
     * 页数
     */
    public int getTotalPageCount() {
        return totalPageCount;
    }

    /**
     * 是否还有下一页.
     */
    @JSONField(serialize = false, deserialize = false)
    public boolean isHasNext() {
        return pageNo < totalPageCount;
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        if (isHasNext()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return pageNo > 1;
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
     */
    @JSONField(serialize = false, deserialize = false)
    public int getPrePage() {
        if (isHasPre()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 结果数据集
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 结果数据集
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    @JSONField(serialize = false, deserialize = false)
    public QueryParam getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "PageResult [recordCount=" + totalRecordCount + ", pageCount=" + totalPageCount + ", data=" + data
                + ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", query=" + query + "]";
    }
}
