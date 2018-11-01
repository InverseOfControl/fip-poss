package com.ipaylinks.poss.integration.acct;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipaylinks.acct.facade.AccountFreezeFacade;
import com.ipaylinks.acct.facade.AccountManageFacade;
import com.ipaylinks.acct.facade.dto.AccountDto;
import com.ipaylinks.acct.facade.dto.BalanceDailyDto;
import com.ipaylinks.acct.facade.dto.BalanceDetailDto;
import com.ipaylinks.acct.facade.dto.FreezingDailyDto;
import com.ipaylinks.acct.facade.dto.FreezingDto;
import com.ipaylinks.acct.facade.request.FreezeRequest;
import com.ipaylinks.acct.facade.request.QueryAccountsReq;
import com.ipaylinks.acct.facade.request.QueryBalanceDailysReq;
import com.ipaylinks.acct.facade.request.QueryBalanceDetailsReq;
import com.ipaylinks.acct.facade.request.QueryFreezingDailysReq;
import com.ipaylinks.acct.facade.request.QueryFreezingDetailsReq;
import com.ipaylinks.acct.facade.request.UpdateAccountReq;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.CommonResponse;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.common.rpc.response.SingleResponse;
import com.ipaylinks.poss.exception.InvokeException;
import com.ipaylinks.poss.util.ResponseUtil;

/**
 * 账户服务客户端
 * @author Jerry Chen
 * @date 2018年8月28日
 */
@Service
public class AccountManageIntegration {
	
	private Logger logger = LoggerFactory.getLogger(AccountManageIntegration.class);
	@Autowired
	private AccountManageFacade accountManageFacade;
	@Autowired
	private AccountFreezeFacade accountFreezeFacade;

	/**
	 * 查询账户列表
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public PagedResult<AccountDto> listAccounts(QueryAccountsReq req) throws InvokeException{
		logger.info("调用acct查询账户列表,req={}",req);
		PageQueryResponse<AccountDto> res;
		try {
			res = accountManageFacade.listAccounts(req);
		} catch (Exception e) {
			logger.error("调用acct查询账户列表异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("调用acct查询账户列表结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("账户系统返回:"+res.getResponseMsg());
		}
		return res.getPagedResult();
	}
	/**
	 * 查询账户信息
	 * @param accountNo 账户号
	 * @return
	 * @throws InvokeException
	 */
	public AccountDto queryAccount(String accountNo)throws InvokeException{
		logger.info("调用acct查询账户信息,accountNo={}",accountNo);
		SingleResponse<AccountDto> res;
		try {
			res = accountManageFacade.queryAccount(new BigDecimal(accountNo));
		} catch (Exception e) {
			logger.error("调用acct查询账户信息异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("调用acct查询账户信息结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("账户系统返回:"+res.getResponseMsg());
		}
		return res.getResponseObj();
	}
	/**
	 * 更新账户信息（主要是止入/止出状态）
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public boolean updateAccount(UpdateAccountReq req)throws InvokeException{
		logger.info("调用acct更新账户信息,req={}",req);
		CommonResponse res;
		try {
			res = accountManageFacade.updateAccount(req);
		} catch (Exception e) {
			logger.error("调用acct更新账户信息异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("调用acct更新账户信息结果:"+ResponseUtil.getDesc(res));
		return ResponseUtil.isSuccess(res);
	}
	/**
	 * 查询账户余额详细
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public PagedResult<BalanceDetailDto> listBalanceDetails(QueryBalanceDetailsReq req) throws InvokeException{
		logger.info("调用acct查询账户余额列表,req={}",req);
		PageQueryResponse<BalanceDetailDto> res;
		try {
			res = accountManageFacade.listBalanceDetails(req);
		} catch (Exception e) {
			logger.error("调用acct查询账户余额列表异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("调用acct查询账户余额列表结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("账户系统返回:"+res.getResponseMsg());
		}
		return res.getPagedResult();
	}
	/**
	 * 查询账户历史余额
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public PagedResult<BalanceDailyDto> listBalanceDailys(QueryBalanceDailysReq req) throws InvokeException{
		logger.info("调用acct查询账户历史余额列表,req={}",req);
		PageQueryResponse<BalanceDailyDto> res;
		try {
			res = accountManageFacade.listBalanceDailys(req);
		} catch (Exception e) {
			logger.error("调用acct查询账户历史余额列表异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("调用acct查询账户历史余额列表结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("账户系统返回:"+res.getResponseMsg());
		}
		return res.getPagedResult();
	}
	/**
	 * 查询账户余额信息
	 * @param accountNo
	 * @return
	 * @throws InvokeException
	 */
	public BalanceDetailDto queryBalanceDetail(String accountNo)throws InvokeException{
		logger.info("调用acct查询账户余额信息,accountNo={}",accountNo);
		SingleResponse<BalanceDetailDto> res = null;
		try {
			res = accountManageFacade.queryBalanceDetail(new BigDecimal(accountNo));
		} catch (Exception e) {
			logger.error("调用acct查询账户余额信息异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("调用acct查询账户余额信息结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("账户系统返回:"+res.getResponseMsg());
		}
		return res.getResponseObj();
	}
	/**
	 * 冻结/解冻
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public CommonResponse freeze(FreezeRequest req)throws InvokeException{
		logger.info("调acct冻结请求,req={}",req);
		CommonResponse res;
		try {
			res = accountFreezeFacade.freeze(req);
		} catch (Exception e) {
			logger.error("调acct冻结异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("调acct冻结结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("账户系统返回:"+res.getResponseMsg());
		}
		return res;
	}
	/**
	 * 查询冻结解冻明细
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public PagedResult<FreezingDto> listFreezingDetails(QueryFreezingDetailsReq req) throws InvokeException{
		logger.info("调用acct查询冻结解冻明细列表,req={}",req);
		PageQueryResponse<FreezingDto> res;
		try {
			res = accountFreezeFacade.listFreezingDetails(req);
		} catch (Exception e) {
			logger.error("调用acct查询冻结解冻明细异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("调用acct查询冻结解冻明细结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("账户系统返回:"+res.getResponseMsg());
		}
		return res.getPagedResult();
	}
	/**
	 * 每日冻结余额汇总
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public PagedResult<FreezingDailyDto> listFreezingDailys(QueryFreezingDailysReq req) throws InvokeException{
		logger.info("调用acct查询每日冻结余额汇总列表,req={}",req);
		PageQueryResponse<FreezingDailyDto> res;
		try {
			res = accountFreezeFacade.listFreezingDailys(req);
		} catch (Exception e) {
			logger.error("调用acct查询每日冻结余额汇总异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("调用acct查询每日冻结余额汇总结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("账户系统返回:"+res.getResponseMsg());
		}
		return res.getPagedResult();
	}
	
}
