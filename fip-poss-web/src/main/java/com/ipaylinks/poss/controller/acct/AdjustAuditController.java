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

import com.ipaylinks.acct.facade.dto.AdjustDetailDto;
import com.ipaylinks.acct.facade.enums.AcctAdjustAuditStatusEnum;
import com.ipaylinks.acct.facade.request.AdjustAuditRequest;
import com.ipaylinks.acct.facade.request.QueryAdjustDetailsReq;
import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.response.CommonResponse;
import com.ipaylinks.common.util.ResponseUtil;
import com.ipaylinks.poss.common.AjaxResult;
import com.ipaylinks.poss.common.Constants;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.dal.domain.crm.Member;
import com.ipaylinks.poss.helper.PageBeanHelper;
import com.ipaylinks.poss.integration.acct.AdjustFacadeIntegration;
import com.ipaylinks.poss.util.DateUtil;
import com.ipaylinks.poss.util.StringUtil;
/**
 * 调账审核
 * @author Jerry Chen
 * @date 2018年9月6日
 */
@Controller
@RequestMapping("/acct/adjustAudit")
public class AdjustAuditController {
	private Logger logger = LoggerFactory.getLogger(AdjustAuditController.class);
	@Autowired
	private AdjustFacadeIntegration adjustFacadeIntegration;
	@RequestMapping
	public String index() {
		return "/acct/adjustAudit";
	}
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			QueryAdjustDetailsReq req = buildReq(request);
			logger.info("调账审核,查询调账明细:{}",req);
			req.setPageBean(PageBeanHelper.build(request));
			PagedResult<AdjustDetailDto> pageResult = adjustFacadeIntegration.listAdjustDetails(req);
			if (pageResult != null) {
				dataGrid.setTotal(pageResult.getTotal());
				dataGrid.setRows(convertList(pageResult.getDataList()));
			}
			logger.info("调账审核,查询调账明细，记录数:" + dataGrid.getTotal());
		} catch (Exception e) {
			dataGrid.setErrorMsg(e.getMessage());
		}
		return dataGrid;
	}
	private QueryAdjustDetailsReq buildReq(HttpServletRequest request){
		QueryAdjustDetailsReq req = new QueryAdjustDetailsReq();
		req.setBatchId(StringUtil.str2BigDecimal(request.getParameter("batchId")));
		req.setAccountNo(StringUtil.str2BigDecimal(request.getParameter("accountNo")));
		req.setAdjustId(StringUtil.str2BigDecimal(request.getParameter("adjustId")));
		req.setAuditStatus(request.getParameter("auditStatus"));
		req.setBeginApplyDate(StringUtil.str2StartDate(request.getParameter("beginApplyDate")));
		req.setEndApplyDate(StringUtil.str2EndDate(request.getParameter("endApplyDate")));
		req.setBeginAuditDate(StringUtil.str2StartDate(request.getParameter("beginAuditDate")));
		req.setEndAuditDate(StringUtil.str2EndDate(request.getParameter("endAuditDate")));
		return req;
	}
	private List<Map<String, String>> convertList(List<AdjustDetailDto> detailList) {
		List<Map<String, String>> list = new ArrayList<>();
		for (AdjustDetailDto tmp : detailList) {
			Map<String, String> map = BeanUtil.beanToStringMap(tmp);
			map.put("auditStatusDesc", AcctAdjustAuditStatusEnum.getMessageByCode(tmp.getAuditStatus()));
			map.put("createTime", DateUtil.formatTime(tmp.getCreateTime()));
			map.put("updateTime", DateUtil.formatTime(tmp.getUpdateTime()));
			list.add(map);
		}
		return list;
	}
	@RequestMapping("/audit")
	public void list(String adjustId,Model model) {
		model.addAttribute("adjustId", adjustId);
	}
	
	@RequestMapping("/doAudit")
	@ResponseBody
	public AjaxResult doAudit(HttpServletRequest request) {
		AjaxResult ajaxResult = new AjaxResult();
		AdjustAuditRequest req = new AdjustAuditRequest();
		req.setAdjustId(new BigDecimal(request.getParameter("adjustId")));
		req.setAuditStatus(AcctAdjustAuditStatusEnum.NOT_PASS_AUDIT.getCode());
		if(Boolean.valueOf(request.getParameter("auditStatus"))){
			req.setAuditStatus(AcctAdjustAuditStatusEnum.PASS_AUDIT.getCode());
		}
		Member member = (Member) request.getSession().getAttribute(Constants.SESSION_MEMBER_KEY);
		req.setCommitor(member.getUserName());
		req.setCommitSummary(request.getParameter("commitSummary"));
		try {
			CommonResponse res = adjustFacadeIntegration.auditAdjust(req);
			ajaxResult.setSuccess(ResponseUtil.isSuccess(res));
			ajaxResult.setMessage(res.getResponseMsg());
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
			ajaxResult.setMessage(e.getMessage());
		}
		
		return ajaxResult;
	}
	
}
