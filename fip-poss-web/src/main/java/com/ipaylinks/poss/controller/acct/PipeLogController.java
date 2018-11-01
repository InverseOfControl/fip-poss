package com.ipaylinks.poss.controller.acct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipaylinks.acct.facade.dto.PipeLogDto;
import com.ipaylinks.acct.facade.enums.AcctBookTypeEnum;
import com.ipaylinks.acct.facade.enums.AcctPipeLogStatusTypeEnum;
import com.ipaylinks.acct.facade.request.QueryPipeLogsReq;
import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.enums.ProdCodeEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.helper.PageBeanHelper;
import com.ipaylinks.poss.integration.acct.AccountingFacadeIntegration;
import com.ipaylinks.poss.util.DateUtil;
import com.ipaylinks.poss.util.StringUtil;
/**
 * 记账日志管理
 * @author Jerry Chen
 * @date 2018年9月11日
 */
@Controller
@RequestMapping("/acct/pipeLog")
public class PipeLogController {
	private Logger logger = LoggerFactory.getLogger(PipeLogController.class);
	@Autowired
	private AccountingFacadeIntegration accountingFacadeIntegration;
	
	@RequestMapping
	public String index() {
		return "/acct/pipeLog";
	}
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
		logger.info("查询记账规则列表");
		QueryPipeLogsReq req = buildReq(request);
		req.setPageBean(PageBeanHelper.build(request));
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			PagedResult<PipeLogDto> pageResult = accountingFacadeIntegration.listPipeLogs(req);
			if (pageResult != null) {
				dataGrid.setTotal(pageResult.getTotal());
				dataGrid.setRows(convertList(pageResult.getDataList()));
			}
			logger.info("查询记账规则列表，记录数:" + dataGrid.getTotal());
		} catch (Exception e) {
			dataGrid.setErrorMsg(e.getMessage());
		}
		return dataGrid;
	}
	private List<Map<String, String>> convertList(List<PipeLogDto> tmpList) {
		List<Map<String, String>> list = new ArrayList<>();
		for (PipeLogDto tmp : tmpList) {
			Map<String, String> map = BeanUtil.beanToStringMap(tmp);
			map.put("orderType",TradeTypeEnum.getDescByNumeric(tmp.getOrderType()));
			map.put("bookType",AcctBookTypeEnum.getMessageByCode(tmp.getBookType()));
			map.put("status", AcctPipeLogStatusTypeEnum.getMessageByCode(tmp.getStatus()));
			map.put("txnTime", DateUtil.formatTime(tmp.getTxnTime()));
			map.put("createTime", DateUtil.formatTime(tmp.getCreateTime()));
			map.put("completeTime",DateUtil.formatTime(tmp.getCompleteTime()));
			list.add(map);
		}
		return list;
	}
	private QueryPipeLogsReq buildReq(HttpServletRequest request){
		QueryPipeLogsReq req = new QueryPipeLogsReq();
		req.setPipeLogId(StringUtil.str2BigDecimal(request.getParameter("pipeLogId")));
		req.setProdCode(ProdCodeEnum.CODE_10.getCode());
		req.setSysCode(request.getParameter("sysCode"));
		req.setRequestId(request.getParameter("requestId"));
		req.setMerchantOrderId(request.getParameter("merchantOrderId"));
		req.setTxnOrderNo(request.getParameter("txnOrderNo"));
		req.setOrderType(request.getParameter("orderType"));
		req.setSysTraceNo(request.getParameter("sysTraceNo"));
		req.setBookType(request.getParameter("bookType"));
		req.setStatus(request.getParameter("status"));
		req.setBeginAccountingDay(StringUtil.str2AccountingDay(request.getParameter("beginAccountingDay")));
		req.setEndAccountingDay(StringUtil.str2AccountingDay(request.getParameter("endAccountingDay")));
		return req;
	}
}
