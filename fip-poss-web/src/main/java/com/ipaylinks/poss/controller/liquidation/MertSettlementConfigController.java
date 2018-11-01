package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.mcs.facade.dto.MerchantSettlementConfigDto;
import com.ipaylinks.mcs.facade.request.MerchantSettlementConfigRequest;
import com.ipaylinks.poss.common.Constants;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.dal.domain.crm.Member;
import com.ipaylinks.poss.domain.liquidation.MertSettlementConfigVo;
import com.ipaylinks.poss.integration.css.liquidation.MertSettlementConfigClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 商户结算信息配置控制层
 *
 * @author hongxu.gao
 * @date 2018/8/21 19:14
 */
@Controller
@RequestMapping("/liquidation/mert-settlement-config")
public class MertSettlementConfigController {
    private Logger logger = LoggerFactory.getLogger(MertSettlementConfigController.class);

    @Autowired
    private MertSettlementConfigClient mertSettlementConfigClient;

    @Autowired
    private LiquidationEnumDataController enumDataController;

    @RequestMapping
    public String index() {
        return "liquidation/mert-settlement-config";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(MertSettlementConfigVo mertSettlementConfigVo) {
        PagedResult pagedResult = mertSettlementConfigClient.selectList(buildRequestParams(mertSettlementConfigVo));
        return new DataGrid<>(pagedResult.getTotal(), pagedResult.getDataList());
    }

    @RequestMapping("/add")
    @ResponseBody
    public BaseResponse add(MertSettlementConfigVo mertSettlementConfigVo, HttpServletRequest httpServletRequest) {
        MerchantSettlementConfigRequest request = new MerchantSettlementConfigRequest();
        BeanUtils.copyProperties(mertSettlementConfigVo, request);
        request = this.getLoginInfo(request, httpServletRequest);
        return mertSettlementConfigClient.add(request);
    }

    @RequestMapping("/edit")
    @ResponseBody
    public BaseResponse edit(MertSettlementConfigVo mertSettlementConfigVo, HttpServletRequest httpServletRequest) {
        MerchantSettlementConfigRequest request = new MerchantSettlementConfigRequest();
        BeanUtils.copyProperties(mertSettlementConfigVo, request);
        request = this.getLoginInfo(request, httpServletRequest);
        return mertSettlementConfigClient.edit(request);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public BaseResponse delete(String id) {
        MerchantSettlementConfigRequest request = new MerchantSettlementConfigRequest();
        request.setId(id);
        return mertSettlementConfigClient.delete(request);
    }

    @RequestMapping("/toDialogPage")
    public String toDialogPage(MertSettlementConfigVo requestVo, HttpServletRequest request) {
        request.setAttribute("id", Objects.toString(requestVo.getId(), ""));
        request.setAttribute("flag",request.getParameter("flag"));
        return "liquidation/mert-settlement-config-dialog";
    }

    @RequestMapping("/one")
    @ResponseBody
    public MerchantSettlementConfigDto one(MertSettlementConfigVo requestVo) {
        return mertSettlementConfigClient.selectOne(buildRequestParams(requestVo));
    }

    /**
     * 构建查询请求参数
     */
    private MerchantSettlementConfigRequest buildRequestParams(MertSettlementConfigVo mertSettlementConfigVo) {
        MerchantSettlementConfigRequest request = new MerchantSettlementConfigRequest();
        BeanUtils.copyProperties(mertSettlementConfigVo, request);
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(mertSettlementConfigVo.getPage());
        pageBean.setPageSize(mertSettlementConfigVo.getRows());
        request.setPageBean(pageBean);
        return request;
    }

    private MerchantSettlementConfigRequest getLoginInfo(MerchantSettlementConfigRequest request, HttpServletRequest httpServletRequest) {
        Member member = (Member) httpServletRequest.getSession().getAttribute(Constants.SESSION_MEMBER_KEY);
        request.setOperator(member.getUserName());
        request.setOperateTime(new Date());
        return request;
    }

    @RequestMapping("/getAllSelectData")
    @ResponseBody
    public List<Map<String, Object>> getAllSelectData(String type) {
        List<Map<String, Object>> list = enumDataController.selectData(type);
        List<Map<String, Object>> returnList = new ArrayList<>();
        returnList.add(buildAllSelectNode("*","全部"));
        returnList.addAll(list);
        return returnList;
    }

    private Map<String,Object> buildAllSelectNode(String id, String text){
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("text", text);
        map.put("selected",true);
        return map;
    }
}
