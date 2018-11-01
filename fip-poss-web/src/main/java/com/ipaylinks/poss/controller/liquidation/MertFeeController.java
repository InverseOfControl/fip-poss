package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.cmp.css.facade.request.QueryMerchantFeeRequest;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.domain.liquidation.MertFeeVo;
import com.ipaylinks.poss.integration.css.liquidation.MertFeeQueryClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 查询商户手续费控制层
 *
 * @author hongxu.gao
 * @date 2018/8/28 11:03
 */
@Controller
@RequestMapping("/liquidation/mert-fee")
public class MertFeeController {

    @Autowired
    private MertFeeQueryClient client;

    @Autowired
    private DownloadController downloadController;

    @RequestMapping
    public String index(){
        return "liquidation/mert-fee";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(MertFeeVo requestVo) {
        PagedResult pagedResult = client.query(buildRequestParams(requestVo));
        return new DataGrid<>(pagedResult.getTotal(),pagedResult.getDataList());
    }

    @RequestMapping("/charge")
    @ResponseBody
    public BaseResponse charge(String id){
        BaseResponse response = new BaseResponse();
        QueryMerchantFeeRequest request = new QueryMerchantFeeRequest();
        request.setId(id);
        return client.charge(request);
    }

    @RequestMapping("/download")
    public void download(MertFeeVo requestVo, HttpServletRequest request, HttpServletResponse response){
        PagedResult pagedResult = client.query(buildRequestParams(requestVo));
        downloadController.downLoad(pagedResult.getDataList(),response,request,"mertFee");
    }

    private QueryMerchantFeeRequest buildRequestParams(MertFeeVo requestVo){
        QueryMerchantFeeRequest request = new QueryMerchantFeeRequest();
        BeanUtils.copyProperties(requestVo,request);
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(requestVo.getPage());
        pageBean.setPageSize(requestVo.getRows());
        request.setPageBean(pageBean);

        // 日期处理
        String beginOrderDate = Objects.toString(requestVo.getBeginOrderDate(),"").replaceAll("-","");
        String endOrderDate = Objects.toString(requestVo.getEndOrderDate(),"").replaceAll("-","");;
        String beginSettleDate = Objects.toString(requestVo.getBeginSettleDate(),"").replaceAll("-","");;
        String endSettleDate = Objects.toString(requestVo.getEndSettleDate(),"").replaceAll("-","");;
        request.setBeginOrderDate(beginOrderDate);
        request.setEndOrderDate(endOrderDate);
        request.setBeginSettleDate(beginSettleDate);
        request.setEndSettleDate(endSettleDate);

        return request;
    }
}
