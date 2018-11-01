package com.ipaylinks.poss.integration.css.liquidation;

import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.exception.BaseExceptionCode;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.mcs.facade.MerchantSettlementBankConfigFacade;
import com.ipaylinks.mcs.facade.dto.MerchantSettlementBankConfigDto;
import com.ipaylinks.mcs.facade.request.MerchantSettlementBankConfigRequest;
import com.ipaylinks.poss.util.RespUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商户结算银行信息配置接口调用层
 *
 * @author hongxu.gao
 * @date 2018/8/21 17:46
 */
@Service
public class MertSettlementBankConfigClient {

    private Logger logger = LoggerFactory.getLogger(MertSettlementBankConfigClient.class);

    @Autowired(required = false)
    private MerchantSettlementBankConfigFacade facade;

    public PagedResult selectList(MerchantSettlementBankConfigRequest request){
        PagedResult pagedResult = this.doSelectList(request);;
        pagedResult.setDataList(convertList(pagedResult.getDataList()));
        return pagedResult;
    };

   public MerchantSettlementBankConfigDto selectOne(MerchantSettlementBankConfigRequest request){
        PagedResult pagedResult = this.doSelectList(request);
        return (MerchantSettlementBankConfigDto)pagedResult.getDataList().get(0);
    };

    public BaseResponse add(MerchantSettlementBankConfigRequest request){
        BaseResponse response = facade.create(request);
        if(null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())){
            logger.info("新增商户结算银行账户信息失败，服务方返回异常，response = {}", response);
            RespUtils.setToFail(response, BaseExceptionCode.INVOKE_UNKOWN_ERR.getCode(), "新增商户结算银行账户信息失败");
        }
        return response;
    };

    public BaseResponse edit(MerchantSettlementBankConfigRequest request){
        BaseResponse response = facade.modify(request);
        if(null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())){
            logger.info("编辑商户结算银行账户信息失败，服务方返回异常，response = {}", response);
            RespUtils.setToFail(response, BaseExceptionCode.INVOKE_UNKOWN_ERR.getCode(), "编辑商户结算银行账户信息失败");
        }
        return response;
    };

    public BaseResponse delete(MerchantSettlementBankConfigRequest request){
        BaseResponse response = facade.delete(request);
        if(null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())){
            logger.info("删除商户结算银行账户信息失败，服务方返回异常，response = {}", response);
            RespUtils.setToFail(response, BaseExceptionCode.INVOKE_UNKOWN_ERR.getCode(), "删除商户结算银行账户信息失败");
        }
        return response;
    };

    private PagedResult doSelectList(MerchantSettlementBankConfigRequest request){
        PageQueryResponse<MerchantSettlementBankConfigDto> response = facade.queryList(request);
        if(null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())){
            logger.info("查询商户结算银行配置信息失败，服务方返回异常，response = {}", response);
        }
        return response.getPagedResult();
    };

    private List convertList(List<MerchantSettlementBankConfigDto> list){
        return list;
    }
}
