package com.ipaylinks.poss.domain.liquidation;

import com.ipaylinks.mcs.facade.request.MerchantSettlementBankConfigRequest;

public class MertSettlementBankConfigVo extends MerchantSettlementBankConfigRequest {

    /** 当前页 */
    private int page = 1;

    /** 每页记录数 */
    private int rows = 1;

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
