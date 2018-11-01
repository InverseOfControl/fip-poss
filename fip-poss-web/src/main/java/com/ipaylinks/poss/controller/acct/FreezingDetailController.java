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

import com.ipaylinks.acct.facade.dto.FreezingDto;
import com.ipaylinks.acct.facade.enums.AcctFreezeStatusEnum;
import com.ipaylinks.acct.facade.enums.AcctFreezeTypeEnum;
import com.ipaylinks.acct.facade.request.QueryFreezingDetailsReq;
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
 * 账户冻结解冻明细
 * @author Jerry Chen
 * @date 2018年8月29日
 */
@Controller
@RequestMapping("/acct/freezingDetail")
public class FreezingDetailController {

	private Logger logger = LoggerFactory.getLogger(FreezingDetailController.class);
	
	@Autowired
	private AccountManageIntegration accountManageClient;
	
	@RequestMapping
	public String index() {
		return "/acct/freezingDetail";
	}
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
		logger.info("查询账户余额列表");
		QueryFreezingDetailsReq req = buildReq(request);
		req.setPageBean(PageBeanHelper.build(request));
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			PagedResult<FreezingDto> pageResult = accountManageClient.listFreezingDetails(req);
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
		logger.info("下载账户冻结解冻明细");
		QueryFreezingDetailsReq req = buildReq(request);
		List<Map<String, String>> list = new ArrayList<>();
		try {
			PagedResult<FreezingDto> pageResult = accountManageClient.listFreezingDetails(req);
			if (pageResult != null) {
				list = convertList(pageResult.getDataList());
			}
			logger.info("下载账户冻结解冻明细，记录数："+list.size());
			String[] headers = new String[]{"会计日期","流水号","账户号","币种","冻结/解冻","备注","金额","状态","失败原因","操作员","创建时间","完成时间"};
            String[] fields = new String[]{"accountingDay","freezingId","accountNo","currency","freezingType","freezingSummary","amount","status","remark","operator","createTime","completeTime"};
            String fileName = "账户冻结解冻明细_" + DateUtils.toFormatDateString(new Date(), DateUtils.MAIL_DATE_FORMAT)+".xls";
            fileName = DownLoadUtil.encodeFileName(fileName, request.getHeader("User-Agent"));
            Workbook grid = SimpleExcelGenerator.generateGrid("账户冻结解冻明细",headers, fields, list);
            response.setContentType("application/force-download");
            response.setContentType("applicationnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            grid.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("下载账户冻结解冻明细异常,",e);
		}
	}
	private QueryFreezingDetailsReq buildReq(HttpServletRequest request){
		QueryFreezingDetailsReq req = new QueryFreezingDetailsReq();
		req.setAccountNo(StringUtil.str2BigDecimal(request.getParameter("accountNo")));
		req.setFreezingId(StringUtil.str2BigDecimal(request.getParameter("freezingId")));
		req.setFreezingType(request.getParameter("freezingType"));
		req.setBeginAccountingDay(StringUtil.str2AccountingDay(request.getParameter("beginAccountingDay")));
		req.setEndAccountingDay(StringUtil.str2AccountingDay(request.getParameter("endAccountingDay")));
		req.setStatus(request.getParameter("status"));
		return req;
	}
	private List<Map<String, String>> convertList(List<FreezingDto> freezingList) {
		List<Map<String, String>> list = new ArrayList<>();
		for (FreezingDto freezingDto : freezingList) {
			Map<String, String> map = BeanUtil.beanToStringMap(freezingDto);
			map.put("status", AcctFreezeStatusEnum.getMessageByCode(freezingDto.getStatus()));
			map.put("freezingType", AcctFreezeTypeEnum.getMessageByCode(freezingDto.getFreezingType()));
			map.put("createTime", DateUtil.formatTime(freezingDto.getCreateTime()));
			map.put("completeTime", DateUtil.formatTime(freezingDto.getCompleteTime()));
			list.add(map);
		}
		return list;
	}
}
