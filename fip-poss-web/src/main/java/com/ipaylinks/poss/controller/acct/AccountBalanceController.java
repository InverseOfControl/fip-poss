package com.ipaylinks.poss.controller.acct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipaylinks.acct.facade.dto.BalanceDetailDto;
import com.ipaylinks.acct.facade.enums.AcctUserTypeEnum;
import com.ipaylinks.acct.facade.request.QueryBalanceDetailsReq;
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
 * 账户余额管理
 * @author Jerry Chen
 * @date 2018年8月28日
 */
@Controller
@RequestMapping("/acct/balance")
public class AccountBalanceController {
	private Logger logger = LoggerFactory.getLogger(AccountBalanceController.class);
	
	@Autowired
	private AccountManageIntegration accountManageClient;
	
	@RequestMapping
	public String index() {
		return "/acct/balance";
	}
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request,HttpSession session) {
		logger.info("查询账户余额列表");
		QueryBalanceDetailsReq req = buildReq(request);
		req.setPageBean(PageBeanHelper.build(request));
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			PagedResult<BalanceDetailDto> pageResult = accountManageClient.listBalanceDetails(req);
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
		logger.info("下载账户余额列表");
		QueryBalanceDetailsReq req = buildReq(request);
		List<Map<String, String>> list = new ArrayList<>();
		try {
			PagedResult<BalanceDetailDto> pageResult = accountManageClient.listBalanceDetails(req);
			if (pageResult != null) {
				list = convertList(pageResult.getDataList());
			}
			logger.info("下载账户余额列表，记录数:"+list.size());
			String[] headers = new String[]{"用户号","用户名称","账户号","账户类型","账户币种","余额","冻结金额","可用余额","开户时间","最后更新时间"};
            String[] fields = new String[]{"userId","userName","accountNo","accountDesc","accountCurrecny","balanceAmount","freezingAmount","availableAmount","createTime","updateTime"};
            String fileName = "账户余额_" + DateUtils.toFormatDateString(new Date(), DateUtils.MAIL_DATE_FORMAT)+".xls";
            fileName = DownLoadUtil.encodeFileName(fileName, request.getHeader("User-Agent"));
            Workbook grid = SimpleExcelGenerator.generateGrid("账户余额",headers, fields, list);
            response.setContentType("application/force-download");
            response.setContentType("applicationnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            grid.write(response.getOutputStream());
		} catch (Exception e) {
			logger.info("下载账户余额列表异常,",e);
		}
	}
	private QueryBalanceDetailsReq buildReq(HttpServletRequest request){
		QueryBalanceDetailsReq req = new QueryBalanceDetailsReq();
		req.setUserId(request.getParameter("userId"));
		req.setUserName(request.getParameter("userName"));
		req.setUserType(request.getParameter("userType"));
		req.setAccountNo(StringUtil.str2BigDecimal(request.getParameter("accountNo")));
		req.setTitleNo(StringUtil.str2Long(request.getParameter("titleNo")));
		req.setAccountCurrecny(request.getParameter("accountCurrecny"));
		return req;
	}
	private List<Map<String, String>> convertList(List<BalanceDetailDto> accountDetails) {
		List<Map<String, String>> list = new ArrayList<>();
		for (BalanceDetailDto balanceDetail : accountDetails) {
			Map<String, String> map = BeanUtil.beanToStringMap(balanceDetail);
			String userType = AcctUserTypeEnum.getMessageByCode(balanceDetail.getUserType());
			map.put("userType", userType);
			map.put("createTime", DateUtil.formatTime(balanceDetail.getCreateTime()));
			map.put("updateTime", DateUtil.formatTime(balanceDetail.getUpdateTime()));
			list.add(map);
		}
		return list;
	}
}
