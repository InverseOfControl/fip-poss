package com.ipaylinks.poss.controller.account;

import com.ipaylinks.accounting.facade.enums.CheckEntryResultEnum;
import com.ipaylinks.accounting.facade.enums.CrDrEnum;
import com.ipaylinks.accounting.facade.model.TAcctEntryVo;
import com.ipaylinks.accounting.facade.model.TAcctTitleBalanceCompareVo;
import com.ipaylinks.accounting.facade.model.TAcctTitleDailyVo;
import com.ipaylinks.accounting.facade.request.BaseQueryRequest;
import com.ipaylinks.common.enums.BaseRespStatusEnum;
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
 * 账户会计核对结果
 *
 * @author gson
 */
@Controller
@RequestMapping("/account/titleBalanceCompare")
public class TitleBalanceCompareController {
    private static final String EXCEL_FILE_TITLE = "账户会计核对结果";

    Logger logger = Logger.getLogger(RoleController.class);

    @Autowired TAcctTitleBizIntegration tAcctTitleBizIntegration;

    @RequestMapping
    public String index() {
        return "/account/titleBalanceCompare";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid<TAcctTitleBalanceCompareVo> list(int page, int rows, String accountNo, String startDay,
        String endDay, String checkEntryResult, String startCreateTime, String endCreateTime) {
        DataGrid<TAcctTitleBalanceCompareVo> dataGrid = new DataGrid<>();
        try {
            logger.info("查询账户会计核对结果");
            BaseQueryRequest<TAcctTitleBalanceCompareVo> request = new BaseQueryRequest<>();
            PageBean pageBean = new PageBean();
            pageBean.setPageSize(rows);
            pageBean.setPageNumber(page);
            request.setPageBean(pageBean);

            PageQueryResponse<TAcctTitleBalanceCompareVo> response =
                gettAcctTitleBalanceCompareVoPageQueryResponse(accountNo, startDay, endDay, checkEntryResult, startCreateTime, endCreateTime, request);

            PagedResult<TAcctTitleBalanceCompareVo> pageResult = response.getPagedResult();
            if (pageResult != null) {
                dataGrid.setTotal(pageResult.getTotal());
                pageResult.getDataList().parallelStream().forEach(dto -> {
                    String checkEntryResult1 = CheckEntryResultEnum.getByCode(dto.getCheckEntryResult()).getName();
                    dto.setScreateTime(DateUtil.formatTime(dto.getCreateTime()));
                    dto.setCheckEntryResult(checkEntryResult1);
                    dto.setAccountNoS(String.valueOf(dto.getAccountNo()));
                });
                dataGrid.setRows(pageResult.getDataList());
            }
            logger.info("查询账户会计核对结果，记录数:" + dataGrid.getTotal());
        } catch (Exception e) {
            dataGrid.setErrorMsg(e.getMessage());
        }
        return dataGrid;
    }

    private PageQueryResponse<TAcctTitleBalanceCompareVo> gettAcctTitleBalanceCompareVoPageQueryResponse(String accountNo, String startDay, String endDay,
        String checkEntryResult, String startCreateTime, String endCreateTime, BaseQueryRequest<TAcctTitleBalanceCompareVo> request) {
        TAcctTitleBalanceCompareVo vo = new TAcctTitleBalanceCompareVo();
        if (!StringUtils.isEmpty(accountNo)) {
            vo.setAccountNo(new BigDecimal(accountNo));
        }
        if (!StringUtils.isEmpty(checkEntryResult)) {
            vo.setCheckEntryResult(checkEntryResult);
        }
        if (!StringUtils.isEmpty(startDay)) {
            vo.setStartDay(Integer.valueOf(startDay.replace("-", "")));
        }
        if (!StringUtils.isEmpty(endDay)) {
            vo.setEndDay(Integer.valueOf(endDay.replace("-", "")));
        }
        if (!StringUtils.isEmpty(startCreateTime)) {
            vo.setStartCreateTime(startCreateTime);
        }
        if (!StringUtils.isEmpty(endCreateTime)) {
            vo.setEndCreateTime(endCreateTime);
        }
        request.setRequestObj(vo);
        return tAcctTitleBizIntegration.queryTitleBalanceCompare(request);
    }

    @RequestMapping("/download")
    @ResponseBody
    public AjaxResult downLoad(String accountNo, String startDay, String endDay, String checkEntryResult,
        String startCreateTime, String endCreateTime, final HttpServletResponse response, final HttpServletRequest request) {
        try {
            BaseQueryRequest<TAcctTitleBalanceCompareVo> request1 = new BaseQueryRequest<>();
            PageQueryResponse<TAcctTitleBalanceCompareVo> pageResponse =
                gettAcctTitleBalanceCompareVoPageQueryResponse(accountNo, startDay, endDay, checkEntryResult, startCreateTime, endCreateTime, request1);
            List<TAcctTitleBalanceCompareVo> list;
            if (pageResponse != null && BaseRespStatusEnum.SUCCESS.getCode().equals(pageResponse.getResponseStatus()) && null != pageResponse
                .getPagedResult()) {
                list = pageResponse.getPagedResult().getDataList();
                list.parallelStream().forEach(dto -> {
                    String checkEntryResult1 = CheckEntryResultEnum.getByCode(dto.getCheckEntryResult()).getName();
                    dto.setScreateTime(DateUtil.formatTime(dto.getCreateTime()));
                    dto.setCheckEntryResult(checkEntryResult1);
                });
            } else {
                return new AjaxResult(false, "账户会计核对结果查询失败");
            }

            String[] headers =
                new String[] {"会计日期", "账户号", "币种", "账户期初余额", "会计期初余额", "账户收入金额", "会计收入金额", "账户支出金额", "会计支出金额", "账户期末余额", "会计期末余额", "核对结果", "核对描述", "创建时间"};
            String[] fields = new String[] {"accountingDay", "accountNo", "currency", "openingAmountBalance", "openingAmountTitle", "incomeAmountBalance",
                "incomeAmountTitle", "costAmountBalance", "costAmountTitle", "closingAmountBalance", "closingAmountTitle", "checkEntryResult", "checkEntryMsg",
                "screateTime"};
            Workbook grid = SimpleExcelGenerator.generateGrid(EXCEL_FILE_TITLE, headers, fields, list);
            String fileName = DownLoadUtil.encodeFileName(EXCEL_FILE_TITLE + DateUtils.getCurrentDate(), request.getHeader("User-Agent"));
            response.setContentType("application/force-download");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            grid.write(response.getOutputStream());
        } catch (Exception e) {
            logger.error("账户会计核对结果下载失败" + e);
            return new AjaxResult(false, "账户会计核对结果下载异常");
        }
        return new AjaxResult("账户会计核对结果下载成功");
    }
}
