package com.ipaylinks.poss.integration.css.liquidation;

import com.ipaylinks.cmp.css.facade.QuerySettlementSummaryFacade;
import com.ipaylinks.cmp.css.facade.dto.MertSettlementSummaryDto;
import com.ipaylinks.cmp.css.facade.request.QuerySettlementSummaryRequest;
import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.integration.common.EnumConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 查询结算汇总信息接口调用层
 *
 * @author hongxu.gao
 * @date 2018/8/28 11:05
 */
@Service
public class SettlementSummaryQueryClient {
    private Logger logger = LoggerFactory.getLogger(SettlementSummaryQueryClient.class);

    @Autowired(required = false)
    private QuerySettlementSummaryFacade facade;

    public PagedResult query(QuerySettlementSummaryRequest request){
        PageQueryResponse response = facade.querySettlementSummary(request);
        if(null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())){
            logger.info("查询结算汇总信息失败，服务方返回异常，response = {}", response);
        }
        PagedResult pagedResult = response.getPagedResult();
        pagedResult.setDataList(convertList(pagedResult.getDataList()));
        return response.getPagedResult();
    };

    private List convertList(List<MertSettlementSummaryDto> list){
        for (int i = 0; i < list.size(); i++){
            list.get(i).setAutoWithdrawFlag(EnumConverter.convertEnumCode(EnumConverter.AUTO_WITHDRAWAL,list.get(i).getAutoWithdrawFlag()));
            list.get(i).setWithdrawStatus(EnumConverter.convertEnumCode(EnumConverter.WITHDRAW_STATUS,list.get(i).getWithdrawStatus()));
        }
        return list;
    }

}
