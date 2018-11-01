package com.ipaylinks.poss.controller.account;

import com.ipaylinks.accounting.facade.model.TAcctEntryVo;
import com.ipaylinks.accounting.facade.model.TAcctTitleBalanceDailyVo;
import com.ipaylinks.accounting.facade.model.TAcctTitleDailyVo;
import com.ipaylinks.accounting.facade.request.BaseQueryRequest;
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
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会计账户余额表
 *
 * @author gson
 */
@Controller
@RequestMapping("/account/balanceAcctDaily")
public class BalanceAcctDailyController {
    private static final String EXCEL_FILE_TITLE = "会计账户余额表";
    Logger logger = Logger.getLogger(RoleController.class);

    @Autowired TAcctTitleBizIntegration tAcctTitleBizIntegration;

    @RequestMapping
    public String index() {
        return "/account/balanceAcctDaily";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid<TAcctTitleBalanceDailyVo> list(int page, int rows, String accountNo, String startDay,
        String endDay) {

        DataGrid<TAcctTitleBalanceDailyVo> dataGrid = new DataGrid<>();
        try {
            logger.info("查询会计账户余额表");
            BaseQueryRequest<TAcctTitleBalanceDailyVo> request = new BaseQueryRequest<>();
            PageBean pageBean = new PageBean();
            pageBean.setPageSize(rows);
            pageBean.setPageNumber(page);
            request.setPageBean(pageBean);
            PageQueryResponse<TAcctTitleBalanceDailyVo> response = gettAcctTitleBalanceDailyVoPageQueryResponse(accountNo, startDay, endDay, request);
            PagedResult<TAcctTitleBalanceDailyVo> pageResult = response.getPagedResult();
            if (pageResult != null) {
                dataGrid.setTotal(pageResult.getTotal());
                pageResult.getDataList().parallelStream().forEach(dto -> {
                    dto.setSupdateTime(DateUtil.formatTime(dto.getUpdateTime()));
                    dto.setScreateTime(DateUtil.formatTime(dto.getCreateTime()));
                    dto.setAccountNoS(String.valueOf(dto.getAccountNo()));
                });
                dataGrid.setRows(pageResult.getDataList());
            }
            logger.info("查询会计账户余额表，记录数:" + dataGrid.getTotal());
        } catch (Exception e) {
            dataGrid.setErrorMsg(e.getMessage());
        }
        return dataGrid;
    }

    /**
     * 按照参数查询
     * @param accountNo
     * @param startDay
     * @param endDay
     * @param request
     * @return
     */
    private PageQueryResponse<TAcctTitleBalanceDailyVo> gettAcctTitleBalanceDailyVoPageQueryResponse(String accountNo, String startDay, String endDay,
        BaseQueryRequest<TAcctTitleBalanceDailyVo> request) {
        TAcctTitleBalanceDailyVo vo = new TAcctTitleBalanceDailyVo();
        if (!StringUtils.isEmpty(accountNo)) {
            vo.setAccountNo(new BigDecimal(accountNo));
        }
        if (!StringUtils.isEmpty(startDay)) {
            vo.setStartDay(Integer.valueOf(startDay.replace("-", "")));
        }
        if (!StringUtils.isEmpty(endDay)) {
            vo.setEndDay(Integer.valueOf(endDay.replace("-", "")));
        }
        request.setRequestObj(vo);
        return tAcctTitleBizIntegration.queryBalanceAcctDaily(request);
    }

    /**
     * 下载
     * @param accountNo
     * @param startDay
     * @param endDay
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/download")
    @ResponseBody
    public AjaxResult downLoad(String accountNo, String startDay,
        String endDay, final HttpServletResponse response, final HttpServletRequest request) {
        try {
            BaseQueryRequest<TAcctTitleBalanceDailyVo> request1 = new BaseQueryRequest<>();
            PageQueryResponse<TAcctTitleBalanceDailyVo> pageResponse = gettAcctTitleBalanceDailyVoPageQueryResponse(accountNo, startDay, endDay, request1);
            List<TAcctTitleBalanceDailyVo> list;
            if (pageResponse != null && BaseRespStatusEnum.SUCCESS.getCode().equals(pageResponse.getResponseStatus()) && null != pageResponse
                .getPagedResult()) {
                list = pageResponse.getPagedResult().getDataList();
                list.parallelStream().forEach(dto -> {
                    dto.setSupdateTime(DateUtil.formatTime(dto.getUpdateTime()));
                    dto.setScreateTime(DateUtil.formatTime(dto.getCreateTime()));
                });
            } else {
                return new AjaxResult(false, "会计账户余额表查询失败");
            }

            String[] headers =
                new String[] {"会计日期", "账户号",  "币种", "期初余额", "收入金额", "支出金额", "期末余额", "创建时间", "最后更新时间"};
            String[] fields =
                new String[] {"accountingDay", "accountNo", "currency", "openingAmount", "incomeAmount", "costAmount", "closingAmount", "screateTime", "supdateTime"};
            Workbook grid = SimpleExcelGenerator.generateGrid(EXCEL_FILE_TITLE, headers, fields, list);
            String fileName = DownLoadUtil.encodeFileName(EXCEL_FILE_TITLE + DateUtils.getCurrentDate(), request.getHeader("User-Agent"));
            response.setContentType("application/force-download");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            grid.write(response.getOutputStream());
        } catch (Exception e) {
            logger.error("会计账户余额表下载失败" + e);
            return new AjaxResult(false, "会计账户余额表下载异常");
        }
        return new AjaxResult("会计账户余额表下载成功");
    }
}
