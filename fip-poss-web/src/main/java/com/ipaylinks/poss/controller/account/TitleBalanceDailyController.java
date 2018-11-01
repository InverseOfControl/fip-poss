package com.ipaylinks.poss.controller.account;

import com.ipaylinks.accounting.facade.enums.CheckEntryResultEnum;
import com.ipaylinks.accounting.facade.enums.CrDrEnum;
import com.ipaylinks.accounting.facade.model.TAcctEntryVo;
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
import java.util.List;

/**
 * 科目历史余额表
 *
 * @author gson
 */
@Controller
@RequestMapping("/account/balanceDaily")
@Transactional(readOnly = true)
public class TitleBalanceDailyController {
    private static final String EXCEL_FILE_TITLE = "科目历史余额表";
    Logger logger = Logger.getLogger(RoleController.class);

    @Autowired
    TAcctTitleBizIntegration tAcctTitleBizIntegration;



    @RequestMapping
    public String index() {
        return "/account/balanceDaily";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid<TAcctTitleDailyVo> list(int page, int rows, String titleNo,  String currency,
        String checkBalance,String startDay,String endDay) {
        DataGrid<TAcctTitleDailyVo> dataGrid = new DataGrid<>();
        try {
            logger.info("查询科目历史余额表");
        BaseQueryRequest<TAcctTitleDailyVo> request =new BaseQueryRequest<>();
        PageBean pageBean=new PageBean();
        pageBean.setPageSize(rows);
        pageBean.setPageNumber(page);
        request.setPageBean(pageBean);
        PageQueryResponse<TAcctTitleDailyVo> response = gettAcctTitleDailyVoPageQueryResponse(titleNo, currency, checkBalance, startDay, endDay, request);

        PagedResult<TAcctTitleDailyVo> pageResult=response.getPagedResult();
        if (pageResult != null) {
            dataGrid.setTotal(pageResult.getTotal());
            pageResult.getDataList().parallelStream().forEach(dto -> {
                String crdrName1 = CrDrEnum.getByCode(dto.getDrCr()).getName();
                dto.setDrCr(crdrName1);
                dto.setSupdateTime(DateUtil.formatTime(dto.getUpdateTime()));
                dto.setScreateTime(DateUtil.formatTime(dto.getCreateTime()));
            });
            dataGrid.setRows(pageResult.getDataList());
        }
            logger.info("查询科目历史余额表，记录数:" + dataGrid.getTotal());
        } catch (Exception e) {
            dataGrid.setErrorMsg(e.getMessage());
        }
        return dataGrid;
    }

    private PageQueryResponse<TAcctTitleDailyVo> gettAcctTitleDailyVoPageQueryResponse(String titleNo, String currency, String checkBalance, String startDay,
        String endDay, BaseQueryRequest<TAcctTitleDailyVo> request) {
        TAcctTitleDailyVo vo=new TAcctTitleDailyVo();
        if(!StringUtils.isEmpty(titleNo)) {
            vo.setTitleNo(Long.valueOf(titleNo));
        }
        if(!StringUtils.isEmpty(currency)) {
            vo.setCurrency(currency);
        }
        if(!StringUtils.isEmpty(checkBalance)) {
            vo.setCheckBalance(checkBalance);
        }
        if(!StringUtils.isEmpty(startDay)) {
            vo.setStartDay(Integer.valueOf(startDay.replace("-","")));
        }
        if(!StringUtils.isEmpty(endDay)) {
            vo.setEndDay(Integer.valueOf(endDay.replace("-","")));
        }

        request.setRequestObj(vo);

        return tAcctTitleBizIntegration.queryBalanceDaily(request);
    }

    @RequestMapping("/download")
    @ResponseBody
    public AjaxResult downLoad(String titleNo,  String currency,
        String checkBalance,String startDay,String endDay, final HttpServletResponse response, final HttpServletRequest request) {
        try {
            BaseQueryRequest<TAcctTitleDailyVo> request1 = new BaseQueryRequest<>();
            PageQueryResponse<TAcctTitleDailyVo> pageResponse = gettAcctTitleDailyVoPageQueryResponse(titleNo, currency, checkBalance, startDay, endDay, request1);

            List<TAcctTitleDailyVo> list;
            if (pageResponse != null && BaseRespStatusEnum.SUCCESS.getCode().equals(pageResponse.getResponseStatus()) && null != pageResponse
                .getPagedResult()) {
                list = pageResponse.getPagedResult().getDataList();
                list.parallelStream().forEach(dto -> {
                    String crdrName1 = CrDrEnum.getByCode(dto.getDrCr()).getName();
                    dto.setDrCr(crdrName1);
                    dto.setSupdateTime(DateUtil.formatTime(dto.getUpdateTime()));
                    dto.setScreateTime(DateUtil.formatTime(dto.getCreateTime()));
                });
            } else {
                return new AjaxResult(false, "科目历史余额表查询失败");
            }

            String[] headers =
                new String[] {"会计日期", "科目号", "科目名称", "账户号", "余额方向", "币种", "期初借方余额", "期初借方人民币余额", "期初贷方余额", "期初贷方人民币余额", "借方发生额", "借方发生额人民币金额", "贷方发生额",
                    "贷方发生额人民币金额", "期末借方余额", "期末借方人民币余额", "期末贷方余额", "期末贷方人民币余额", "创建时间", "试算结果"};
            String[] fields =
                new String[] {"accountingDay", "titleNo", "titleName", "accountNo", "drCr", "currency", "drOpenBalance", "drOpenBalanceCny", "crOpenBalance",
                    "crOpenBalanceCny", "drOccurrenceAmount", "drOccurrenceAmountCny", "crOpenBalanceCny", "crOccurrenceAmountCny", "drCloseBalance",
                    "drCloseBalanceCny", "crCloseBalance", "crCloseBalanceCny", "screateTime", "checkBalance"};
            Workbook grid = SimpleExcelGenerator.generateGrid(EXCEL_FILE_TITLE, headers, fields, list);
            String fileName = DownLoadUtil.encodeFileName(EXCEL_FILE_TITLE + DateUtils.getCurrentDate(), request.getHeader("User-Agent"));
            response.setContentType("application/force-download");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            grid.write(response.getOutputStream());
        } catch (Exception e) {
            logger.error("科目历史余额表下载失败" + e);
            return new AjaxResult(false, "科目历史余额表下载异常");
        } return new AjaxResult("科目历史余额表下载成功");
    }
}
