package com.ipaylinks.poss.integration.acct;

import com.ipaylinks.acct.facade.AccountingFacade;
import com.ipaylinks.acct.facade.dto.OuterDetailDto;
import com.ipaylinks.acct.facade.dto.PipeLogDto;
import com.ipaylinks.acct.facade.dto.TradeDetailDto;
import com.ipaylinks.acct.facade.request.QueryOuterDetailsReq;
import com.ipaylinks.acct.facade.request.QueryPipeLogsReq;
import com.ipaylinks.acct.facade.request.QueryTradeDetailsReq;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.exception.InvokeException;
import com.ipaylinks.poss.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 记账服务
 * @author user
 * @date 2018年8月27日
 */
@Service
public class AccountingFacadeIntegration {
	private Logger logger = LoggerFactory.getLogger(AccountingFacadeIntegration.class);
	@Autowired
	private AccountingFacade accountingFacade;
	
	public PagedResult<OuterDetailDto> listOuterDetails(QueryOuterDetailsReq req) throws InvokeException{
		logger.error("调用acct查询账户收支明细(外部流水)列表,req={}",req);
		PageQueryResponse<OuterDetailDto> res;
		try {
			res = accountingFacade.listOuterDetails(req);
		} catch (Exception e) {
			logger.error("调用acct查询账户收支明细(外部流水)异常:",e);
			throw new InvokeException("调用acct异常");
		}
		logger.info("调用acct查询账户收支明细(外部流水)结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("调用acct失败，错误信息:"+res.getResponseMsg());
		}
		return res.getPagedResult();
	}
	public PagedResult<TradeDetailDto> listTradeDetails(QueryTradeDetailsReq req) throws InvokeException{
		logger.error("调用acct查询账户收支明细,req={}",req);
		PageQueryResponse<TradeDetailDto> res;
		try {
			res = accountingFacade.listTradeDetails(req);
		} catch (Exception e) {
			logger.error("调用acct查询账户收支明细异常:",e);
			throw new InvokeException("调用acct异常");
		}
		logger.info("调用acct查询账户收支明细结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("调用acct失败，错误信息:"+res.getResponseMsg());
		}
		return res.getPagedResult();
	}
	public PagedResult<PipeLogDto> listPipeLogs(QueryPipeLogsReq req) throws InvokeException{
		logger.error("调用acct查询记账日志,req={}",req);
		PageQueryResponse<PipeLogDto> res;
		try {
			res = accountingFacade.listPipeLogs(req);
		} catch (Exception e) {
			logger.error("调用acct查询记账日志异常:",e);
			throw new InvokeException("调用acct异常");
		}
		logger.info("调用acct查询记账日志结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("调用acct失败，错误信息:"+res.getResponseMsg());
		}
		return res.getPagedResult();
	}
}
