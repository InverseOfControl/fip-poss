package com.ipaylinks.poss.integration.css.liquidation;

import com.ipaylinks.cmp.css.facade.QueryMerchantFeeFacade;
import com.ipaylinks.cmp.css.facade.dto.MertFeeDto;
import com.ipaylinks.cmp.css.facade.enums.AccountStatusEnum;
import com.ipaylinks.cmp.css.facade.enums.ChargeMethodEnum;
import com.ipaylinks.cmp.css.facade.enums.SettleEnum;
import com.ipaylinks.cmp.css.facade.request.QueryMerchantFeeRequest;
import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.enums.FinanceTypeEnum;
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
import java.util.Objects;

/**
 * 商户手续费查询接口调用层
 *
 * @author hongxu.gao
 * @date 2018/8/29 11:17
 */
@Service
public class MertFeeQueryClient {
    private Logger logger = LoggerFactory.getLogger(MertFeeQueryClient.class);

    @Autowired(required = false)
    private QueryMerchantFeeFacade facade;

    public PagedResult query(QueryMerchantFeeRequest request){
        PageQueryResponse response = facade.queryMerchantFee(request);
        if(null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())){
            logger.info("查询商户手续费失败，服务方返回异常，response = {}", response);
        }
        PagedResult pagedResult = response.getPagedResult();
        pagedResult.setDataList(convertList(pagedResult.getDataList()));
        return response.getPagedResult();
    };

    public BaseResponse charge(QueryMerchantFeeRequest request){
        BaseResponse response = facade.charge(request);
        if(null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())){
            logger.info("收费失败，服务方返回异常，response = {}", response);
        }
        return response;
    };

    private List convertList(List<MertFeeDto> list){
        for (int i = 0; i < list.size(); i++){
            String feeSettleMethod = Objects.toString(list.get(i).getFeeSettleMethod(),"");
            String accountingStatus = Objects.toString(list.get(i).getAccountingStatus(),"");
            if(feeSettleMethod.equals(ChargeMethodEnum.INNER.getCode())){
                if(accountingStatus.equals(AccountStatusEnum.SUCCESS.getCode())){
                    list.get(i).setSettleStatus(SettleEnum.SETTLED.getDesc());
                }else{
                    list.get(i).setSettleStatus(SettleEnum.OUTSTANDING.getDesc());
                }
            }else{
                list.get(i).setSettleStatus(EnumConverter.convertEnumCode(EnumConverter.SETTLE_STATUS,list.get(i).getSettleStatus()));
            }
            list.get(i).setTransType(TradeTypeEnum.getDescByNumeric(list.get(i).getTransType()));
            list.get(i).setAmountType(FinanceTypeEnum.getByCode(list.get(i).getAmountType()).getMessage());
            list.get(i).setFeeSettleMethod(EnumConverter.convertEnumCode(EnumConverter.CHARGE_METHOD,list.get(i).getFeeSettleMethod()));
        }
        return list;
    }

}
