package com.ipaylinks.poss.integration.css.liquidation;

import com.ipaylinks.cmp.css.facade.ChannelOrderFacade;
import com.ipaylinks.cmp.css.facade.RepairExceptionProcessFacade;
import com.ipaylinks.cmp.css.facade.dto.ChannelCostDto;
import com.ipaylinks.cmp.css.facade.dto.ChannelOrderDTO;
import com.ipaylinks.cmp.css.facade.request.ChannelOrderQueryRequest;
import com.ipaylinks.cmp.css.facade.request.QueryChannelCostRequest;
import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.integration.common.EnumConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 渠道接口调用层
 *
 * @author hongxu.gao
 * @date 2018/8/21 14:27
 */
@Service
public class ChannelOrderClient {
    private Logger logger = LoggerFactory.getLogger(ChannelOrderClient.class);

    @Autowired(required = false)
    private ChannelOrderFacade channelOrderFacade;

    @Autowired(required = false)
    private RepairExceptionProcessFacade repairExceptionProcessFacade;

    /**
     * 查询渠道流水
     *
     * @return com.ipaylinks.common.page.PagedResult
     * @author hongxu.gao
     * @date 2018/8/29 17:19
     */
    public PagedResult queryChannelOrder(ChannelOrderQueryRequest request) {
        PageQueryResponse response = channelOrderFacade.queryChannelOrder(request);
        if (null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())) {
            logger.info("查询渠道流水单失败，服务方返回异常，response = {}", response);
        }
        PagedResult pagedResult = response.getPagedResult();
        pagedResult.setDataList(convertOrderList(pagedResult.getDataList()));
        return response.getPagedResult();
    }

    /**
     * 查询渠道成本
     *
     * @return com.ipaylinks.common.page.PagedResult
     * @author hongxu.gao
     * @date 2018/8/29 17:19
     */
    public PagedResult queryChannelCost(QueryChannelCostRequest request) {
        PageQueryResponse response = channelOrderFacade.queryChannelCost(request);
        if (null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())) {
            logger.info("查询渠道成本失败，服务方返回异常，response = {}", response);
        }
        PagedResult pagedResult = response.getPagedResult();
        pagedResult.setDataList(convertCostList(pagedResult.getDataList()));
        return response.getPagedResult();
    }

    /**
     * 查询渠道成本
     *
     * @return com.ipaylinks.common.page.PagedResult
     * @author hongxu.gao
     * @date 2018/8/29 17:19
     */
    public BaseResponse channelWaterPush(ChannelOrderQueryRequest request) {
        BaseResponse response = repairExceptionProcessFacade.channelWaterPush(request);
        if (null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())) {
            logger.info("渠道流水推送失败，服务方返回异常，response = {}", response);
        }
        return response;
    }

    private List convertOrderList(List<ChannelOrderDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTransType(TradeTypeEnum.getDescByNumeric(list.get(i).getTransType()));
            list.get(i).setTransStatus(EnumConverter.convertEnumCode(EnumConverter.BIZ_STATUS, list.get(i).getTransStatus()));
            list.get(i).setPayMethod(EnumConverter.convertEnumCode(EnumConverter.PAY_METHOD, list.get(i).getPayMethod()));
        }
        return list;
    }

    private List convertCostList(List<ChannelCostDto> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPayMethod(EnumConverter.convertEnumCode(EnumConverter.PAY_METHOD, list.get(i).getPayMethod()));
            list.get(i).setTransType(TradeTypeEnum.getDescByNumeric(list.get(i).getTransType()));
            list.get(i).setTransStatus(EnumConverter.convertEnumCode(EnumConverter.TRANS_STATUS, list.get(i).getTransStatus()));
            list.get(i).setChargeCostMethod(EnumConverter.convertEnumCode(EnumConverter.CHARGE_METHOD, list.get(i).getChargeCostMethod()));
        }
        return list;
    }

}
