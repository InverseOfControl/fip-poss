package com.ipaylinks.poss.controller.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipaylinks.acct.facade.dto.AccountDto;
import com.ipaylinks.acct.facade.enums.AcctUserTypeEnum;
import com.ipaylinks.acct.facade.request.QueryAccountsReq;
import com.ipaylinks.acct.facade.request.UpdateAccountReq;
import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.AjaxResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.helper.PageBeanHelper;
import com.ipaylinks.poss.integration.acct.AccountManageIntegration;
import com.ipaylinks.poss.util.DateUtil;
import com.ipaylinks.poss.util.StringUtil;

/**
 * 账户信息管理
 * @author Jerry Chen
 * @date 2018年8月28日
 */
@Controller
@RequestMapping("/acct/account")
public class AccountController {
	private Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountManageIntegration accountManageClient;

	@RequestMapping
	public String index() {
		return "/acct/account";
	}

	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
		logger.info("查询账户列表");
		QueryAccountsReq req = buildReq(request);
		req.setPageBean(PageBeanHelper.build(request));
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			PagedResult<AccountDto> pageResult = accountManageClient.listAccounts(req);
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

	private QueryAccountsReq buildReq (HttpServletRequest request) {
		QueryAccountsReq req = new QueryAccountsReq();
		req.setUserId(request.getParameter("userId"));
		req.setUserName(request.getParameter("userName"));
		req.setUserType(request.getParameter("userType"));
		req.setAccountNo(StringUtil.str2BigDecimal(request.getParameter("accountNo")));
		req.setTitleNo(StringUtil.str2Long(request.getParameter("titleNo")));
		req.setAccountCurrecny(request.getParameter("accountCurrecny"));
		req.setOpenStartTime(StringUtil.str2StartDate(request.getParameter("openStartTime")));
		req.setOpenEndTime(StringUtil.str2EndDate(request.getParameter("openEndTime")));
		return req;
	}
	
	private List<Map<String, String>> convertList(List<AccountDto> accountDtos) {
		List<Map<String, String>> list = new ArrayList<>();
		for (AccountDto account : accountDtos) {
			Map<String, String> map = BeanUtil.beanToStringMap(account);
			String userType = AcctUserTypeEnum.getMessageByCode(account.getUserType());
			map.put("userType", userType);
			map.put("openTime", DateUtil.formatTime(account.getOpenTime()));
			map.put("updateTime", DateUtil.formatTime(account.getUpdateTime()));
			list.add(map);
		}
		return list;
	}

	@RequestMapping("/form")
	public void form(String accountNo, Model model)throws Exception{
		logger.info("查询账户信息");
		AccountDto accountDto = accountManageClient.queryAccount(accountNo);
		Map<String, String> map = BeanUtil.beanToStringMap(accountDto);
		model.addAttribute("account", map);
	}

	@RequestMapping({ "/save", "/update" })
	@ResponseBody
	public AjaxResult save(HttpServletRequest request) {
		logger.info("修改账户信息");
		AjaxResult ajaxResult = new AjaxResult();
		String accountNo = request.getParameter("accountNo");
		String inLimit = request.getParameter("inLimit");
		String outLimit = request.getParameter("outLimit");
		UpdateAccountReq req = new UpdateAccountReq();
		req.setAccountNo(new BigDecimal(accountNo));
		req.setInLimit(inLimit);
		req.setOutLimit(outLimit);
		try {
			accountManageClient.updateAccount(req);
			ajaxResult.setSuccess(true);
			ajaxResult.setMessage("修改成功");
		} catch (Exception e) {
			logger.error("修改账户异常:", e);
			ajaxResult.setSuccess(false);
			ajaxResult.setMessage(e.getMessage());
		}
		return ajaxResult;
	}
}
