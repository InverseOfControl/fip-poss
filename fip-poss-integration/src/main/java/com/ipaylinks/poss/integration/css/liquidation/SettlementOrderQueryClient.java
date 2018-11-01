package com.ipaylinks.poss.integration.css.liquidation;

import com.ipaylinks.cmp.css.facade.QuerySettlementOrderFacade;
import com.ipaylinks.cmp.css.facade.dto.MertSettlementOrderDto;
import com.ipaylinks.cmp.css.facade.request.QuerySettlementRequest;
import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.enums.FinanceTypeEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.integration.common.EnumConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 查询结算单接口调用层
 *
 * @author hongxu.gao
 * @date 2018/8/28 11:05
 */
@Service
public class SettlementOrderQueryClient {
    private Logger logger = LoggerFactory.getLogger(SettlementOrderQueryClient.class);

    @Autowired(required = false)
    private QuerySettlementOrderFacade facade;

    public PagedResult query(QuerySettlementRequest request){
        PageQueryResponse response = facade.querySettlementOrder(request);
        if(null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())){
            logger.info("查询结算单失败，服务方返回异常，response = {}", response);
        }
        PagedResult pagedResult = response.getPagedResult();
        pagedResult.setDataList(convertList(pagedResult.getDataList()));
        return response.getPagedResult();
    };

    private List convertList(List<MertSettlementOrderDto> list){
        for (int i = 0; i < list.size(); i++){
            list.get(i).setTransType(TradeTypeEnum.getDescByNumeric(list.get(i).getTransType()));
            list.get(i).setAmountType(FinanceTypeEnum.getMessage(list.get(i).getAmountType()));
            list.get(i).setSummaryStatus(EnumConverter.convertEnumCode(EnumConverter.SUMMARY_STATUS,list.get(i).getSummaryStatus()));
            list.get(i).setAccountingStatus(EnumConverter.convertEnumCode(EnumConverter.ACCOUNT_STATUS,list.get(i).getAccountingStatus()));
        }
        return list;
    }

}
