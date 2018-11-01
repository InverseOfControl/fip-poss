package com.ipaylinks.poss.integration.css.liquidation;

import com.ipay.commonService.channel.request.dto.ChannelQueryRequest;
import com.ipay.commonService.channel.response.dto.ChannelQueryResponse;
import com.ipay.commonService.channel.rpc.ChannelQueryRpc;
import com.ipaylinks.common.Country3166_1;
import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.exception.BaseExceptionCode;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.common.rpc.request.BaseCreateOrUpdateRequest;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.common.rpc.response.SingleResponse;
import com.ipaylinks.mcs.facade.MerchantSettlementConfigFacade;
import com.ipaylinks.mcs.facade.dto.MerchantSettlementConfigDto;
import com.ipaylinks.mcs.facade.request.MerchantSettlementConfigRequest;
import com.ipaylinks.poss.integration.common.EnumConverter;
import com.ipaylinks.poss.util.RespUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 商户结算信息配置接口调用层
 *
 * @author hongxu.gao
 * @date 2018/8/21 17:46
 */
@Service
public class MertSettlementConfigClient {
    private Logger logger = LoggerFactory.getLogger(MertSettlementConfigClient.class);

    @Autowired(required = false)
    private MerchantSettlementConfigFacade merchantSettlementConfigFacade;

    @Autowired(required = false)
    private ChannelQueryRpc channelQueryRpc;

    public PagedResult selectList(MerchantSettlementConfigRequest request) {
        PagedResult pagedResult = this.doSelectList(request);
        pagedResult.setDataList(convertList(pagedResult.getDataList()));
        return pagedResult;
    }

    public MerchantSettlementConfigDto selectOne(MerchantSettlementConfigRequest request) {
        PagedResult pagedResult = this.doSelectList(request);
        return (MerchantSettlementConfigDto) pagedResult.getDataList().get(0);
    }

    public BaseResponse add(MerchantSettlementConfigRequest request) {
        BaseCreateOrUpdateRequest<MerchantSettlementConfigRequest> baseRequest = new BaseCreateOrUpdateRequest();
        baseRequest.setRequestObj(request);
        BaseResponse response = merchantSettlementConfigFacade.create(baseRequest);
        if (null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())) {
            logger.info("新增商户结算信息配置失败，服务方返回异常，response = {}", response);
            RespUtils.setToFail(response, BaseExceptionCode.INVOKE_UNKOWN_ERR.getCode(), "新增商户结算配置信息失败");
        }
        return response;
    }

    public BaseResponse edit(MerchantSettlementConfigRequest request) {
        BaseCreateOrUpdateRequest<MerchantSettlementConfigRequest> baseRequest = new BaseCreateOrUpdateRequest();
        baseRequest.setRequestObj(request);
        BaseResponse response = merchantSettlementConfigFacade.modify(baseRequest);
        if (null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())) {
            logger.info("编辑商户结算信息配置失败，服务方返回异常，response = {}", response);
            RespUtils.setToFail(response, BaseExceptionCode.INVOKE_UNKOWN_ERR.getCode(), "编辑商户结算配置信息失败");
        }
        return response;
    }

    /**
     * 查询渠道信息
     *
     * @return com.ipaylinks.common.rpc.response.SingleResponse
     * @author hongxu.gao
     * @date 2018/9/4 14:29
     */
    public SingleResponse queryChannelInfo() {
        SingleResponse response = new SingleResponse();
        ChannelQueryRequest channelQueryRequest = new ChannelQueryRequest();
        channelQueryRequest.setDisplayForChannel(1);
        ChannelQueryResponse channelQueryResponse = channelQueryRpc.invoke(channelQueryRequest);
        if (null == channelQueryResponse || !"0000".equals(channelQueryResponse.getResponseCode())) {
            logger.info("查询渠道信息失败，服务方返回异常，response = {}", channelQueryResponse);
            RespUtils.setToFail(response, BaseExceptionCode.INVOKE_UNKOWN_ERR.getCode(), "查询渠道信息失败");
        }
        //POrganization
        response.setResponseObj(channelQueryResponse.getResult());
        RespUtils.setToSuccess(response);
        return response;
    }

    public BaseResponse delete(MerchantSettlementConfigRequest request) {
        BaseCreateOrUpdateRequest<MerchantSettlementConfigRequest> baseRequest = new BaseCreateOrUpdateRequest();
        baseRequest.setRequestObj(request);
        BaseResponse response = merchantSettlementConfigFacade.delete(baseRequest);
        if (null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())) {
            logger.info("删除商户结算信息配置失败，服务方返回异常，response = {}", response);
            RespUtils.setToFail(response, BaseExceptionCode.INVOKE_UNKOWN_ERR.getCode(), "删除商户结算配置信息失败");
        }
        return response;
    }


    private PagedResult doSelectList(MerchantSettlementConfigRequest request) {
        PageQueryResponse<MerchantSettlementConfigDto> response = merchantSettlementConfigFacade.queryList(request);
        if (null == response || !BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())) {
            logger.info("查询商户结算信息配置失败，服务方返回异常，response = {}", response);
        }
        return response.getPagedResult();
    }

    private List convertList(List<MerchantSettlementConfigDto> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSettlementCycle(EnumConverter.convertEnumCode(EnumConverter.SETTLEMENT_CYCLE, list.get(i).getSettlementCycle()));
            list.get(i).setSettlementDayType(EnumConverter.convertEnumCode(EnumConverter.SETTLEMENT_DAY_TYPE, list.get(i).getSettlementDayType()));
            // 支付方式
            String paymentMethods = Objects.toString(list.get(i).getPaymentMethods(), "");
            if (!StringUtils.isEmpty(paymentMethods)) {
                if (paymentMethods.equals("*")) list.get(i).setPaymentMethods("全部");
                else list.get(i).setPaymentMethods(EnumConverter.convertEnumCode(EnumConverter.PAY_METHOD, paymentMethods));
            }
            // 国家
            String countryCodes = Objects.toString(list.get(i).getCountryCodes(), "");
            if (!StringUtils.isEmpty(countryCodes)) {
                if (countryCodes.equals("*")) list.get(i).setCountryCodes("全部");
                else list.get(i).setCountryCodes(Country3166_1.getByLetter2(countryCodes).getCountryCN());
            }
            // 渠道
            String channelCodes = Objects.toString(list.get(i).getChannelCodes(), "");
            if (!StringUtils.isEmpty(channelCodes)) {
                if (channelCodes.equals("*")) list.get(i).setChannelCodes("全部");
                else list.get(i).setChannelCodes(EnumConverter.convertChannelCode(channelCodes));
            }
        }
        return list;
    }

}
