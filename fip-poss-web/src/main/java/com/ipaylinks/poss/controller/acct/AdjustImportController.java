package com.ipaylinks.poss.controller.acct;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ipaylinks.acct.facade.dto.AdjustDetailDto;
import com.ipaylinks.acct.facade.dto.AdjustDto;
import com.ipaylinks.acct.facade.dto.AdjustSubsetDto;
import com.ipaylinks.acct.facade.enums.AcctAdjustAuditStatusEnum;
import com.ipaylinks.acct.facade.request.AdjustRecordRequest;
import com.ipaylinks.acct.facade.request.QueryAdjustDetailsReq;
import com.ipaylinks.acct.facade.request.UploadVoucherReq;
import com.ipaylinks.common.BeanListUtil;
import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.enums.FinanceTypeEnum;
import com.ipaylinks.common.enums.ProdCodeEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.ClientEnum;
import com.ipaylinks.common.rpc.response.CommonResponse;
import com.ipaylinks.common.rpc.response.SingleResponse;
import com.ipaylinks.common.util.ResponseUtil;
import com.ipaylinks.poss.common.AjaxResult;
import com.ipaylinks.poss.common.Constants;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.dal.domain.crm.Member;
import com.ipaylinks.poss.helper.PageBeanHelper;
import com.ipaylinks.poss.integration.acct.AdjustFacadeIntegration;
import com.ipaylinks.poss.integration.oss.UFSClient;
import com.ipaylinks.poss.util.DateUtil;
import com.ipaylinks.poss.util.StringUtil;

/**
 * 调账申请
 * 
 * @author Jerry Chen
 * @date 2018年8月30日
 */
@Controller
@RequestMapping("/acct/adjustImport")
public class AdjustImportController {
	private Logger logger = LoggerFactory.getLogger(AdjustImportController.class);
	@Autowired
	private AdjustFacadeIntegration adjustFacadeIntegration;
	@Autowired
	private UFSClient ufsClient;
	
