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

import com.ipaylinks.acct.facade.dto.RuleDto;
import com.ipaylinks.acct.facade.enums.AcctAccountTypeEnum;
import com.ipaylinks.acct.facade.enums.AcctRuleStatusEnum;
import com.ipaylinks.acct.facade.request.QueryRulesReq;
import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.enums.CurrencyTypeEnum;
import com.ipaylinks.common.enums.FinanceTypeEnum;
import com.ipaylinks.common.enums.ProdCodeEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.helper.PageBeanHelper;
import com.ipaylinks.poss.integration.acct.RuleFacadeIntegration;
import com.ipaylinks.poss.util.DateUtil;
/**
 * 记账规则管理
 * @author Jerry Chen
 * @date 2018年9月8日
 */
@Controller
@RequestMapping("/acct/rule")
public class RuleController {
	private Logger logger = LoggerFactory.getLogger(RuleController.class);
	
	@Autowired
	private RuleFacadeIntegration ruleFacadeIntegration; 
	
	@RequestMapping
	public String index() {
		return "/acct/rule";
	}
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
		logger.info("查询记账规则列表");
		QueryRulesReq req = buildReq(request);
		req.setPageBean(PageBeanHelper.build(request));
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			PagedResult<RuleDto> pageResult = ruleFacadeIntegration.listRules(req);
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
	private List<Map<String, String>> convertList(List<RuleDto> ruleDtos) {
		List<Map<String, String>> list = new ArrayList<>();
		for (RuleDto tmp : ruleDtos) {
			Map<String, String> map = BeanUtil.beanToStringMap(tmp);
			map.put("accountType",AcctAccountTypeEnum.getMessageByCode(tmp.getAccountType()));
			map.put("financeType",FinanceTypeEnum.getMessage(tmp.getFinanceType()));
			map.put("currencyType",CurrencyTypeEnum.getMessageByCode(tmp.getCurrencyType()));
			map.put("tradeType",TradeTypeEnum.getDescByNumeric(tmp.getTradeType()));
			map.put("status", AcctRuleStatusEnum.getMessageByCode(tmp.getStatus()));
			map.put("createTime", DateUtil.formatTime(tmp.getCreateTime()));
			list.add(map);
		}
		return list;
	}
	private QueryRulesReq buildReq(HttpServletRequest request){
		QueryRulesReq req = new QueryRulesReq();
		req.setCurrencyType(request.getParameter("currencyType"));
		req.setFinanceType(request.getParameter("financeType"));
		req.setProdCode(ProdCodeEnum.CODE_10.getCode());
		req.setSceneCode(request.getParameter("sceneCode"));
		req.setStatus(request.getParameter("status"));
		req.setTradeType(request.getParameter("tradeType"));
		return req;
	}
}
