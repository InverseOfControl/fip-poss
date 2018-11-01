package com.ipaylinks.poss.controller.account;

import com.ipaylinks.accounting.facade.enums.CrDrEnum;
import com.ipaylinks.accounting.facade.model.TAcctEntryVo;
import com.ipaylinks.accounting.facade.request.BaseQueryRequest;
import com.ipaylinks.acct.facade.dto.BalanceDetailDto;
import com.ipaylinks.acct.facade.enums.AcctUserTypeEnum;
import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.enums.FinanceTypeEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.common.AjaxResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.controller.system.RoleController;
import com.ipaylinks.poss.integration.TAcctTitleBizIntegration;
import com.ipaylinks.poss.util.DateUtil;
import com.ipaylinks.poss.util.DateUtils;
import com.ipaylinks.poss.util.DownLoadUtil;
import com.ipaylinks.poss.util.SimpleExcelGenerator;
import com.ipaylinks.verify.facade.enums.AccountingStatusEnum;
import com.ipaylinks.verify.facade.enums.ReconHandleResultEnum;
import com.ipaylinks.verify.facade.enums.ReconResultEnum;
import com.ipaylinks.verify.facade.req.ArsChannelFileRecordRequest;
import com.ipaylinks.verify.facade.resp.ArsChannelFileRecordResponse;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.catalina.connector.Request;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制器
 *
 * @author gson
 */
@Controller
@RequestMapping("/account/accountEntry")
public class AccountingController {
    private static final String EXCEL_FILE_TITLE = "会计分录记录";

    Logger logger = Logger.getLogger(RoleController.class);

    @Autowired TAcctTitleBizIntegration tAcctTitleBizIntegration;

    @RequestMapping
    public String index() {
        return "/account/accountEntry";
    }

