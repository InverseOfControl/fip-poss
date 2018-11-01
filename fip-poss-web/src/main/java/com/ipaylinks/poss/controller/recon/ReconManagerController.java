package com.ipaylinks.poss.controller.recon;

import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.common.AjaxResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.integration.common.dto.EnumEntity;
import com.ipaylinks.poss.integration.verifycenter.TradeReconQueryFacadeIntegration;
import com.ipaylinks.poss.integration.verifycenter.dto.FileUploadRequest;
import com.ipaylinks.poss.util.DateUtils;
import com.ipaylinks.poss.util.DownLoadUtil;
import com.ipaylinks.poss.util.ResponseUtil;
import com.ipaylinks.poss.util.SimpleExcelGenerator;
import com.ipaylinks.verify.facade.enums.ChannelsTradeRecon;
import com.ipaylinks.verify.facade.enums.ReconHandleResultEnum;
import com.ipaylinks.verify.facade.enums.ReconResultEnum;
import com.ipaylinks.verify.facade.enums.ReconciliationTypeEnum;
import com.ipaylinks.verify.facade.req.ArsChannelFileRecordRequest;
import com.ipaylinks.verify.facade.resp.ArsChannelFileRecordResponse;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 对账管理控制器
 *
 * @author Alan
 * @date 20180821
 */
@Controller
@RequestMapping("/recon/channelfile")
public class ReconManagerController {

    private Logger logger = Logger.getLogger(ReconManagerController.class);

    private static final String EXCEL_FILE_TITLE = "渠道文件记录";

    private final TradeReconQueryFacadeIntegration tradeReconQueryFacadeClient;

    @Autowired(required = false)
    public ReconManagerController(TradeReconQueryFacadeIntegration tradeReconQueryFacadeClient) {
        this.tradeReconQueryFacadeClient = tradeReconQueryFacadeClient;
    }

    /**
     * 列表视图
     *
     * @return 返回
     */
    @RequestMapping
    public String index() {
        return "/recon/channelfile";
    }

    /**
     * 查询列表
     *
     * @param page    页
     * @param rows    码
     * @param request 请求
     * @return 返回
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid<ArsChannelFileRecordResponse> list(int page, int rows, ArsChannelFileRecordRequest request) {
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(page);
        pageBean.setPageSize(rows);
        request.setPageBean(pageBean);
        return new DataGrid<>(tradeReconQueryFacadeClient.queryChannelFileRecord(request));
    }

    /**
     * 对账交易渠道列表
     *
     * @return 返回
     */
    @RequestMapping("/channels")
    @ResponseBody
    public List channels() {
        return EnumEntity.convert(ChannelsTradeRecon.values());
    }

    /**
     * 对账结果列表
     *
     * @return 返回
     */
    @RequestMapping("/reconResultList")
    @ResponseBody
    public List reconResultList() {
        return EnumEntity.convert(ReconResultEnum.values());
    }

    /**
     * 对账结果列表
     *
     * @return 返回
     */
    @RequestMapping("/reconHandleResultList")
    @ResponseBody
    public List reconHandleResultList() {
        return EnumEntity.convert(ReconHandleResultEnum.values());
    }

    /**
     * 文件类型列表
     *
     * @return 返回
     */
    @RequestMapping("/fileType")
    @ResponseBody
    public List fileType() {
        return EnumEntity.convert(ReconciliationTypeEnum.values());
    }


    /**
     * 添加视图
     */
    @RequestMapping("addChannelFileView")
    public void addChannelFileView(Model model) {
        model.addAttribute("channels", EnumEntity.convert(ChannelsTradeRecon.values()));
    }


    /**
     * 渠道文件上传
     *
     * @return 返回
     */
    @RequestMapping("/addChannelFileDetails")
    @ResponseBody
    public AjaxResult addChannelFileDetails(FileUploadRequest fileUploadRequest) {
        AjaxResult ajaxResult = new AjaxResult();
        BaseResponse response = tradeReconQueryFacadeClient.createChannelFileRecord(fileUploadRequest);
        if (ResponseUtil.isSuccess(response)) {
            ajaxResult.setMessage("渠道文件上传成功");
        } else {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage(response.getResponseMsg());
        }
        return ajaxResult;
    }

    /**
     * 渠道文件下载
     *
     * @param fileRequest 请求
     * @param response    响应
     * @return 返回
     */
    @RequestMapping("/download")
    @ResponseBody
    public AjaxResult downLoad(ArsChannelFileRecordRequest fileRequest, final HttpServletResponse response, final HttpServletRequest request) {
        try {
            PageQueryResponse<ArsChannelFileRecordResponse> pageResponse = tradeReconQueryFacadeClient.queryChannelFileRecord(fileRequest);
            List<ArsChannelFileRecordResponse> list;
            if (ResponseUtil.isSuccess(pageResponse) && null != pageResponse.getPagedResult()) {
                list = pageResponse.getPagedResult().getDataList();
            } else {
                return new AjaxResult(false, "渠道文件记录查询失败");
            }
            String[] headers = new String[]{"渠道号", "渠道名称", "批次号", "文件类型", "文件格式", "文件名称", "文件解析状态", "文件对账状态", "文件记账状态", "导入时间"};
            String[] fields = new String[]{"orgCode", "orgName", "batchNo", "fileType", "fileExtensionName", "fileName", "handleStatus", "reconStatus", "accountingStatus", "gmtCreateTime"};
            Workbook grid = SimpleExcelGenerator.generateGrid(EXCEL_FILE_TITLE, headers, fields, list);
            String fileName = DownLoadUtil.encodeFileName(EXCEL_FILE_TITLE + DateUtils.getCurrentDate(), request.getHeader("User-Agent"));
            response.setContentType("application/force-download");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            grid.write(response.getOutputStream());
        } catch (Exception e) {
            logger.error("渠道文件记录下载失败" + e);
            return new AjaxResult(false, "渠道文件记录下载异常");
        }
        return new AjaxResult("渠道文件下载成功");
    }


}
