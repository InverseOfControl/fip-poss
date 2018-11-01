package com.ipaylinks.poss.controller.paycore;

import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.enums.BizStatusEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.paycore.facade.bean.PaymentOrderDTO;
import com.ipaylinks.paycore.facade.request.QueryPayOrderListReq;
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
 * 支付订单查询controller
 * @author zyj
 * @date 2018年09月01日
 */
@Controller
@RequestMapping("/paycore/payOrder")
public class PayOrderController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PayOrderQueryIntegration payOrderQueryIntegration;

	@RequestMapping
	public String index() {
		return "/paycore/payOrder";
	}

	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
		logger.info("查询支付订单列表");
		QueryPayOrderListReq req = this.buildQueryPayOrderListReq(request);
		req.setPageBean(PageBeanHelper.build(request));
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			PagedResult<PaymentOrderDTO> pagedResult = payOrderQueryIntegration.queryPayOrderList(req);
			if (pagedResult != null) {
				dataGrid.setTotal(pagedResult.getTotal());
				dataGrid.setRows(convertList(pagedResult.getDataList()));
			}
			logger.info("查询支付订单列表，记录数:{}", dataGrid.getTotal());
		} catch (Exception e) {
			dataGrid.setErrorMsg(e.getMessage());
		}
		return dataGrid;
	}


	@RequestMapping("/download")
	public void download(HttpServletRequest request,HttpServletResponse response) {
		logger.info("下载支付订单列表");
		QueryPayOrderListReq req = this.buildQueryPayOrderListReq(request);
		List<Map<String, String>> list = new ArrayList<>();
		try {
			PagedResult<PaymentOrderDTO> pageResult = payOrderQueryIntegration.queryPayOrderList(req);
			if (pageResult != null) {
				list = convertList(pageResult.getDataList());
			}
			logger.info("下载支付订单列表，记录数:{}", list.size());
			String[] headers = new String[]{"商户号","商户订单号","收单订单号","支付订单号","交易类型",
					"交易金额","交易币种","支付金额","支付币种","支付状态","支付模式","支付类型","创建时间",
					"完成时间","原始支付订单号","结算币种","对外code","剩余taken金额"};
            String[] fields = new String[]{"merchantId","merchantOrderId","acquireOrderId","paymentOrderId","tradeType",
					"amount","currency","payAmount","payCurrency","payStatus","payMode","payType","gmtCreateTime",
					"gmtCompleteTime","relatePaymentId","settleCurrency","resultCode","lastTakenAmount"};
            String fileName = "支付订单_" + DateUtils.toFormatDateString(new Date(), DateUtils.MAIL_DATE_FORMAT)+".xls";
            fileName = DownLoadUtil.encodeFileName(fileName, request.getHeader("User-Agent"));
            Workbook grid = SimpleExcelGenerator.generateGrid("支付订单",headers, fields, list);
            response.setContentType("application/force-download");
            response.setContentType("applicationnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            grid.write(response.getOutputStream());
		} catch (Exception e) {
			logger.info("下载支付订单列表异常,",e);
		}
	}

	private QueryPayOrderListReq buildQueryPayOrderListReq(HttpServletRequest request){
		QueryPayOrderListReq req = new QueryPayOrderListReq();
        // req.setMemberId(request.getParameter("memberId"));
        req.setMerchantId(request.getParameter("merchantId"));
        req.setTradeType(request.getParameter("tradeType"));
        req.setPayStatus(request.getParameter("payStatus"));
        req.setCurrency(request.getParameter("currency"));
        // req.setPayCurrency(request.getParameter("payCurrency"));
        req.setPayType(request.getParameter("payType"));
        req.setPaymentOrderId(request.getParameter("paymentOrderId"));
        req.setAcquireOrderId(request.getParameter("acquireOrderId"));
        req.setMerchantOrderId(request.getParameter("merchantOrderId"));
        req.setPayMode(request.getParameter("payMode"));
		// req.setSettleCurrency(request.getParameter("settleCurrency"));
		req.setTradeDateBegin(request.getParameter("tradeDateBegin"));
		req.setTradeDateEnd(request.getParameter("tradeDateEnd"));
		return req;
	}

	private List<Map<String, String>> convertList(List<PaymentOrderDTO> payOrderList) {
		List<Map<String, String>> list = new ArrayList<>();
		for (PaymentOrderDTO payDTO : payOrderList) {
			Map<String, String> map = BeanUtil.beanToStringMap(payDTO);
			map.put("tradeType", TradeTypeEnum.getDescByNumeric(payDTO.getTradeType()));
			map.put("payStatus", BizStatusEnum.getDescByCode(payDTO.getPayStatus()));
			map.put("gmtCreateTime", DateUtil.formatTime(payDTO.getGmtCreateTime()));
			map.put("gmtModifyTime", DateUtil.formatTime(payDTO.getGmtCreateTime()));
			map.put("gmtCompleteTime", DateUtil.formatTime(payDTO.getGmtCompleteTime()));
			list.add(map);
		}
		return list;
	}

}
