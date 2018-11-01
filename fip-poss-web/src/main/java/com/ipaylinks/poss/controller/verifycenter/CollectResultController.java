/**
 *
 */
package com.ipaylinks.poss.controller.verifycenter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.integration.verifycenter.TradeReconQueryFacadeIntegration;
import com.ipaylinks.verify.facade.req.TradeReconTotalRequest;
import com.ipaylinks.verify.facade.resp.TradeReconTotalResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 对账结果相关操作控制器类，列表结果查询
 */
@Controller
@RequestMapping("/traderecon/verifyCollectResult")
public class CollectResultController {

    /**
     * 日志
     */
    Logger logger = Logger.getLogger(CollectResultController.class);

    /**
     * 汇总接口调用
     */
    @Autowired
    TradeReconQueryFacadeIntegration tradeReconQueryFacadeClient;


    /**
     * 初始化也没跳转
     *
     * @return 页面
     */
    @RequestMapping
    public void index() {
    }

    /**
     * 对账结果汇总
     *
     * @param page    页数
     * @param rows    每页条数
     * @param request 请求对象
     * @return 查询结果
     */
    @ResponseBody
    @RequestMapping("/totalList")
    public DataGrid list(int page, int rows, TradeReconTotalRequest request) {
        PageBean pageBean = new PageBean();
        pageBean.setPageSize(rows);
        pageBean.setPageNumber(page);
        request.setPageBean(pageBean);
        logger.info(request);
        PageQueryResponse<TradeReconTotalResponse> resp = tradeReconQueryFacadeClient.queryCorrectionResultTotal(request);
        return new DataGrid<>(resp);
    }
}
