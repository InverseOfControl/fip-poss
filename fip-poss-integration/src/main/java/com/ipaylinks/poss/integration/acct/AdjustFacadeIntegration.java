package com.ipaylinks.poss.integration.acct;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipaylinks.acct.facade.AccountAdjustFacade;
import com.ipaylinks.acct.facade.dto.AdjustDetailDto;
import com.ipaylinks.acct.facade.dto.AdjustDto;
import com.ipaylinks.acct.facade.request.AdjustAuditRequest;
import com.ipaylinks.acct.facade.request.AdjustRecordRequest;
import com.ipaylinks.acct.facade.request.QueryAdjustDetailsReq;
import com.ipaylinks.acct.facade.request.UploadVoucherReq;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.CommonResponse;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.common.rpc.response.SingleResponse;
import com.ipaylinks.poss.exception.InvokeException;
import com.ipaylinks.poss.util.ResponseUtil;

/**
 * 调账服务
 * @author Jerry Chen
 * @date 2018年9月4日
 */
@Service
public class AdjustFacadeIntegration {
	private Logger logger = LoggerFactory.getLogger(AdjustFacadeIntegration.class);
	
	@Autowired
	private AccountAdjustFacade accountAdjustFacade;
	
	/**
	 * 调账申请
	 * @param reqs
	 * @return
	 * @throws InvokeException
	 */
	public CommonResponse adjustApply(List<AdjustRecordRequest> reqs)throws InvokeException{
		logger.info("调用acct调账，调账申请记录数,req={}",reqs.size());
		CommonResponse res = null;
		try {
			res = accountAdjustFacade.recordAdjust(reqs);
		} catch (Exception e) {
			logger.error("调用acct调账，调账申请异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("调用acct调账，调账申请结果:"+ResponseUtil.getDesc(res));
		return res;
	}
	
	/**
	 * (分页)查询调账明细
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public PagedResult<AdjustDetailDto> listAdjustDetails(QueryAdjustDetailsReq req) throws InvokeException{
		logger.info("查询调账明细:{}",req);
		PageQueryResponse<AdjustDetailDto> res;
		try {
			res = accountAdjustFacade.listAdjustDetails(req);
		} catch (Exception e) {
			logger.error("查询调账明细异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("查询调账明细结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("账户系统返回:"+res.getResponseMsg());
		}
		return res.getPagedResult();
	}
	/**
	 * 调账审核
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public CommonResponse auditAdjust(AdjustAuditRequest req)throws InvokeException{
		logger.info("调用acct调账，调账审核,req={}",req);
		try {
			CommonResponse res = accountAdjustFacade.auditAdjust(req);
			logger.info("调用acct调账，调账审核结果:"+ResponseUtil.getDesc(res));
			return res;
		} catch (Exception e) {
			logger.error("调用acct调账，调账审核异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
	}
	
	public CommonResponse uploadVoucher(UploadVoucherReq req) throws InvokeException{
		logger.info("上传调账凭证,req={}",req);
		try {
			CommonResponse res = accountAdjustFacade.uploadVoucher(req);
			logger.info("上传调账凭证结果:"+ResponseUtil.getDesc(res));
			return res;
		} catch (Exception e) {
			logger.error("调用acct上传调账凭证异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
	}
	/**
	 * 查询调账主体
	 * @param adjustId
	 * @return
	 * @throws InvokeException
	 */
	public SingleResponse<AdjustDto> queryAdjust(BigDecimal adjustId) throws InvokeException{
		logger.info("查询调账主体,adjustId={}",adjustId);
		try {
			SingleResponse<AdjustDto> res = accountAdjustFacade.queryAdjust(adjustId);
			logger.info("查询调账主体结果:"+ResponseUtil.getDesc(res));
			return res;
		} catch (Exception e) {
			logger.error("调用acct查询调账主体异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
	}
}
