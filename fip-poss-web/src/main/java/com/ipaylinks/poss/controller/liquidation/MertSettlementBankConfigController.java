package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.mcs.facade.dto.MerchantSettlementBankConfigDto;
import com.ipaylinks.mcs.facade.request.MerchantSettlementBankConfigRequest;
import com.ipaylinks.poss.common.Constants;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.dal.domain.crm.Member;
import com.ipaylinks.poss.domain.liquidation.MertSettlementBankConfigVo;
import com.ipaylinks.poss.integration.css.liquidation.MertSettlementBankConfigClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * 商户结算银行信息配置控制层
 *
 * @author hongxu.gao
 * @date 2018/8/21 19:14
 */
@Controller
@RequestMapping("/liquidation/mert-settlement-bank-config")
public class MertSettlementBankConfigController {
    @Autowired
    private MertSettlementBankConfigClient client;

    @RequestMapping
    public String index(){
        return "liquidation/mert-settlement-bank-config";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(MertSettlementBankConfigVo requestVo) {
        PagedResult pagedResult = client.selectList(buildRequestParams(requestVo));
        return new DataGrid<>(pagedResult.getTotal(),pagedResult.getDataList());
    }

    @RequestMapping("/add")
    @ResponseBody
    public BaseResponse add(MertSettlementBankConfigVo requestVo, HttpServletRequest httpServletRequest) {
        MerchantSettlementBankConfigRequest request = new MerchantSettlementBankConfigRequest();
        BeanUtils.copyProperties(requestVo,request);
        request = this.getLoginInfo(requestVo,httpServletRequest);
        return client.add(request);
    }

    @RequestMapping("/edit")
    @ResponseBody
    public BaseResponse edit(MertSettlementBankConfigVo requestVo, HttpServletRequest httpServletRequest) {
        MerchantSettlementBankConfigRequest request = new MerchantSettlementBankConfigRequest();
        BeanUtils.copyProperties(requestVo,request);
        request = this.getLoginInfo(requestVo,httpServletRequest);
        return client.edit(request);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public BaseResponse delete(String id) {
        MerchantSettlementBankConfigRequest request = new MerchantSettlementBankConfigRequest();
        request.setId(id);
        return client.delete(request);
    }

    @RequestMapping("/toDialogPage")
    public String toDialogPage(MertSettlementBankConfigVo requestVo, HttpServletRequest request) {
        request.setAttribute("id",Objects.toString(requestVo.getId(),""));
        return "liquidation/mert-settlement-bank-config-dialog";
    }

    @RequestMapping("/one")
    @ResponseBody
    public MerchantSettlementBankConfigDto one(MertSettlementBankConfigVo requestVo) {
        return client.selectOne(buildRequestParams(requestVo));
    }

    /**
     * 构建查询请求参数
     */
    private MerchantSettlementBankConfigRequest buildRequestParams(MertSettlementBankConfigVo requestVo){
        MerchantSettlementBankConfigRequest request = new MerchantSettlementBankConfigRequest();
        BeanUtils.copyProperties(requestVo,request);
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(requestVo.getPage());
        pageBean.setPageSize(requestVo.getRows());
        request.setPageBean(pageBean);
        return request;
    }

    private MerchantSettlementBankConfigRequest getLoginInfo(MerchantSettlementBankConfigRequest request, HttpServletRequest httpServletRequest){
        Member member = (Member) httpServletRequest.getSession().getAttribute(Constants.SESSION_MEMBER_KEY);
        request.setOperator(member.getUserName());
        request.setOperateTime(new Date());
        return request;
    }
}
