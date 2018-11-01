package com.ipaylinks.poss.integration.ccs;

import com.ipaylinks.ccs.facade.biz.MerchChargeConfigFacade;
import com.ipaylinks.ccs.facade.dto.MerchChargeStrgDTO;
import com.ipaylinks.ccs.facade.model.ChargeQueryByIdRequest;
import com.ipaylinks.ccs.facade.model.MerchChargeConfigListQueryRequest;
import com.ipaylinks.common.exception.BaseExceptionCode;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.common.rpc.request.BaseCreateOrUpdateRequest;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.common.rpc.response.SingleResponse;
import com.ipaylinks.poss.exception.InvokeException;
import com.ipaylinks.poss.util.PossException;
import com.ipaylinks.poss.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hubin.wei
 * @date 2018/8/20 17:17
 **/
@Service
public class MerchChargeIntegration {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private MerchChargeConfigFacade merchChargeConfigFacade;

    /**
     * 查询商户算费策略列表
     * @param request
     * @return
     */
    public PagedResult<MerchChargeStrgDTO> queryList(MerchChargeConfigListQueryRequest request) throws Exception{
        log.info("查询商户算费策略列表的请求参数：[{}]", request.toString());
        PageQueryResponse<MerchChargeStrgDTO> response = null;
        try {
            response = merchChargeConfigFacade.queryList(request);
        } catch (Exception e) {
            log.error("查询商户算费策略列表异常----", e);
            throw new InvokeException("调用CCS异常");
        }
        if(ResponseUtil.isFail(response)){
            throw new InvokeException("计费系统返回:"+ response.getResponseMsg());
        }
        return response.getPagedResult();
    };

    /**
     *
     * 调用ccs-新增商户计费策略
     * @param request
     * @throws Exception
     */
    public void create(BaseCreateOrUpdateRequest<MerchChargeStrgDTO> request) throws Exception {
        log.info("新增商户计费策略请求参数：[{}]", request.toString());
        BaseResponse response = null;
        try {
            response = merchChargeConfigFacade.create(request);
        } catch (Exception e) {
            log.error("新增商户计费策略请求参数异常----", e);
            throw new PossException("0000","调用ccs异常");
        }
        if(ResponseUtil.isFail(response)){
            throw new InvokeException("计费系统返回:"+ response.getResponseMsg());
        }
    }


    /**
     * 调用ccs-查询商户计费策略详情
     * @param request
     * @throws Exception
     */
    public SingleResponse<MerchChargeStrgDTO> queryById(ChargeQueryByIdRequest request) throws Exception {
        log.info("查询商户计费策略详情：[{}]", request.toString());
        SingleResponse<MerchChargeStrgDTO> response = null;
        try {
            response = merchChargeConfigFacade.queryById(request);
        } catch (Exception e) {
            log.error("查询商户计费策略详情异常----", e);
            throw new InvokeException("调用CCS异常");
        }
        if(ResponseUtil.isFail(response)){
            throw new InvokeException("计费系统返回:"+ response.getResponseMsg());
        }
        return response;
    }



    /**
     *
     * 调用ccs-修改商户计费策略
     * @param request
     * @throws Exception
     */
    public void updateById(BaseCreateOrUpdateRequest<MerchChargeStrgDTO> request) throws Exception {
        log.info("修改商户计费策略：[{}]", request.toString());
        BaseResponse response = null;
        try {
            response = merchChargeConfigFacade.updateByIdSelective(request);
        } catch (Exception e) {
            log.error("修改商户计费策略异常----", e);
            throw new InvokeException("调用CCS异常");
        }
        if(ResponseUtil.isFail(response)){
            throw new InvokeException("计费系统返回:"+ response.getResponseMsg());
        }
    }




}
