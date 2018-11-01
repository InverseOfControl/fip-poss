/**
 *
 */
package com.ipaylinks.poss.controller.verifycenter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.common.AjaxResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.integration.verifycenter.TradeReconQueryFacadeIntegration;
import com.ipaylinks.verify.facade.enums.AuditModeEnum;
import com.ipaylinks.verify.facade.req.TradeReconResultRequest;
import com.ipaylinks.verify.facade.resp.TradeReconResultResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author dev
 *         对账结果相关操作控制器类，列表结果查询
 */
@Controller
@RequestMapping("/traderecon/tradereconresult")
public class TradeReconResultController {
    Logger logger = Logger.getLogger(TradeReconResultController.class);

    @Autowired
    TradeReconQueryFacadeIntegration tradeReconQueryFacadeClient;

    @RequestMapping
    public String index(String batchNo, String type, Model model) {
        if (batchNo != null && type != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                TradeReconResultResponse tradeRecon = new TradeReconResultResponse();
                tradeRecon.setBatchNo(batchNo);
                tradeRecon.setReconResult("0" + type);
                tradeRecon.setChannelOrderId("");
                model.addAttribute("tradeRecon", mapper.writeValueAsString(tradeRecon));
            } catch (JsonProcessingException e) {
                logger.error("json转换错误", e);
            }
        }
        return "traderecon/tradereconresult";
    }

    /**
     * 对账结果明细列表
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(int page, int rows, TradeReconResultRequest request) {
        PageBean pageBean = new PageBean();
        pageBean.setPageSize(rows);
        pageBean.setPageNumber(page);
        request.setPageBean(pageBean);
        PageQueryResponse<TradeReconResultResponse> resp = tradeReconQueryFacadeClient.queryCorrectionResult(request);
        return new DataGrid<>(resp);
    }

    /**
     * 对账结果差错处理方式
     *
     * @param ids            主键
     * @param correctionType 差错类型
     * @param userName       用户
     * @return 处理结果
     */
    @RequestMapping("/initialAudit")
    @Transactional
    @ResponseBody
    public AjaxResult initialAudit(String ids, String correctionType, String userName) {
        BaseResponse resp = new BaseResponse();
        if (StringUtils.isBlank(correctionType)) {
            resp.setResponseMsg("初审-差错处理方式不能为空!");
            return new AjaxResult(false, resp.getResponseMsg());
        }
        if (StringUtils.isBlank(ids)) {
            resp.setResponseMsg("初审-订单主键不能为空!");
            return new AjaxResult(false, resp.getResponseMsg());
        }
        String id[] = ids.split(",");
        int errCount = 0;
        Boolean flag = false;
        for (String str : id) {
            try {
                String auditMode = AuditModeEnum.INITIAL_AUDIT.getCode();
                resp = tradeReconQueryFacadeClient.dealInitialAudit(new BigDecimal(str), correctionType, userName, auditMode);
                if (!BaseRespStatusEnum.SUCCESS.getCode().equals(resp.getResponseStatus())) {
                    errCount++;
                    flag = true;
                }
            } catch (Exception e) {
                errCount++;
                flag = true;
            }
        }
        if (!flag) {
            return new AjaxResult(true, resp.getResponseMsg());
        } else {
            resp.setResponseMsg("共有笔" + errCount + "订单初审不成功!");
            return new AjaxResult(false, resp.getResponseMsg());
        }
    }

    /**
     * 对账结果差错处理方式
     *
     * @param ids            主键
     * @param correctionType 差错类型
     * @param userName       用户
     * @return 处理结果
     */
    @RequestMapping("/repeatAudit")
    @Transactional
    @ResponseBody
    public AjaxResult repeatAudit(String ids, String correctionType, String userName) {
        BaseResponse resp = new BaseResponse();
        String auditMode = AuditModeEnum.REPEAT_AUDIT.getCode();
        if (StringUtils.isBlank(ids)) {
            resp.setResponseMsg("复审-订单主键不能为空!");
            return new AjaxResult(false, resp.getResponseMsg());
        }
        String id[] = ids.split(",");
        int errCount = 0;
        Boolean flag = false;
        for (String str : id) {
            try {
                resp = tradeReconQueryFacadeClient.dealRepeatAudit(new BigDecimal(str), correctionType, userName, auditMode);

                if (!BaseRespStatusEnum.SUCCESS.getCode().equals(resp.getResponseStatus())) {
                    errCount++;
                    flag = true;
                }
            } catch (Exception e) {
                errCount++;
                flag = true;
            }
        }
        if (!flag) {
            return new AjaxResult(true, resp.getResponseMsg());
        } else {
            resp.setResponseMsg("共有笔" + errCount + "订单复审不成功!");
            return new AjaxResult(false, resp.getResponseMsg());
        }
    }
}