    /**
     * 会计分录查询
     * @param page
     * @param rows
     * @param titleNo
     * @param accountNo
     * @param currency
     * @param voucharNo
     * @param txnOrderNo
     * @param drCr
     * @param orderType
     * @param financeType
     * @param orgCode
     * @param startDay
     * @param endDay
     * @param startCompletionTime
     * @param endCompletionTime
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid<TAcctEntryVo> list(int page, int rows, String titleNo, String accountNo, String currency,
        String voucharNo, String txnOrderNo, String drCr, String orderType, String financeType, String orgCode, String startDay, String endDay,
        String startCompletionTime, String endCompletionTime) {
        DataGrid<TAcctEntryVo> dataGrid = new DataGrid<>();
        try {
            logger.info("查询会计分录");
            BaseQueryRequest<TAcctEntryVo> request = new BaseQueryRequest<>();
            PageBean pageBean = new PageBean();
            pageBean.setPageSize(rows);
            pageBean.setPageNumber(page);
            request.setPageBean(pageBean);
            PageQueryResponse<TAcctEntryVo> response =
                gettAcctEntryVoPageQueryResponse(titleNo, accountNo, currency, voucharNo, txnOrderNo, drCr, orderType, financeType, orgCode, startDay, endDay,
                    startCompletionTime, endCompletionTime, request);
            PagedResult<TAcctEntryVo> pageResult = response.getPagedResult();
            if (pageResult != null) {
                dataGrid.setTotal(pageResult.getTotal());
                pageResult.getDataList().parallelStream().forEach(dto -> {
                    setEntry(dto);
                });
                dataGrid.setRows(pageResult.getDataList());
            }
            logger.info("查询会计分录，记录数:" + dataGrid.getTotal());
        }catch (Exception e) {
            dataGrid.setErrorMsg(e.getMessage());
        }
        return dataGrid;
    }


    /**
     * 下载
     * @param titleNo
     * @param accountNo
     * @param currency
     * @param voucharNo
     * @param txnOrderNo
     * @param drCr
     * @param orderType
     * @param financeType
     * @param orgCode
     * @param startDay
     * @param endDay
     * @param startCompletionTime
     * @param endCompletionTime
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/download")
    @ResponseBody
    public AjaxResult downLoad(String titleNo, String accountNo, String currency,
        String voucharNo, String txnOrderNo, String drCr, String orderType, String financeType, String orgCode, String startDay, String endDay,
        String startCompletionTime, String endCompletionTime, final HttpServletResponse response, final HttpServletRequest request) {
        try {
            BaseQueryRequest<TAcctEntryVo> request1 = new BaseQueryRequest<>();
            PageQueryResponse<TAcctEntryVo> pageResponse =
                gettAcctEntryVoPageQueryResponse(titleNo, accountNo, currency, voucharNo, txnOrderNo, drCr, orderType, financeType, orgCode, startDay, endDay,
                    startCompletionTime, endCompletionTime, request1);
            List<TAcctEntryVo> list;
             if (pageResponse != null && BaseRespStatusEnum.SUCCESS.getCode().equals(pageResponse.getResponseStatus()) && null != pageResponse
                .getPagedResult()) {
                list = pageResponse.getPagedResult().getDataList();
                list.parallelStream().forEach(dto -> {
                    setEntry(dto);
                });
            } else {
                return new AjaxResult(false, "会计分录记录查询失败");
            }
            String[] headers =
                new String[] {"会计日期", "会计凭证号", "订单号", "科目号", "科目名称", "账户号'", "借贷标志", "币种", "金额", "账户资金方向", "支付服务码", "交易类型", "金额类型", "渠道号", "记账时间", "订单完成时间"};
            String[] fields =
                new String[] {"accountingDay", "voucharNo", "txnOrderNo", "titleNo", "titleName", "accountNo", "drCr", "currency", "amount", "acctDrCr",
                    "psCode", "orderType", "financeType", "orgCode", "screateTime", "scompletionTime"};
            Workbook grid = SimpleExcelGenerator.generateGrid(EXCEL_FILE_TITLE, headers, fields, list);
            String fileName = DownLoadUtil.encodeFileName(EXCEL_FILE_TITLE + DateUtils.getCurrentDate(), request.getHeader("User-Agent"));
            response.setContentType("application/force-download");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            grid.write(response.getOutputStream());
        } catch (Exception e) {
            logger.error("会计分录记录下载失败" + e);
            return new AjaxResult(false, "会计分录记录下载异常");
        }
        return new AjaxResult("会计分录下载成功");
    }

    private void setEntry(TAcctEntryVo dto) {
        String crdrName = CrDrEnum.getByCode(dto.getDrCr()).getName();
        String acctDrCr = CrDrEnum.getByCode(dto.getAcctDrCr()).getName();
        String orderType1 = TradeTypeEnum.getDescByNumeric(dto.getOrderType());
        String financeType1 = FinanceTypeEnum.getMessage(dto.getFinanceType());
        dto.setDrCr(crdrName);
        dto.setAccountNoS(String.valueOf(dto.getAccountNo()));
        dto.setAcctDrCr(acctDrCr);
        dto.setOrderType(orderType1);
        dto.setFinanceType(financeType1);
        dto.setScompletionTime(DateUtil.formatTime(dto.getCompletionTime()));
        dto.setScreateTime(DateUtil.formatTime(dto.getCreateTime()));
    }

    private PageQueryResponse<TAcctEntryVo> gettAcctEntryVoPageQueryResponse(String titleNo, String accountNo, String currency, String voucharNo,
        String txnOrderNo, String drCr, String orderType, String financeType, String orgCode, String startDay, String endDay, String startCompletionTime,
        String endCompletionTime, BaseQueryRequest<TAcctEntryVo> request1) {
        TAcctEntryVo vo = new TAcctEntryVo();
        if (!StringUtils.isEmpty(titleNo)) {
            vo.setTitleNo(Long.valueOf(titleNo));
        }
        if (!StringUtils.isEmpty(accountNo)) {
            vo.setAccountNo(new BigDecimal(accountNo));
        }
        if (!StringUtils.isEmpty(currency)) {
            vo.setCurrency(currency);
        }
        if (!StringUtils.isEmpty(voucharNo)) {
            vo.setVoucharNo(voucharNo);
        }
        if (!StringUtils.isEmpty(txnOrderNo)) {
            vo.setTxnOrderNo(txnOrderNo);
        }
        if (!StringUtils.isEmpty(drCr)) {
            vo.setDrCr(drCr);
        }
        if (!StringUtils.isEmpty(orderType)) {
            vo.setOrderType(orderType);
        }
        if (!StringUtils.isEmpty(financeType)) {
            vo.setFinanceType(financeType);
        }
        if (!StringUtils.isEmpty(orgCode)) {
            vo.setOrgCode(orgCode);
        }
        if (!StringUtils.isEmpty(startDay)) {
            vo.setStartDay(Integer.valueOf(startDay.replace("-", "")));
        }
        if (!StringUtils.isEmpty(endDay)) {
            vo.setEndDay(Integer.valueOf(endDay.replace("-", "")));
        }
        if (!StringUtils.isEmpty(startCompletionTime)) {
            vo.setStartCompletionTime(startCompletionTime);
        }
        if (!StringUtils.isEmpty(endCompletionTime)) {
            vo.setEndCompletionTime(endCompletionTime);
        }
        request1.setRequestObj(vo);
        return tAcctTitleBizIntegration.queryEntry(request1);
    }

}
