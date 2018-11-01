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

import com.ipaylinks.acct.facade.dto.CollectBatchDto;
import com.ipaylinks.acct.facade.enums.AcctCollectBatchStatusEnum;
import com.ipaylinks.acct.facade.enums.AcctCollectBatchTypeEnum;
import com.ipaylinks.acct.facade.request.QueryCollectBatchsReq;
import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.helper.PageBeanHelper;
import com.ipaylinks.poss.integration.acct.CollectBatchFacadeIntegration;
import com.ipaylinks.poss.util.DateUtil;
import com.ipaylinks.poss.util.StringUtil;
/**
 * 汇总任务
 * @author Jerry Chen
 * @date 2018年9月11日
 */
@Controller
@RequestMapping("/acct/collectBatch")
public class CollectBatchController {

	private Logger logger = LoggerFactory.getLogger(CollectBatchController.class);
	
	@Autowired
	private CollectBatchFacadeIntegration collectBatchFacadeIntegration;
	@RequestMapping
	public String index() {
		return "/acct/collectBatch";
	}
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
		logger.info("查询汇总任务");
		QueryCollectBatchsReq req = buildReq(request);
		req.setPageBean(PageBeanHelper.build(request));
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			PagedResult<CollectBatchDto> pageResult = collectBatchFacadeIntegration.listCollectBatchs(req);
			if (pageResult != null) {
				dataGrid.setTotal(pageResult.getTotal());
				dataGrid.setRows(convertList(pageResult.getDataList()));
			}
			logger.info("查询汇总任务，记录数:" + dataGrid.getTotal());
		} catch (Exception e) {
			dataGrid.setErrorMsg(e.getMessage());
		}
		return dataGrid;
	}
	private List<Map<String, String>> convertList(List<CollectBatchDto> tmpList) {
		List<Map<String, String>> list = new ArrayList<>();
		for (CollectBatchDto tmp : tmpList) {
			Map<String, String> map = BeanUtil.beanToStringMap(tmp);
			map.put("countType",AcctCollectBatchTypeEnum.getMessageByCode(tmp.getCountType()));
			map.put("status", AcctCollectBatchStatusEnum.getMessageByCode(tmp.getStatus()));
			map.put("createTime", DateUtil.formatTime(tmp.getCreateTime()));
			map.put("completeTime",DateUtil.formatTime(tmp.getCompleteTime()));
			map.put("updateTime",DateUtil.formatTime(tmp.getUpdateTime()));
			list.add(map);
		}
		return list;
	}
	private QueryCollectBatchsReq buildReq(HttpServletRequest request){
		QueryCollectBatchsReq req = new QueryCollectBatchsReq();
		req.setCountType(request.getParameter("countType"));
		req.setStatus(request.getParameter("status"));
		req.setBeginAccountingDay(StringUtil.str2AccountingDay(request.getParameter("beginAccountingDay")));
		req.setEndAccountingDay(StringUtil.str2AccountingDay(request.getParameter("endAccountingDay")));
		return req;
	}
}
