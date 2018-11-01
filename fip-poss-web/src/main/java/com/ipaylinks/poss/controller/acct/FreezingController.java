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

import com.ipaylinks.acct.facade.dto.BalanceDetailDto;
import com.ipaylinks.acct.facade.enums.AcctFreezeTypeEnum;
import com.ipaylinks.acct.facade.enums.AcctUserTypeEnum;
import com.ipaylinks.acct.facade.request.FreezeRequest;
import com.ipaylinks.acct.facade.request.QueryBalanceDetailsReq;
import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.AjaxResult;
import com.ipaylinks.poss.common.Constants;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.dal.domain.crm.Member;
import com.ipaylinks.poss.helper.PageBeanHelper;
import com.ipaylinks.poss.integration.acct.AccountManageIntegration;
import com.ipaylinks.poss.util.DateUtil;
import com.ipaylinks.poss.util.StringUtil;

/**
 * 冻结管理
 * @author Jerry Chen
 * @date 2018年8月28日
 */
@Controller
@RequestMapping("/acct/freezing")
public class FreezingController {
	private Logger logger = LoggerFactory.getLogger(FreezingController.class);
	@Autowired
	private AccountManageIntegration accountManageClient;
	
	@RequestMapping
	public String index() {
		return "/acct/freezing";
	}
	/**
	 * 冻结解冻管理：查询列表
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
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
	
	@RequestMapping("/form")
	public void form(String accountNo, String freezeType, Model model)throws Exception{
		BalanceDetailDto balanceDetailDto = accountManageClient.queryBalanceDetail(accountNo);
		Map<String, String> map = BeanUtil.beanToStringMap(balanceDetailDto);
		model.addAttribute("balanceDetail", map);
		model.addAttribute("freezeType", freezeType);
	}
	
	@RequestMapping({ "/save", "/update" })
	@ResponseBody
	public AjaxResult save(HttpServletRequest request) {
		AjaxResult ajaxResult = new AjaxResult();
		String accountNo = request.getParameter("accountNo");
		String freezingSummary = request.getParameter("freezingSummary");
		String amount = request.getParameter("amount");
		String freezingType = request.getParameter("freezeType");
		FreezeRequest req = new FreezeRequest();
		req.setAccountNo(new BigDecimal(accountNo));
		req.setFreezingSummary(freezingSummary);
		req.setAmount(new BigDecimal(amount));
		req.setFreezingType("freeze".equals(freezingType)?AcctFreezeTypeEnum.FREEZE.getCode():AcctFreezeTypeEnum.UNFREEZE.getCode());
		Member member = (Member) request.getSession().getAttribute(Constants.SESSION_MEMBER_KEY);
		req.setOperator(member.getUserName());
		try {
			accountManageClient.freeze(req);
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
