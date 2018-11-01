package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.cmp.css.facade.request.LiquidationSubOrderQueryRequest;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.domain.liquidation.LiquidationSubOrderVo;
import com.ipaylinks.poss.integration.css.liquidation.LiquidationOrderQueryClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/liquidation/liquidation-sub-order")
public class LiquidationSubOrderController {
    @Autowired
    private LiquidationOrderQueryClient client;

    @RequestMapping
    public String index(){
        return "liquidation/liquidation-sub-order";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(LiquidationSubOrderVo requestVo) {
        PagedResult pagedResult = client.querySubOrder(buildRequestParams(requestVo));
        return new DataGrid<>(pagedResult.getTotal(),pagedResult.getDataList());
    }

    private LiquidationSubOrderQueryRequest buildRequestParams(LiquidationSubOrderVo requestVo){
        LiquidationSubOrderQueryRequest request = new LiquidationSubOrderQueryRequest();
        BeanUtils.copyProperties(requestVo,request);
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(requestVo.getPage());
        pageBean.setPageSize(requestVo.getRows());
        request.setPageBean(pageBean);
        return request;
    }
}
