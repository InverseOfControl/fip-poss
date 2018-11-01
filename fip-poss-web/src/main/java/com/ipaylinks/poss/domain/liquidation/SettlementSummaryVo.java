package com.ipaylinks.poss.domain.liquidation;

import com.ipaylinks.cmp.css.facade.request.QuerySettlementSummaryRequest;

public class SettlementSummaryVo extends QuerySettlementSummaryRequest {
    /** 当前页 */
    private int page;
    /** 每页记录数 */
    private int rows;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
