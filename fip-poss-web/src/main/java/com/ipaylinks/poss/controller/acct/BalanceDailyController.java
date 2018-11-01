package com.ipaylinks.poss.controller.acct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipaylinks.acct.facade.dto.BalanceDailyDto;
import com.ipaylinks.acct.facade.enums.AcctCheckEntryStatusEnum;
import com.ipaylinks.acct.facade.request.QueryBalanceDailysReq;
import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.helper.PageBeanHelper;
import com.ipaylinks.poss.integration.acct.AccountManageIntegration;
import com.ipaylinks.poss.util.DateUtil;
import com.ipaylinks.poss.util.DateUtils;
import com.ipaylinks.poss.util.DownLoadUtil;
import com.ipaylinks.poss.util.SimpleExcelGenerator;
import com.ipaylinks.poss.util.StringUtil;
/**
 * 账户历史余额
 * @author Jerry Chen
 * @date 2018年8月28日
 */
@Controller
@RequestMapping("/acct/balanceDaily")
public class BalanceDailyController {
	private Logger logger = LoggerFactory.getLogger(BalanceDailyController.class);
	
	@Autowired
	private AccountManageIntegration accountManageClient;
	
	@RequestMapping
	public String index() {
		return "/acct/balanceDaily";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
		logger.info("查询账户历史余额");
		QueryBalanceDailysReq req = buildReq(request);
		req.setPageBean(PageBeanHelper.build(request));
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			PagedResult<BalanceDailyDto> pageResult = accountManageClient.listBalanceDailys(req);
			if (pageResult != null) {
				dataGrid.setTotal(pageResult.getTotal());
				dataGrid.setRows(convertList(pageResult.getDataList()));
			}
			logger.info("查询账户列表，记录数:" + dataGrid.getTotal());
		} catch (Exception e) {
			dataGrid.setErrorMsg(e.getMessage());
		}
		return dataGrid;
	}
	@RequestMapping("/download")
	public void download(HttpServletRequest request,HttpServletResponse response) {
		logger.info("下载账户历史余额");
		QueryBalanceDailysReq req = buildReq(request);
		List<Map<String, String>> list = new ArrayList<>();
		try {
			PagedResult<BalanceDailyDto> pageResult = accountManageClient.listBalanceDailys(req);
			if (pageResult != null) {
				list = convertList(pageResult.getDataList());
			}
			logger.info("下载账户历史余额，记录数:"+list.size());
			String[] headers = new String[]{"会计日期","账户号","币种","期初余额","收入金额","支出金额","期末余额","和会计核对结果","和会计核对结果描述","创建时间","更新时间","完成时间"};
            String[] fields = new String[]{"accountingDay","accountNo","currency","openingBalance","incomeAmount","costAmount","closingAmount","checkEntryResult","checkEntryMsg","createTime","updateTime","completeTime"};
            String fileName = "账户历史余额_" + DateUtils.toFormatDateString(new Date(), DateUtils.MAIL_DATE_FORMAT)+".xls";
            fileName = DownLoadUtil.encodeFileName(fileName, request.getHeader("User-Agent"));
            Workbook grid = SimpleExcelGenerator.generateGrid("账户历史余额",headers, fields, list);
            response.setContentType("application/force-download");
            response.setContentType("applicationnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            grid.write(response.getOutputStream());
		} catch (Exception e) {
			logger.info("下载账户历史余额异常,",e);
		}
	}
	private QueryBalanceDailysReq buildReq(HttpServletRequest request){
		QueryBalanceDailysReq req = new QueryBalanceDailysReq();
		req.setAccountNo(StringUtil.str2BigDecimal(request.getParameter("accountNo")));
		req.setBeginAccountingDay(StringUtil.str2AccountingDay(request.getParameter("beginAccountingDay")));
		req.setEndAccountingDay(StringUtil.str2AccountingDay(request.getParameter("endAccountingDay")));
		return req;
	}
	private List<Map<String, String>> convertList(List<BalanceDailyDto> balanceDailys) {
		List<Map<String, String>> list = new ArrayList<>();
		for (BalanceDailyDto daily : balanceDailys) {
			Map<String, String> map = BeanUtil.beanToStringMap(daily);
			map.put("createTime", DateUtil.formatTime(daily.getCreateTime()));
			map.put("updateTime", DateUtil.formatTime(daily.getUpdateTime()));
			map.put("completeTime", DateUtil.formatTime(daily.getCompleteTime()));
			map.put("checkEntryResult", AcctCheckEntryStatusEnum.getMessageByCode(daily.getCheckEntryResult()));
			list.add(map);
		}
		return list;
	}
}
