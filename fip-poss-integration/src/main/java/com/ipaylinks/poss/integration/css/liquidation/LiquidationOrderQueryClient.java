package com.ipaylinks.poss.integration.css.liquidation;

import com.ipaylinks.cmp.css.facade.LiquidationOrderFacade;
import com.ipaylinks.cmp.css.facade.dto.LiquidationOrderDto2;
import com.ipaylinks.cmp.css.facade.dto.LiquidationSubOrderDTO;
import com.ipaylinks.cmp.css.facade.request.LiquidationOrderQueryRequest;
import com.ipaylinks.cmp.css.facade.request.LiquidationSubOrderQueryRequest;
import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.integration.common.EnumConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 清算订单查询接口
 *
 * @author hongxu.gao
 * @date 2018/8/21 14:26
 */
@Service
public class LiquidationOrderQueryClient {
    private Logger logger = LoggerFactory.getLogger(LiquidationOrderQueryClient.class);

    @Autowired(required = false)
    private LiquidationOrderFacade liquidationOrderFacade;

    public PagedResult query(LiquidationOrderQueryRequest request) {
        PageQueryResponse response = liquidationOrderFacade.queryLiquidationOrder(request);
        if (null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())) {
            logger.info("查询清算订单失败，服务方返回异常，response = {}", response);
        }
        PagedResult pagedResult = response.getPagedResult();
        pagedResult.setDataList(convertList(pagedResult.getDataList()));
        return response.getPagedResult();
    }

    public PagedResult querySubOrder(LiquidationSubOrderQueryRequest request) {
        PageQueryResponse response = liquidationOrderFacade.queryLiquidationSubOrder(request);
        if (null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())) {
            logger.info("查询清算子订单失败，服务方返回异常，response = {}", response);
        }
        PagedResult pagedResult = response.getPagedResult();
        pagedResult.setDataList(convertSubList(pagedResult.getDataList()));
        return response.getPagedResult();
    }

    private List convertList(List<LiquidationOrderDto2> list) {
        for (int i = 0; i < list.size(); i++) {
            String oriPayOrderId = Objects.toString(list.get(i).getOriPayOrderId(),"");
            list.get(i).setOriPayOrderId(oriPayOrderId.replaceAll("null",""));
            list.get(i).setTransType(TradeTypeEnum.getDescByNumeric(list.get(i).getTransType()));
            list.get(i).setTransStatus(EnumConverter.convertEnumCode(EnumConverter.TRANS_STATUS, list.get(i).getTransStatus()));
            list.get(i).setPayMethod(EnumConverter.convertEnumCode(EnumConverter.PAY_METHOD, list.get(i).getPayMethod()));
        }
        return list;
    }

    private List convertSubList(List<LiquidationSubOrderDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPayMethod(EnumConverter.convertEnumCode(EnumConverter.PAY_METHOD, list.get(i).getPayMethod()));
            list.get(i).setPayStatus(EnumConverter.convertEnumCode(EnumConverter.BIZ_STATUS, list.get(i).getPayStatus()));
            list.get(i).setAccountingStatus(EnumConverter.convertEnumCode(EnumConverter.ACCOUNT_STATUS, list.get(i).getAccountingStatus()));
        }
        return list;
    }
}
