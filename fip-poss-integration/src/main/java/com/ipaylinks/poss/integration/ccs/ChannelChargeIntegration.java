package com.ipaylinks.poss.integration.ccs;

import com.ipaylinks.ccs.facade.biz.ChannelChargeConfigFacade;
import com.ipaylinks.ccs.facade.dto.ChannelChargeStrgDTO;
import com.ipaylinks.ccs.facade.model.ChannelChargeConfigListQueryRequest;
import com.ipaylinks.ccs.facade.model.ChargeQueryByIdRequest;
import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.exception.BaseExceptionCode;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.common.rpc.request.BaseCreateOrUpdateRequest;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.common.rpc.response.SingleResponse;
import com.ipaylinks.poss.exception.InvokeException;
import com.ipaylinks.poss.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hubin.wei
 * @date 2018/8/27 14:47
 **/
@Service
public class ChannelChargeIntegration {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private ChannelChargeConfigFacade channelChargeConfigFacade;


    /**
     * 查询渠道算费策略列表
     * @param request
     * @return
     */
    public PagedResult<ChannelChargeStrgDTO> queryList(ChannelChargeConfigListQueryRequest request) throws Exception{
        log.info("查询渠道算费策略列表的请求参数：[{}]", request.toString());
        PageQueryResponse<ChannelChargeStrgDTO> response = null;
        try {
            response = channelChargeConfigFacade.queryList(request);
        } catch (Exception e) {
            log.error("查询渠道算费策略列表异常----", e);
            throw new InvokeException("调用CCS异常");
        }
        if(ResponseUtil.isFail(response)){
            throw new InvokeException("计费系统返回:"+ response.getResponseMsg());
        }
        return response.getPagedResult();
    };

    /**
     *
     * 调用ccs-新增渠道计费策略
     * @param request
     * @throws Exception
     */
    public void create(BaseCreateOrUpdateRequest<ChannelChargeStrgDTO> request) throws Exception {
        log.info("新增渠道计费策略请求参数：[{}]", request.toString());
        BaseResponse response = null;
        try {
            response = channelChargeConfigFacade.create(request);
        } catch (Exception e) {
            log.error("新增渠道计费策略请求参数异常----", e);
            throw new InvokeException("调用CCS异常");
        }
        if(ResponseUtil.isFail(response)){
            throw new InvokeException("计费系统返回:"+ response.getResponseMsg());
        }
    }


    /**
     * 调用ccs-查询渠道计费策略详情
     * @param request
     * @throws Exception
     */
    public SingleResponse<ChannelChargeStrgDTO> queryById(ChargeQueryByIdRequest request) throws Exception {
        log.info("查询渠道计费策略详情：[{}]", request.toString());
        SingleResponse<ChannelChargeStrgDTO> response = null;
        try {
            response = channelChargeConfigFacade.queryById(request);
        } catch (Exception e) {
            log.error("查询渠道计费策略详情异常----", e);
            throw new InvokeException("调用CCS异常");
        }
        if(ResponseUtil.isFail(response)){
            throw new InvokeException("计费系统返回:"+ response.getResponseMsg());
        }
        return response;
    }



    /**
     *
     * 调用ccs-修改渠道计费策略
     * @param request
     * @throws Exception
     */
    public void updateById(BaseCreateOrUpdateRequest<ChannelChargeStrgDTO> request) throws Exception {
        log.info("修改渠道计费策略：[{}]", request.toString());
        BaseResponse response = null;
        try {
            response = channelChargeConfigFacade.updateByIdSelective(request);
        } catch (Exception e) {
            log.error("修改渠道计费策略异常----", e);
            throw new InvokeException("调用CCS异常");
        }
        if(ResponseUtil.isFail(response)){
            throw new InvokeException("计费系统返回:"+ response.getResponseMsg());
        }
    }

}
