package com.ipaylinks.poss.controller.charge;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipaylinks.ccs.facade.dto.ChannelChargeStrgDTO;
import com.ipaylinks.ccs.facade.model.ChannelChargeConfigListQueryRequest;
import com.ipaylinks.ccs.facade.model.ChargeQueryByIdRequest;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.request.BaseCreateOrUpdateRequest;
import com.ipaylinks.common.rpc.response.SingleResponse;
import com.ipaylinks.poss.common.AjaxResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.controller.system.RoleController;
import com.ipaylinks.poss.integration.ccs.ChannelChargeIntegration;
import com.ipaylinks.poss.util.DateUtil;
import com.ipaylinks.poss.util.DateUtils;
import com.ipaylinks.poss.util.StringUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hubin.wei
 * @date 2018/8/27 14:44
 **/
@Controller
@RequestMapping("/charge/channelcharge")
@Transactional(readOnly = true)
public class ChannelChargeController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ChannelChargeIntegration channelChargeIntegration;


    @RequestMapping
    public void index() {
    }


    @RequestMapping("/list")
    @ResponseBody
    public DataGrid<ChannelChargeStrgDTO> list(int page, int rows, String sort, String order, String channelCode, String channelName, String chargeScene, String status) {
        DataGrid<ChannelChargeStrgDTO> dataGrid = null;
        try {
            ChannelChargeConfigListQueryRequest request = new ChannelChargeConfigListQueryRequest();
            request.setChannelCode(channelCode);
            request.setChannelName(channelName);
            request.setChargeScene(chargeScene);
            request.setStatus(status);
            PageBean pageBean = new PageBean();
            pageBean.setPageNumber(page);
            pageBean.setPageSize(rows);
            String orderBy = StringUtil.camelToUnderline(sort);
            if(!"".equals(orderBy)){
                pageBean.setOrderBy(orderBy + " "+ order);
            }
            request.setPageBean(pageBean);
            PagedResult<ChannelChargeStrgDTO> pagedResult = channelChargeIntegration.queryList(request);
            dataGrid = new DataGrid<>(pagedResult.getDataList());
            dataGrid.setTotal(pagedResult.getTotal());
        }catch (Exception e){
            logger.error("merchcharge list orror", e);
            dataGrid.setErrorMsg(e.getMessage());
        }
        return dataGrid;
    }

    /**
     * 查询明细信息
     * @param id
     * @param model
     */
    @RequestMapping("/channelchargedtl")
    public void merchchargedtl(Long id, Model model) {
        if (id != null) {
            ObjectMapper mapper = new ObjectMapper();
            ChargeQueryByIdRequest request = new ChargeQueryByIdRequest();
            request.setId(id);
            try {
                SingleResponse<ChannelChargeStrgDTO> singleResponse =  channelChargeIntegration.queryById(request);
                ChannelChargeStrgDTO chargeStrgDTO = singleResponse.getResponseObj();
                String effectiveDate= chargeStrgDTO.getEffectiveDate();
                chargeStrgDTO.setEffectiveDate(effectiveDate.substring(0,4)+"-"+effectiveDate.substring(4,6)
                        +"-"+effectiveDate.substring(6,8));
                String  expiryDate= chargeStrgDTO.getExpiryDate();
                chargeStrgDTO.setExpiryDate(expiryDate.substring(0,4)+"-"+expiryDate.substring(4,6)
                        +"-"+expiryDate.substring(6,8));
                model.addAttribute("channelChargeStrgDTO", mapper.writeValueAsString(chargeStrgDTO));
            }catch (JsonProcessingException e) {
                logger.error("json转换错误", e);
            }catch (Exception e){
                logger.error("merchchargedtl queryById error", e);
            }
        }
    }



    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public AjaxResult add(@RequestBody String jsonString) {
        logger.info("add json={}", jsonString);
        ChannelChargeStrgDTO channelChargeStrgDTO = null;
        try {
            channelChargeStrgDTO = JSON.parseObject(jsonString, ChannelChargeStrgDTO.class);
            if(channelChargeStrgDTO.getEffectiveDate() !=null){
                channelChargeStrgDTO.setEffectiveDate(channelChargeStrgDTO.getEffectiveDate().replaceAll("-",""));
            }
            if(channelChargeStrgDTO.getExpiryDate() !=null){
                channelChargeStrgDTO.setExpiryDate(channelChargeStrgDTO.getExpiryDate().replaceAll("-",""));
            }
            BaseCreateOrUpdateRequest<ChannelChargeStrgDTO> createOrUpdateRequest = new BaseCreateOrUpdateRequest();
            createOrUpdateRequest.setRequestObj(channelChargeStrgDTO);
            if(channelChargeStrgDTO.getId() != null){
                channelChargeIntegration.updateById(createOrUpdateRequest);
            }else {
                channelChargeIntegration.create(createOrUpdateRequest);
            }
        }  catch (Exception e) {
            logger.error("merchcharge add orror", e);
            return new AjaxResult(false).setMessage(e.getMessage());
        }
        return new AjaxResult();
    }


}
