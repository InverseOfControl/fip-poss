package com.ipaylinks.poss.controller.paycore;

import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.enums.BizStatusEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.paycore.facade.bean.DirectiveDTO;
import com.ipaylinks.paycore.facade.bean.PaymentOrderDTO;
import com.ipaylinks.paycore.facade.enums.DirectiveTypeEnum;
import com.ipaylinks.paycore.facade.request.QueryPayOrderListReq;
import com.ipaylinks.paycore.facade.request.QuerySubDirectiveListReq;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.helper.PageBeanHelper;
import com.ipaylinks.poss.integration.paycore.PayOrderQueryIntegration;
import com.ipaylinks.poss.util.DateUtil;
import com.ipaylinks.poss.util.DateUtils;
import com.ipaylinks.poss.util.DownLoadUtil;
import com.ipaylinks.poss.util.SimpleExcelGenerator;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 支付指令查询controller
 * @author zyj
 * @date 2018年09月01日
 */
@Controller
@RequestMapping("/paycore/directive")
public class PayDirectiveController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PayOrderQueryIntegration payOrderQueryIntegration;

	@RequestMapping
	public String index() {
		return "/paycore/payDirective";
	}

	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
		logger.info("查询支付指令列表");
		QuerySubDirectiveListReq req = this.buildQueryPayDirectiveListReq(request);
		req.setPageBean(PageBeanHelper.build(request));
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			PagedResult<DirectiveDTO> pagedResult = payOrderQueryIntegration.queryPayDirectiveList(req);
			if (pagedResult != null) {
				dataGrid.setTotal(pagedResult.getTotal());
				dataGrid.setRows(convertList(pagedResult.getDataList()));
			}
			logger.info("查询支付指令列表，记录数:{}", dataGrid.getTotal());
		} catch (Exception e) {
			dataGrid.setErrorMsg(e.getMessage());
		}
		return dataGrid;
	}


	@RequestMapping("/download")
	public void download(HttpServletRequest request,HttpServletResponse response) {
		logger.info("下载支付指令列表");
		QuerySubDirectiveListReq req = this.buildQueryPayDirectiveListReq(request);
		List<Map<String, String>> list = new ArrayList<>();
		try {
			PagedResult<DirectiveDTO> pageResult = payOrderQueryIntegration.queryPayDirectiveList(req);
			if (pageResult != null) {
				list = convertList(pageResult.getDataList());
			}
			logger.info("下载支付指令列表，记录数:{}", list.size());
			String[] headers = new String[]{"支付指令号","支付订单号","交易类型","交易金额","交易币种",
					"支付金额","支付币种","支付状态","卡类型","支付类型","汇率","卡ID","国家信息",
					"支付渠道号","创建时间","完成时间","原交易指令号","通道ID","指令类型"};
            String[] fields = new String[]{"directiveId","paymentOrderId","tradeType","amount","currency",
					"payAmount","payCurrency","payStatus","payKind","payType","rate","cardId","countryCode",
					"orgCode","gmtCreateTime","gmtCompleteTime","relateDirectiveId","channelItemId","directiveType"};
            String fileName = "支付指令_" + DateUtils.toFormatDateString(new Date(), DateUtils.MAIL_DATE_FORMAT)+".xls";
            fileName = DownLoadUtil.encodeFileName(fileName, request.getHeader("User-Agent"));
            Workbook grid = SimpleExcelGenerator.generateGrid("支付指令",headers, fields, list);
            response.setContentType("application/force-download");
            response.setContentType("applicationnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            grid.write(response.getOutputStream());
		} catch (Exception e) {
			logger.info("下载支付指令列表异常,",e);
		}
	}

	private QuerySubDirectiveListReq buildQueryPayDirectiveListReq(HttpServletRequest request){
		QuerySubDirectiveListReq req = new QuerySubDirectiveListReq();
        req.setDirectiveId(request.getParameter("directiveId"));
		req.setPaymentOrderId(request.getParameter("paymentOrderId"));
		req.setTradeType(request.getParameter("tradeType"));
		req.setPayMode(request.getParameter("payMode"));
		req.setPayKind(request.getParameter("payKind"));
		req.setCurrency(request.getParameter("currency"));
		// req.setPayCurrency(request.getParameter("payCurrency"));
		req.setCardId(request.getParameter("cardId"));
		req.setCountryCode(request.getParameter("countryCode"));
		req.setOrgCode(request.getParameter("orgCode"));
		req.setPayType(request.getParameter("payType"));
		req.setPayStatus(request.getParameter("payStatus"));
		req.setChannelItemId(request.getParameter("channelItemId"));
		req.setDirectiveType(request.getParameter("directiveType"));
		req.setTradeDateBegin(request.getParameter("tradeDateBegin"));
		req.setTradeDateEnd(request.getParameter("tradeDateEnd"));
		return req;
	}

	private List<Map<String, String>> convertList(List<DirectiveDTO> directiveList) {
		List<Map<String, String>> list = new ArrayList<>();
		for (DirectiveDTO directive : directiveList) {
			Map<String, String> map = BeanUtil.beanToStringMap(directive);
			map.put("tradeType", TradeTypeEnum.getDescByNumeric(directive.getTradeType()));
			map.put("payStatus", BizStatusEnum.getDescByCode(directive.getPayStatus()));
			map.put("directiveType", DirectiveTypeEnum.getDescByCode(directive.getDirectiveType()));
			map.put("gmtCreateTime", DateUtil.formatTime(directive.getGmtCreateTime()));
			map.put("gmtModifyTime", DateUtil.formatTime(directive.getGmtCreateTime()));
			map.put("gmtCompleteTime", DateUtil.formatTime(directive.getGmtCompleteTime()));
			list.add(map);
		}
		return list;
	}

}
