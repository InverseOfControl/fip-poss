package com.ipaylinks.poss.common;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.PageQueryResponse;

public final class DataGrid<T> {

    private Long total;

    private Iterable<T> rows;

    private Iterable<?> footer;

    private String errorMsg;
    
    public DataGrid () {
    	this.total = 0L;
    	this.rows = new ArrayList<T>();
    }
    
    public DataGrid(Iterable<T> rows) {
        this.rows = rows;
    }

    public DataGrid(Long total, Iterable<T> rows) {
        this.total = total;
        this.rows = rows;
    }
	
    public DataGrid(PageQueryResponse<T> queryResponse) {
        PagedResult<T> pageResult = queryResponse.getPagedResult();
        if (pageResult != null) {
            this.total = pageResult.getTotal();
            this.rows = pageResult.getDataList();
        } else {
            this.total = 0L;
            this.rows = null;
        }
    }

    public Long getTotal() {
        return total;
    }

    public DataGrid(Page<T> page) {
        this.total = page.getTotalElements();
        this.rows = page.getContent();
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Iterable<T> getRows() {
        return rows;
    }

    public void setRows(Iterable<T> rows) {
        this.rows = rows;
    }

    public Iterable<?> getFooter() {
        return footer;
    }

    public void setFooter(Iterable<?> footer) {
        this.footer = footer;
    }

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
    
}
