package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.cmp.css.facade.request.QuerySettlementSummaryRequest;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.domain.liquidation.SettlementSummaryVo;
import com.ipaylinks.poss.integration.css.liquidation.SettlementSummaryQueryClient;
import com.ipaylinks.poss.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 查询结算汇总信息Controller
 *
 * @author hongxu.gao
 * @date 2018/8/28 11:03
 */
@Controller
@RequestMapping("/liquidation/settlement-summary")
public class SettlementSummaryController {

    @Autowired
    private SettlementSummaryQueryClient client;

    @Autowired
    private DownloadController downloadController;

    @RequestMapping
    public String index(){
        return "liquidation/settlement-summary";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(SettlementSummaryVo requestVo) {
        PagedResult pagedResult = client.query(buildRequestParams(requestVo));
        return new DataGrid<>(pagedResult.getTotal(),pagedResult.getDataList());
    }

    @RequestMapping("/download")
    public void download(SettlementSummaryVo requestVo, HttpServletRequest request, HttpServletResponse response){
        PagedResult pagedResult = client.query(buildRequestParams(requestVo));
        downloadController.downLoad(pagedResult.getDataList(),response,request,"settlementSummary");
    }

    private QuerySettlementSummaryRequest buildRequestParams(SettlementSummaryVo requestVo){
        QuerySettlementSummaryRequest request = new QuerySettlementSummaryRequest();
        BeanUtils.copyProperties(requestVo,request);
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(requestVo.getPage());
        pageBean.setPageSize(requestVo.getRows());
        request.setPageBean(pageBean);
        // 初始化日期信息
        String beginDate = Objects.toString(requestVo.getBeginDate(),"").replaceAll("-","");
        String endDate = Objects.toString(requestVo.getEndDate(),"").replaceAll("-","");
        if(StringUtils.isEmpty(beginDate) && StringUtils.isEmpty(endDate)){
            String dateStr = DateUtils.initFormDate(-6);
            beginDate = dateStr.split("@")[0].replaceAll("-","");
            endDate = dateStr.split("@")[1].replaceAll("-","");;
        }
        request.setBeginDate(beginDate);
        request.setEndDate(endDate);
        return request;
    }
}
