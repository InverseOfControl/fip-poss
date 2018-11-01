package com.ipaylinks.poss.integration.acct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipaylinks.acct.facade.AccountingRuleFacade;
import com.ipaylinks.acct.facade.dto.RuleDto;
import com.ipaylinks.acct.facade.request.QueryRulesReq;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.exception.InvokeException;
import com.ipaylinks.poss.util.ResponseUtil;

/**
 * 记账规则服务
 * @author Jerry Chen
 * @date 2018年9月8日
 */
@Service
public class RuleFacadeIntegration {
	private Logger logger = LoggerFactory.getLogger(RuleFacadeIntegration.class);
	@Autowired
	private AccountingRuleFacade accountingRuleFacade;
	
	
	/**
	 * (分页)查询记账规则
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public PagedResult<RuleDto> listRules(QueryRulesReq req) throws InvokeException{
		logger.info("查询记账规则:{}",req);
		PageQueryResponse<RuleDto> res;
		try {
			res = accountingRuleFacade.listRules(req);
		} catch (Exception e) {
			logger.error("查询记账规则异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("查询记账规则结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("账户系统返回:"+res.getResponseMsg());
		}
		return res.getPagedResult();
	}
}
