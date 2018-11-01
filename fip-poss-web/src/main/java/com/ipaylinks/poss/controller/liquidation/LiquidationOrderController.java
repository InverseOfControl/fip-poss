package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.cmp.css.facade.request.LiquidationOrderQueryRequest;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.domain.liquidation.LiquidationOrderVo;
import com.ipaylinks.poss.integration.css.liquidation.LiquidationOrderQueryClient;
import com.ipaylinks.poss.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/liquidation/liquidation-order")
public class LiquidationOrderController {
    @Autowired
    private LiquidationOrderQueryClient liquidationOrderQueryClient;

    @Autowired
    private DownloadController downloadController;

    @RequestMapping
    public String index(){
        return "liquidation/liquidation-order";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(LiquidationOrderVo liquidationOrderVo) {
        PagedResult pagedResult = liquidationOrderQueryClient.query(buildRequestParams(liquidationOrderVo));
        return new DataGrid<>(pagedResult.getTotal(),pagedResult.getDataList());
    }

    @RequestMapping("/download")
    public void download(LiquidationOrderVo requestVo, HttpServletRequest request, HttpServletResponse response){
        PagedResult pagedResult = liquidationOrderQueryClient.query(buildRequestParams(requestVo));
        downloadController.downLoad(pagedResult.getDataList(),response,request,"liquidationOrder");
    }

    private LiquidationOrderQueryRequest buildRequestParams(LiquidationOrderVo requestVo){
        LiquidationOrderQueryRequest request = new LiquidationOrderQueryRequest();
        BeanUtils.copyProperties(requestVo,request);
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(requestVo.getPage());
        pageBean.setPageSize(requestVo.getRows());
        request.setPageBean(pageBean);

        // 初始化日期信息
        Date beginDate = requestVo.getBeginPayCompleteTime();
        Date endDate = requestVo.getEndPayCompleteTime();
        if(null == beginDate && null == endDate){
            String[] dateStr = DateUtils.initFormDate(-1).split("@");
            beginDate = DateUtils.str2Date(dateStr[0],DateUtils.SHORT_DATE_FORMAT);
            endDate = DateUtils.str2Date(dateStr[1],DateUtils.SHORT_DATE_FORMAT);
        }
        String beginDateStr = DateUtils.toFormatDateString(beginDate, DateUtils.SHORT_DATE_FORMAT) + " 00:00:01";
        String endDateStr = DateUtils.toFormatDateString(endDate, DateUtils.SHORT_DATE_FORMAT) + " 23:59:59";
        request.setBeginPayCompleteTime(DateUtils.str2Date(beginDateStr,DateUtils.LONG_DATE_FORMAT));
        request.setEndPayCompleteTime(DateUtils.str2Date(endDateStr,DateUtils.LONG_DATE_FORMAT));
        return request;
    }
}
