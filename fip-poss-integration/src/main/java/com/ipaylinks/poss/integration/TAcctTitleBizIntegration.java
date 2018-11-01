/**
 * 会计服务查询
 * @author
 * @create 2018-08-20 17:40
 **/
package com.ipaylinks.poss.integration;

import com.ipaylinks.accounting.facade.biz.TAcctTitleBalanceBizFacade;
import com.ipaylinks.accounting.facade.biz.TAcctTitleBizFacade;
import com.ipaylinks.accounting.facade.model.TAcctEntryVo;
import com.ipaylinks.accounting.facade.model.TAcctTitleBalanceCompareVo;
import com.ipaylinks.accounting.facade.model.TAcctTitleBalanceDailyVo;
import com.ipaylinks.accounting.facade.model.TAcctTitleDailyVo;
import com.ipaylinks.accounting.facade.request.BaseQueryRequest;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  PACKAGENAME: com.ipaylinks.poss.integration
 *  Description:会计查询服务
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  @author mmzhang
 *  @create 2018/8/20  
 *
 */
@Component("tAcctTitleBizIntegration")
public class TAcctTitleBizIntegration {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TAcctTitleBizFacade tAcctTitleBizFacade;
    @Autowired
    private TAcctTitleBalanceBizFacade tAcctTitleBalanceBizFacade;

    /**
     * 查询会计分录
     * @param request
     * @return
     */
    public PageQueryResponse<TAcctEntryVo> queryEntry(BaseQueryRequest<TAcctEntryVo> request) {
        logger.info("查询会计分录的请求参数：[{}]", request.toString());
        PageQueryResponse<TAcctEntryVo>  response = null;
        try {
            response = tAcctTitleBizFacade.queryEntry(request);
        } catch (Exception e) {
            logger.error("未查询到对应的分录,request{}",request, e);
        }
        logger.info("查询会计分录的结果：[{}]", response.toString());
        return  response;
    }
    /**
     * 查询科目历史余额表
     * @param request
     * @return
     */
    public PageQueryResponse<TAcctTitleDailyVo> queryBalanceDaily(BaseQueryRequest<TAcctTitleDailyVo> request) {
        logger.info("查询科目历史余额表的请求参数：[{}]", request.toString());
        PageQueryResponse<TAcctTitleDailyVo>  response = null;
        try {
            response = tAcctTitleBalanceBizFacade.queryBalanceDaily(request);
        } catch (Exception e) {
            logger.error("未查询科目历史余额表,request{}",request, e);
        }
        logger.info("查询科目历史余额表的结果：[{}]", response.toString());
        return  response;
    }
    /**
     * 查询科目历史余额表
     * @param request
     * @return
     */
    public PageQueryResponse<TAcctTitleDailyVo> queryBalanceSubsidiary(BaseQueryRequest<TAcctTitleDailyVo> request) {
        logger.info("查询科目历史余额表的请求参数：[{}]", request.toString());
        PageQueryResponse<TAcctTitleDailyVo>  response = null;
        try {
            response = tAcctTitleBalanceBizFacade.queryBalanceSubsidiary(request);
        } catch (Exception e) {
            logger.error("未查询科目历史余额表,request{}",request, e);
        }
        logger.info("查询科目历史余额表的结果：[{}]", response.toString());
        return  response;
    }
    /**
     * 账户会计核对结果查询
     * @param request
     * @return
     */
    public PageQueryResponse<TAcctTitleBalanceCompareVo> queryTitleBalanceCompare(BaseQueryRequest<TAcctTitleBalanceCompareVo> request) {
        logger.info("查询科目历史余额表的请求参数：[{}]", request.toString());
        PageQueryResponse<TAcctTitleBalanceCompareVo>  response = null;
        try {
            response = tAcctTitleBalanceBizFacade.queryTitleBalanceCompare(request);
        } catch (Exception e) {
            logger.error("未查询科目历史余额表,request{}",request, e);
        }
        logger.info("查询科目历史余额表的结果：[{}]", response.toString());
        return  response;
    }
    /**
     * 账户会计核对结果查询
     * @param request
     * @return
     */
    public PageQueryResponse<TAcctTitleBalanceDailyVo> queryBalanceAcctDaily(BaseQueryRequest<TAcctTitleBalanceDailyVo> request) {
        logger.info("查询科目历史余额表的请求参数：[{}]", request.toString());
        PageQueryResponse<TAcctTitleBalanceDailyVo>  response = null;
        try {
            response = tAcctTitleBalanceBizFacade.queryBalanceAcctDaily(request);
        } catch (Exception e) {
            logger.error("未查询科目历史余额表,request{}",request, e);
        }
        logger.info("查询科目历史余额表的结果：[{}]", response.toString());
        return  response;
    }
}
