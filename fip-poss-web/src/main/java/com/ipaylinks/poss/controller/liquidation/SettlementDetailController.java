package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.cmp.css.facade.request.QuerySettlementDetailRequest;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.domain.liquidation.SettlementDetailVo;
import com.ipaylinks.poss.integration.css.liquidation.SettlementDetailQueryClient;
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
 * 查询结算明细Controller
 *
 * @author hongxu.gao
 * @date 2018/8/28 11:03
 */
@Controller
@RequestMapping("/liquidation/settlement-detail")
public class SettlementDetailController {

    @Autowired
    private SettlementDetailQueryClient client;

    @Autowired
    private DownloadController downloadController;

    @RequestMapping
    public String index(){
        return "liquidation/settlement-detail";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(SettlementDetailVo requestVo) {
        PagedResult pagedResult = client.query(buildRequestParams(requestVo));
        return new DataGrid<>(pagedResult.getTotal(),pagedResult.getDataList());
    }

    @RequestMapping("/download")
    public void download(SettlementDetailVo requestVo, HttpServletRequest request, HttpServletResponse response){
        PagedResult pagedResult = client.query(buildRequestParams(requestVo));
        downloadController.downLoad(pagedResult.getDataList(),response,request,"settlementDetail");
    }

    private QuerySettlementDetailRequest buildRequestParams(SettlementDetailVo requestVo){
        QuerySettlementDetailRequest request = new QuerySettlementDetailRequest();
        BeanUtils.copyProperties(requestVo,request);
        // 分页信息
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(requestVo.getPage());
        pageBean.setPageSize(requestVo.getRows());
        request.setPageBean(pageBean);
        // 初始化日期信息
        String beginSettleDate = Objects.toString(requestVo.getBeginSettleDate(),"").replaceAll("-","");
        String endSettleDate = Objects.toString(requestVo.getEndSettleDate(),"").replaceAll("-","");
        if(StringUtils.isEmpty(beginSettleDate) && StringUtils.isEmpty(endSettleDate)){
            String dateStr = DateUtils.initFormDate(-6);
            beginSettleDate = dateStr.split("@")[0].replaceAll("-","");
            endSettleDate = dateStr.split("@")[1].replaceAll("-","");
        }
        request.setBeginSettleDate(beginSettleDate);
        request.setEndSettleDate(endSettleDate);
        return request;
    }
}