	@RequestMapping
	public String index() {
		return "/acct/adjustImport";
	}

	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			QueryAdjustDetailsReq req = buildReq(request);
			logger.info("调账申请，查询调账明细:{}",req);
			req.setPageBean(PageBeanHelper.build(request));
			PagedResult<AdjustDetailDto> pageResult = adjustFacadeIntegration.listAdjustDetails(req);
			if (pageResult != null) {
				dataGrid.setTotal(pageResult.getTotal());
				dataGrid.setRows(convertList(pageResult.getDataList()));
			}
			logger.info("调账申请，查询调账明细，记录数:" + dataGrid.getTotal());
		} catch (Exception e) {
			dataGrid.setErrorMsg(e.getMessage());
		}
		return dataGrid;
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
	

	/**
	 * 下载调账模板
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/downloadTemplate")
	public void downloadTemplate(HttpServletRequest request,HttpServletResponse response) {
		logger.info("下载调账模板");
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream("asset/acct/调账模板.xls");
			byte[] bt = new byte[is.available()];
			is.read(bt);
			response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode("调账模板.xls", "UTF-8"));
			response.setContentType("application/force-download;charset=UTF-8");
			response.setContentLength(bt.length);
			ServletOutputStream sos = response.getOutputStream();
			sos.write(bt);			
		} catch (IOException e) {
			logger.error("下载调账模板",e);
		}
	}

	/**
	 * 导入调账文件
	 */
	@RequestMapping("/importFile")
	public void importFile() {
	}

	@RequestMapping("/doImport")
	@ResponseBody
	public AjaxResult doImport(MultipartFile file,HttpServletRequest request) {
		logger.info("文件名：" + file.getOriginalFilename());
		AjaxResult ajaxResult = new AjaxResult();
		try {
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			int size = sheet.getLastRowNum();
			Map<String,List<AdjustEntity>> map = new HashMap<>();
			for (int i = 1; i <= size; i++) {// 从索引1开始解析
				AdjustEntity adjustEntity = parse(sheet.getRow(i));
				if(map.get(adjustEntity.getBatchId()) == null){
					List<AdjustEntity> list = new ArrayList<>();
					list.add(adjustEntity);
					map.put(adjustEntity.getBatchId(), list);
				}else{
					map.get(adjustEntity.getBatchId()).add(adjustEntity);
				}
			}
			if (map.size() == 0) {
				ajaxResult.setSuccess(false);
				ajaxResult.setMessage("未解析到调账记录");
				return ajaxResult;
			}
			check(map);
			List<AdjustRecordRequest> reqs = buildAdjustApply(map,request);
			logger.info("调账申请请求:{}",reqs);
			CommonResponse res = adjustFacadeIntegration.adjustApply(reqs);
			ajaxResult.setSuccess(ResponseUtil.isSuccess(res));
			ajaxResult.setMessage(res.getResponseMsg());
		} catch (Exception e) {
			logger.error("解析文件异常：", e);
			ajaxResult.setSuccess(false);
			ajaxResult.setMessage(e.getMessage());
		}
		return ajaxResult;
	}

	/**
	 * 构建调账申请请求
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	private List<AdjustRecordRequest> buildAdjustApply(Map<String,List<AdjustEntity>> map,HttpServletRequest request) throws Exception{
		List<AdjustRecordRequest> list = new ArrayList<>();
		for(String batchId : map.keySet()){
			AdjustRecordRequest adjustRecordRequest = new AdjustRecordRequest();
			List<AdjustEntity> tmpList = map.get(batchId);
			AdjustEntity firstAdjustEntity = tmpList.get(0);
			adjustRecordRequest.setBatchId(batchId);
			adjustRecordRequest.setSysCode(ClientEnum.FIP_POSS.getClientCode());
			adjustRecordRequest.setSysTraceNo(firstAdjustEntity.getSysTraceNo());
			adjustRecordRequest.setOrgCode(firstAdjustEntity.getOrgCode());
			adjustRecordRequest.setProdCode(ProdCodeEnum.CODE_10.getCode());
			adjustRecordRequest.setSceneCode("1099");
			adjustRecordRequest.setTradeType(TradeTypeEnum.ADJUST.getNumeric());
			adjustRecordRequest.setFinanceType(FinanceTypeEnum.PRINCIPAL.getCode());
			adjustRecordRequest.setPsCode("109901");
			Member member = (Member) request.getSession().getAttribute(Constants.SESSION_MEMBER_KEY);
			adjustRecordRequest.setAdjustOperator(member.getUserName());
			adjustRecordRequest.setAdjustSummary(firstAdjustEntity.getAdjustSummary());
			List<AdjustSubsetDto> subsets = BeanListUtil.copyBeanList(tmpList, AdjustSubsetDto.class);
			adjustRecordRequest.setSubsets(subsets);
			list.add(adjustRecordRequest);
		}
		return list;
	}
	
	/**
	 * 验证调账内容
	 * @param list
	 * @throws Exception
	 */
	private void check(Map<String,List<AdjustEntity>> map) throws Exception {
		for (List<AdjustEntity> list : map.values()) {
			for(AdjustEntity tmp : list){
				if (tmp.getBatchId() == null) {
					throw new Exception("请填写批次号");
				}
				if (tmp.getAccountNoStr() == null) {
					throw new Exception("请填写账户号");
				}
				try {
					tmp.setAccountNo(new BigDecimal(tmp.getAccountNoStr()));
				} catch (Exception e) {
					throw new Exception("错误的账户号");
				}
				if (tmp.getDcDirection() == null) {
					throw new Exception("请填写借贷方向");
				}
				if (!("DR".equals(tmp.getDcDirection()) || "CR".equals(tmp.getDcDirection()))) {
					throw new Exception("错误的借贷方向");
				}
				if (tmp.getAmountStr() == null) {
					throw new Exception("请填写金额");
				}
				try {
					tmp.setAmount(new BigDecimal(tmp.getAmountStr()));
				} catch (Exception e) {
					throw new Exception("错误的金额");
				}
			}
		}
	}

	/**
	 * 解析调账明细
	 * @param row
	 * @return
	 * @throws Exception
	 */
	private AdjustEntity parse(Row row) throws Exception {
		AdjustEntity adjustEntity = new AdjustEntity();
		adjustEntity.setBatchId(getCellString(row.getCell(0)));//批次号
		adjustEntity.setAccountNoStr(getCellString(row.getCell(1)));//账号
		adjustEntity.setDcDirection(getCellString(row.getCell(2)));//借贷方向
		adjustEntity.setAmountStr(getCellString(row.getCell(3)));//金额
		adjustEntity.setAdjustSummary(getCellString(row.getCell(4)));//摘要
		adjustEntity.setOrgCode(getCellString(row.getCell(5)));
		adjustEntity.setSysTraceNo(getCellString(row.getCell(6)));
		return adjustEntity;
	}
	/**
	 * 获取单元格内容，只获取数字型和字符串型，其他返回 null
	 * @param cell
	 * @return
	 */
	private String getCellString(Cell cell) {
		String value = null;
		if(cell == null){
			return value;
		}
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
            	double d = cell.getNumericCellValue();
            	value = String.valueOf(new BigDecimal(d));
                break;
            case Cell.CELL_TYPE_STRING:
            	value =  cell.getStringCellValue();
            	break;
            default:
            	value = null;
        }
        if(value == null || StringUtils.isBlank(value = value.trim())){
        	return null;
        }
        return value;
    }
	
	
	@RequestMapping("/uploadVoucher")
	public void uploadVoucher(String adjustId,Model model) {
		model.addAttribute("adjustId", adjustId);
	}

	@RequestMapping("/doUploadVoucher")
	@ResponseBody
	public AjaxResult doUploadVoucher(HttpServletRequest request,MultipartFile file) {
		AjaxResult ajaxResult = new AjaxResult();
		String adjustId = request.getParameter("adjustId");
		String fileName = file.getOriginalFilename();
		logger.info("上传调账凭证，调账单号:{}，文件名:{}",adjustId,fileName);
		try {
			UploadVoucherReq req = new UploadVoucherReq();
			req.setAdjustId(new BigDecimal(adjustId));
			//获取新的文件名
			String date = DateUtil.format(new Date(),DateUtil.YYYYMMDDHHMMSS);
			String originalFilename = file.getOriginalFilename();
			req.setVoucherName(originalFilename);
			String newFileName = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
			String ossToken = ClientEnum.ACCT.getClientCode()+"/"+adjustId+"/voucher/"+date+newFileName;
			boolean ret = ufsClient.putFile(ossToken, file.getInputStream());
			ajaxResult.setSuccess(false);
			ajaxResult.setMessage("上传凭证失败");
			if(ret){
				req.setVoucherOssToken(ossToken);
				CommonResponse res = adjustFacadeIntegration.uploadVoucher(req);
				ajaxResult.setSuccess(ResponseUtil.isSuccess(res));
				ajaxResult.setMessage(res.getResponseMsg());
			}
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
			ajaxResult.setMessage("上传凭证异常:"+e.getMessage());
		}
		return ajaxResult;
	}
	
	@RequestMapping("/downloadVoucher")
	public void downloadVoucher(HttpServletRequest request,HttpServletResponse response) {
		String adjustId = request.getParameter("adjustId");
		try {
			SingleResponse<AdjustDto> res = adjustFacadeIntegration.queryAdjust(new BigDecimal(adjustId));
			if(ResponseUtil.isSuccess(res)){
				String ossToken = res.getResponseObj().getVoucherOssToken();
				String fileName = res.getResponseObj().getVoucherName();
				fileName =  URLEncoder.encode(fileName, "UTF-8");
				if(StringUtils.isNotBlank(ossToken)){
					response.setHeader("Content-Disposition", "attachment; filename="+fileName);
					response.setContentType("application/force-download;charset=UTF-8");
					ufsClient.getFile(ossToken, response.getOutputStream());
					response.flushBuffer();
				}	
			}
		} catch (Exception e) {
			logger.error("下载凭证异常",e);
		}
	}
}
