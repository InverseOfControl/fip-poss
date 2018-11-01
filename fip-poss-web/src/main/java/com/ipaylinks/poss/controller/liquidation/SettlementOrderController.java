package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.cmp.css.facade.request.QuerySettlementRequest;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.domain.liquidation.SettlementOrderVo;
import com.ipaylinks.poss.integration.css.liquidation.SettlementOrderQueryClient;
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
 * 查询结算单Controller
 *
 * @author hongxu.gao
 * @date 2018/8/28 11:03
 */
@Controller
@RequestMapping("/liquidation/settlement-order")
public class SettlementOrderController {

    @Autowired
    private SettlementOrderQueryClient client;

    @Autowired
    private DownloadController downloadController;

    @RequestMapping
    public String index(){
        return "liquidation/settlement-order";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(SettlementOrderVo requestVo) {
        PagedResult pagedResult = client.query(buildRequestParams(requestVo));
        return new DataGrid<>(pagedResult.getTotal(),pagedResult.getDataList());
    }

    @RequestMapping("/download")
    public void download(SettlementOrderVo requestVo, HttpServletRequest request, HttpServletResponse response){
        PagedResult pagedResult = client.query(buildRequestParams(requestVo));
        downloadController.downLoad(pagedResult.getDataList(),response,request,"settlementOrder");
    }

    private QuerySettlementRequest buildRequestParams(SettlementOrderVo requestVo){
        QuerySettlementRequest request = new QuerySettlementRequest();
        BeanUtils.copyProperties(requestVo,request);
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(requestVo.getPage());
        pageBean.setPageSize(requestVo.getRows());
        request.setPageBean(pageBean);

        // 初始化日期信息
        String beginOrderDate = Objects.toString(requestVo.getOrderBeginDate(),"").replaceAll("-","");
        String endOrderDate = Objects.toString(requestVo.getOrderEndDate(),"").replaceAll("-","");
        if(StringUtils.isEmpty(beginOrderDate) && StringUtils.isEmpty(endOrderDate)){
            String dateStr = DateUtils.initFormDate(-6);
            beginOrderDate = dateStr.split("@")[0].replaceAll("-","");
            endOrderDate = dateStr.split("@")[1].replaceAll("-","");
        }
        request.setOrderBeginDate(beginOrderDate);
        request.setOrderEndDate(endOrderDate);
        return request;
    }
}
