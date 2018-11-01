package com.ipaylinks.poss.integration.acct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipaylinks.acct.facade.CollectBatchFacade;
import com.ipaylinks.acct.facade.dto.CollectBatchDto;
import com.ipaylinks.acct.facade.request.QueryCollectBatchsReq;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.exception.InvokeException;
import com.ipaylinks.poss.util.ResponseUtil;
/**
 * 汇总任务服务
 * @author Jerry Chen
 * @date 2018年9月11日
 */
@Service
public class CollectBatchFacadeIntegration {
	private Logger logger = LoggerFactory.getLogger(CollectBatchFacadeIntegration.class);
	
	@Autowired
	private CollectBatchFacade collectBatchFacade;
	
	public PagedResult<CollectBatchDto> listCollectBatchs(QueryCollectBatchsReq req) throws InvokeException{
		logger.info("调用acct查询汇总任务,req={}",req);
		PageQueryResponse<CollectBatchDto> res;
		try {
			res = collectBatchFacade.listCollectBatchs(req);
		} catch (Exception e) {
			logger.error("调用acct查询汇总任务异常:",e);
			throw new InvokeException("调用账户系统异常");
		}
		logger.info("调用acct查询汇总任务结果:"+ResponseUtil.getDesc(res));
		if(ResponseUtil.isFail(res)){
			throw new InvokeException("账户系统返回:"+res.getResponseMsg());
		}
		return res.getPagedResult();
	}
}
