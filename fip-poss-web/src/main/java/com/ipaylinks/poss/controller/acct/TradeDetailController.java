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

import com.ipaylinks.acct.facade.dto.TradeDetailDto;
import com.ipaylinks.acct.facade.enums.AcctBookTypeEnum;
import com.ipaylinks.acct.facade.request.QueryTradeDetailsReq;
import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.enums.FinanceTypeEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.helper.PageBeanHelper;
import com.ipaylinks.poss.integration.acct.AccountingFacadeIntegration;
import com.ipaylinks.poss.util.DateUtil;
import com.ipaylinks.poss.util.DateUtils;
import com.ipaylinks.poss.util.DownLoadUtil;
import com.ipaylinks.poss.util.SimpleExcelGenerator;
import com.ipaylinks.poss.util.StringUtil;
/**
 * 收支明细（外部流水）
 * @author Jerry Chen
 * @date 2018年8月28日
 */
@Controller
@RequestMapping("/acct/tradeDetail")
public class TradeDetailController {
	private Logger logger = LoggerFactory.getLogger(TradeDetailController.class);
	
	@Autowired
	private AccountingFacadeIntegration accountingFacadeIntegration;
	
	@RequestMapping
	public String index() {
		return "/acct/tradeDetail";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> listTradeDetails(HttpServletRequest request) {
		logger.info("调用acct查询账户收支明细");
		QueryTradeDetailsReq req = buildReq(request);
		req.setPageBean(PageBeanHelper.build(request));
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			PagedResult<TradeDetailDto> pageResult = accountingFacadeIntegration.listTradeDetails(req);
			if (pageResult != null) {
				dataGrid.setTotal(pageResult.getTotal());
				dataGrid.setRows(convertList(pageResult.getDataList()));
			}
			logger.info("调用acct查询账户收支明细，记录数:" + dataGrid.getTotal());
		} catch (Exception e) {
			dataGrid.setErrorMsg(e.getMessage());
		}
		return dataGrid;
	}
	
	@RequestMapping("/download")
	public void downloadTradeDetails(HttpServletRequest request,HttpServletResponse response) {
		logger.info("下载账户收支明细");
		QueryTradeDetailsReq req = buildReq(request);
		List<Map<String, String>> list = new ArrayList<>();
		try {
			PagedResult<TradeDetailDto> pageResult = accountingFacadeIntegration.listTradeDetails(req);
			if (pageResult != null) {
				list = convertList(pageResult.getDataList());
			}
			logger.info("下载账户收支明细，记录数："+list.size());
			String[] headers = new String[]{"请求流水号","商户号","会计日期","账户号","币种","收支金额","收单订单号","商户订单号","订单类型","金额类型","渠道号","支付服务码","记账类型","创建时间","记账时间","操作员","系统CODE","日志id","交易流水号"};
            String[] fields = new String[]{"requestId","userId","accountingDay","accountNo","currency","amount","sysTraceNo","merchantOrderId","orderType","financeType","orgCode","psCode","bookType","createTime","txnTime","operator","sysCode","pipeLogId","detailId"};
            String fileName = "账户收支明细_" + DateUtils.toFormatDateString(new Date(), DateUtils.MAIL_DATE_FORMAT)+".xls";
            fileName = DownLoadUtil.encodeFileName(fileName, request.getHeader("User-Agent"));
            Workbook grid = SimpleExcelGenerator.generateGrid("账户收支明细",headers, fields, list);
            response.setContentType("application/force-download");
            response.setContentType("applicationnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            grid.write(response.getOutputStream());
		} catch (Exception e) {
			logger.info("下载账户收支明细异常,",e);
		}
	}
	
	private QueryTradeDetailsReq buildReq(HttpServletRequest request){
		QueryTradeDetailsReq req = new QueryTradeDetailsReq();
		req.setPipeLogId(StringUtil.str2BigDecimal(request.getParameter("pipeLogId")));
		req.setFinanceType(request.getParameter("financeType"));
		req.setBookType(request.getParameter("bookType"));
		req.setOrderType(request.getParameter("orderType"));
		req.setMerchantOrderId(request.getParameter("merchantOrderId"));
		req.setSysTraceNo(request.getParameter("sysTraceNo"));
		req.setAccountNo(StringUtil.str2BigDecimal(request.getParameter("accountNo")));
		req.setBeginAccountingDay(StringUtil.str2AccountingDay(request.getParameter("beginAccountingDay")));
		req.setEndAccountingDay(StringUtil.str2AccountingDay(request.getParameter("endAccountingDay")));
		req.setRequestId(request.getParameter("requestId"));
		req.setUserId(request.getParameter("userId"));
		return req;
	}
	private List<Map<String, String>> convertList(List<TradeDetailDto> tradeDetails) {
		List<Map<String, String>> list = new ArrayList<>();
		for (TradeDetailDto detail : tradeDetails) {
			if("-".equals(detail.getIrDirection())){
				detail.setAmount(detail.getAmount().negate());
			}
			Map<String, String> map = BeanUtil.beanToStringMap(detail);
			map.put("bookType",  AcctBookTypeEnum.getMessageByCode(detail.getBookType()));
			map.put("orderType",  TradeTypeEnum.getDescByNumeric(detail.getOrderType()));
			map.put("financeType",  FinanceTypeEnum.getMessage(detail.getFinanceType()));
			map.put("createTime", DateUtil.formatTime(detail.getCreateTime()));
			map.put("updateTime", DateUtil.formatTime(detail.getUpdateTime()));
			map.put("txnTime", DateUtil.formatTime(detail.getTxnTime()));
			list.add(map);
		}
		return list;
	}
}
