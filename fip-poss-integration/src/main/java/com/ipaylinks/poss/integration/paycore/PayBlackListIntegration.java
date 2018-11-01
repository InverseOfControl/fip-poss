package com.ipaylinks.poss.integration.paycore;

import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.CommonResponse;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.paycore.facade.bean.BlackListDTO;
import com.ipaylinks.paycore.facade.request.AddBlackListReq;
import com.ipaylinks.paycore.facade.request.DeleteBlackListReq;
import com.ipaylinks.paycore.facade.request.QueryBlackListReq;
import com.ipaylinks.paycore.facade.request.UpdateBlackListReq;
import com.ipaylinks.paycore.facade.rmi.BlackListFacade;
import com.ipaylinks.poss.exception.InvokeException;
import com.ipaylinks.poss.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 黑名单管理客户端
 * @author zyj
 */
@Service
public class PayBlackListIntegration {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private BlackListFacade blackListFacade;

	/**
	 * 查询支付订单列表
	 * @param req
	 * @return
	 * @throws InvokeException
	 */
	public PagedResult<BlackListDTO> queryBlackList(QueryBlackListReq req) throws InvokeException{
		logger.info("调用paycore查询黑名单配置列表,req={}", req);
		PageQueryResponse<BlackListDTO> resp;
		try {
			resp = blackListFacade.queryBlackList(req);
		} catch (Exception e) {
			logger.error("在调用[blackListFacade.queryBlackList]时发生异常", e);
			throw new InvokeException("调用支付核心系统异常");
		}
		logger.info("在调用[blackListFacade.queryBlackList]时返回 resp：responseCode={},responseMsg={},responseStatus={}"
				, resp.getResponseCode(), resp.getResponseMsg(), resp.getResponseStatus());
		if(ResponseUtil.isFail(resp)){
			logger.error("在调用[blackListFacade.queryBlackList]时返回结果异常，resp:{}", ResponseUtil.getDesc(resp));
			throw new InvokeException("支付核心系统返回:{}", ResponseUtil.getDesc(resp));
		}
		return resp.getPagedResult();
	}


	public void addBlackList(BlackListDTO dto) throws InvokeException{
		CommonResponse resp;
		try {
			AddBlackListReq req = new AddBlackListReq();
			BeanUtils.copyProperties(dto, req);
			logger.info("调用paycore新增黑名单配置列表,req={}", req);
			resp = blackListFacade.addBlackList(req);
		} catch (Exception e) {
			logger.error("在调用[blackListFacade.addBlackList]时发生异常", e);
			throw new InvokeException("调用支付核心系统异常");
		}
		logger.info("在调用[blackListFacade.addBlackList]时返回 resp：{}", resp);
		if(ResponseUtil.isFail(resp)){
			logger.error("在调用[blackListFacade.addBlackList]时返回结果异常，resp:{}", ResponseUtil.getDesc(resp));
			throw new InvokeException("支付核心系统返回:{}", ResponseUtil.getDesc(resp));
		}
	}

	public void updateBlackList(BlackListDTO dto) throws InvokeException{
		CommonResponse resp;
		try {
			UpdateBlackListReq req = new UpdateBlackListReq();
			BeanUtils.copyProperties(dto, req);
			logger.info("调用paycore更新黑名单配置列表,req={}", req);
			resp = blackListFacade.updateBlackList(req);
		} catch (Exception e) {
			logger.error("在调用[blackListFacade.updateBlackList]时发生异常", e);
			throw new InvokeException("调用支付核心系统异常");
		}
		logger.info("在调用[blackListFacade.updateBlackList]时返回 resp：{}", resp);
		if(ResponseUtil.isFail(resp)){
			logger.error("在调用[blackListFacade.updateBlackList]时返回结果异常，resp:{}", ResponseUtil.getDesc(resp));
			throw new InvokeException("支付核心系统返回:{}", ResponseUtil.getDesc(resp));
		}
	}

	public void deleteBlackList(Long id) throws InvokeException{
		CommonResponse resp;
		try {
			DeleteBlackListReq req = new DeleteBlackListReq();
			req.setId(id);
			logger.info("调用paycore删除黑名单配置列表,req={}", req);
			resp = blackListFacade.deleteBlackList(req);
		} catch (Exception e) {
			logger.error("在调用[blackListFacade.deleteBlackList]时发生异常", e);
			throw new InvokeException("调用支付核心系统异常");
		}
		logger.info("在调用[blackListFacade.deleteBlackList]时返回 resp：{}", resp);
		if(ResponseUtil.isFail(resp)){
			logger.error("在调用[blackListFacade.deleteBlackList]时返回结果异常，resp:{}", ResponseUtil.getDesc(resp));
			throw new InvokeException("支付核心系统返回:{}", ResponseUtil.getDesc(resp));
		}
	}

}
