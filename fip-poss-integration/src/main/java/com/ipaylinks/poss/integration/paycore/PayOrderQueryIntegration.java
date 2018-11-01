package com.ipaylinks.poss.integration.paycore;

import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.paycore.facade.bean.DirectiveDTO;
import com.ipaylinks.paycore.facade.bean.PaymentOrderDTO;
import com.ipaylinks.paycore.facade.request.QueryPayOrderListReq;
import com.ipaylinks.paycore.facade.request.QuerySubDirectiveListReq;
import com.ipaylinks.paycore.facade.rmi.PayOrderQueryFacade;
import com.ipaylinks.poss.exception.InvokeException;
import com.ipaylinks.poss.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付订单查询客户端
 * @author zyj
 */
@Service
public class PayOrderQueryIntegration {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PayOrderQueryFacade payOrderQueryFacade;

	/**
	 * 查询支付订单列表
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public PagedResult<PaymentOrderDTO> queryPayOrderList(QueryPayOrderListReq req) throws InvokeException{
		logger.info("调用paycore查询支付订单列表,req={}", req);
		PageQueryResponse<PaymentOrderDTO> resp;
		try {
			resp = payOrderQueryFacade.queryPayOrderList(req);
		} catch (Exception e) {
			logger.error("在调用[payOrderQueryFacade.queryPayOrderList]时发生异常", e);
			throw new InvokeException("调用支付核心系统异常");
		}
		logger.info("在调用[payOrderQueryFacade.queryPayOrderList]时返回 resp：responseCode={},responseMsg={},responseStatus={}"
				, resp.getResponseCode(), resp.getResponseMsg(), resp.getResponseStatus());
		if(ResponseUtil.isFail(resp)){
			logger.error("在调用[payOrderQueryFacade.queryPayOrderList]时返回结果异常，resp:{}", ResponseUtil.getDesc(resp));
			throw new InvokeException("支付核心系统返回:{}", ResponseUtil.getDesc(resp));
		}
		return resp.getPagedResult();
	}

	/**
	 * 查询支付指令列表
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public PagedResult<DirectiveDTO> queryPayDirectiveList(QuerySubDirectiveListReq req) throws InvokeException{
		logger.info("调用paycore查询支付订单列表,req={}", req);
		PageQueryResponse<DirectiveDTO> resp;
		try {
			resp = payOrderQueryFacade.querySubDirectiveList(req);
		} catch (Exception e) {
			logger.error("在调用[payOrderQueryFacade.querySubDirectiveList]时发生异常", e);
			throw new InvokeException("调用支付核心系统异常");
		}
		logger.info("在调用[payOrderQueryFacade.querySubDirectiveList]时返回 resp：responseCode={},responseMsg={},responseStatus={}"
				, resp.getResponseCode(), resp.getResponseMsg(), resp.getResponseStatus());
		if(ResponseUtil.isFail(resp)){
			logger.error("在调用[payOrderQueryFacade.querySubDirectiveList]时返回结果异常，resp:{}", ResponseUtil.getDesc(resp));
			throw new InvokeException("支付核心系统返回:{}", ResponseUtil.getDesc(resp));
		}
		return resp.getPagedResult();
	}

}
