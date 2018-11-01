package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.cmp.css.facade.request.QueryChannelCostRequest;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.domain.liquidation.ChannelCostVo;
import com.ipaylinks.poss.integration.css.liquidation.ChannelOrderClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 查询渠道成本控制层
 *
 * @author hongxu.gao
 * @date 2018/8/29 17:24
 */
@Controller
@RequestMapping("/liquidation/channel-cost")
public class ChannelCostController {

    @Autowired
    private ChannelOrderClient channelOrderQueryClient;

    @Autowired
    private DownloadController downloadController;

    @RequestMapping
    public String index(){
        return "liquidation/channel-cost";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(ChannelCostVo requestVo) {
        PagedResult pagedResult = channelOrderQueryClient.queryChannelCost(buildRequestParams(requestVo));
        return new DataGrid<>(pagedResult.getTotal(),pagedResult.getDataList());
    }

    @RequestMapping("/download")
    public void download(ChannelCostVo requestVo, HttpServletRequest request, HttpServletResponse response){
        PagedResult pagedResult = channelOrderQueryClient.queryChannelCost(buildRequestParams(requestVo));
        downloadController.downLoad(pagedResult.getDataList(),response,request,"channelCost");
    }

    private QueryChannelCostRequest buildRequestParams(ChannelCostVo requestVo){
        QueryChannelCostRequest request = new QueryChannelCostRequest();
        BeanUtils.copyProperties(requestVo,request);
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(requestVo.getPage());
        pageBean.setPageSize(requestVo.getRows());
        request.setPageBean(pageBean);

        String beginCostSettleDate = Objects.toString(requestVo.getBeginCostSettleDate(),"").replaceAll("-","");
        String endCostSettleDate = Objects.toString(requestVo.getEndCostSettleDate(),"").replaceAll("-","");
        request.setBeginCostSettleDate(beginCostSettleDate);
        request.setEndCostSettleDate(endCostSettleDate);
        return request;
    }
}
