package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.cmp.css.facade.request.ChannelOrderQueryRequest;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.domain.liquidation.ChannelOrderVo;
import com.ipaylinks.poss.integration.css.liquidation.ChannelOrderClient;
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
@RequestMapping("/liquidation/channel-order")
public class ChannelOrderController {
    @Autowired
    private ChannelOrderClient channelOrderQueryClient;

    @Autowired
    private DownloadController downloadController;

    @RequestMapping
    public String index(){
        return "liquidation/channel-order";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(ChannelOrderVo channelOrderVo) {
        PagedResult pagedResult = channelOrderQueryClient.queryChannelOrder(buildRequestParams(channelOrderVo));
        return new DataGrid<>(pagedResult.getTotal(),pagedResult.getDataList());
    }

    @RequestMapping("/download")
    public void download(ChannelOrderVo channelOrderVo, HttpServletRequest request, HttpServletResponse response){
        PagedResult pagedResult = channelOrderQueryClient.queryChannelOrder(buildRequestParams(channelOrderVo));
        downloadController.downLoad(pagedResult.getDataList(),response,request,"channelOrder");
    }

    @RequestMapping("/waterPush")
    @ResponseBody
    public BaseResponse waterPush(ChannelOrderVo channelOrderVo){
        ChannelOrderQueryRequest request = new ChannelOrderQueryRequest();
        request.setChannelOrderId(channelOrderVo.getChannelOrderId());
        return channelOrderQueryClient.channelWaterPush(request);
    }

    private ChannelOrderQueryRequest buildRequestParams(ChannelOrderVo requestVo){
        ChannelOrderQueryRequest request = new ChannelOrderQueryRequest();
        BeanUtils.copyProperties(requestVo,request);
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(requestVo.getPage());
        pageBean.setPageSize(requestVo.getRows());
        request.setPageBean(pageBean);

        // 初始化日期信息
        Date beginDate = requestVo.getBeginChannelCompleteTime();
        Date endDate = requestVo.getEndChannelCompleteTime();
        if(null == beginDate && null == endDate){
            String[] dateStr = DateUtils.initFormDate(-1).split("@");
            beginDate = DateUtils.str2Date(dateStr[0],"yyyy-MM-dd");
            endDate = DateUtils.str2Date(dateStr[1],"yyyy-MM-dd");
        }
        String beginDateStr = DateUtils.toFormatDateString(beginDate, DateUtils.SHORT_DATE_FORMAT) + " 00:00:01";
        String endDateStr = DateUtils.toFormatDateString(endDate, DateUtils.SHORT_DATE_FORMAT) + " 23:59:59";
        request.setBeginChannelCompleteTime(DateUtils.str2Date(beginDateStr,DateUtils.LONG_DATE_FORMAT));
        request.setEndChannelCompleteTime(DateUtils.str2Date(endDateStr,DateUtils.LONG_DATE_FORMAT));
        return request;
    }


}
