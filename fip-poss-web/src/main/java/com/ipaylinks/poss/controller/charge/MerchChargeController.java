package com.ipaylinks.poss.controller.charge;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipaylinks.ccs.facade.dto.MerchChargeStrgDTO;
import com.ipaylinks.ccs.facade.model.ChargeQueryByIdRequest;
import com.ipaylinks.ccs.facade.model.MerchChargeConfigListQueryRequest;
import com.ipaylinks.common.page.PageBean;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.common.rpc.request.BaseCreateOrUpdateRequest;
import com.ipaylinks.common.rpc.response.SingleResponse;
import com.ipaylinks.poss.common.AjaxResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.controller.system.RoleController;
import com.ipaylinks.poss.integration.ccs.MerchChargeIntegration;
import com.ipaylinks.poss.util.StringUtil;
import org.apache.commons.lang.StringUtils;
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

/**
 * @author hubin.wei
 * @date 2018/8/20 16:12
 **/
@Controller
@RequestMapping("/charge/merchcharge")
@Transactional(readOnly = true)
public class MerchChargeController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MerchChargeIntegration merchChargeIntegration;


    @RequestMapping
    public void index() {
    }


    @RequestMapping("/list")
    @ResponseBody
    public DataGrid<MerchChargeStrgDTO> list(int page, int rows, String sort, String order, String merchantId, String merchantName, String chargeScene, String status) {
        DataGrid<MerchChargeStrgDTO> dataGrid = null;
        try {
            MerchChargeConfigListQueryRequest request = new MerchChargeConfigListQueryRequest();
            request.setMerchantId(merchantId);
            request.setMerchantName(merchantName);
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
            PagedResult<MerchChargeStrgDTO> pagedResult = merchChargeIntegration.queryList(request);
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
    @RequestMapping("/merchchargedtl")
    public void merchchargedtl(Long id, Model model) {
        if (id != null) {
            ObjectMapper mapper = new ObjectMapper();
            ChargeQueryByIdRequest request = new ChargeQueryByIdRequest();
            request.setId(id);
            try {
                SingleResponse<MerchChargeStrgDTO> singleResponse =  merchChargeIntegration.queryById(request);
                MerchChargeStrgDTO chargeStrgDTO = singleResponse.getResponseObj();
                String effectiveDate= chargeStrgDTO.getEffectiveDate();
                chargeStrgDTO.setEffectiveDate(effectiveDate.substring(0,4)+"-"+effectiveDate.substring(4,6)
                        +"-"+effectiveDate.substring(6,8));
                String  expiryDate= chargeStrgDTO.getExpiryDate();
                chargeStrgDTO.setExpiryDate(expiryDate.substring(0,4)+"-"+expiryDate.substring(4,6)
                        +"-"+expiryDate.substring(6,8));
                model.addAttribute("channelChargeStrgDTO", mapper.writeValueAsString(chargeStrgDTO));
                model.addAttribute("merchChargeStrgDTO", mapper.writeValueAsString(chargeStrgDTO));
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
        MerchChargeStrgDTO merchChargeStrgDTO = null;
        try {
            merchChargeStrgDTO = JSON.parseObject(jsonString, MerchChargeStrgDTO.class);
            if(merchChargeStrgDTO.getEffectiveDate() !=null){
                merchChargeStrgDTO.setEffectiveDate(merchChargeStrgDTO.getEffectiveDate().replaceAll("-",""));
            }
            if(merchChargeStrgDTO.getExpiryDate() !=null){
                merchChargeStrgDTO.setExpiryDate(merchChargeStrgDTO.getExpiryDate().replaceAll("-",""));
            }
            BaseCreateOrUpdateRequest<MerchChargeStrgDTO> createOrUpdateRequest = new BaseCreateOrUpdateRequest();
            createOrUpdateRequest.setRequestObj(merchChargeStrgDTO);
            if(merchChargeStrgDTO.getId() != null){
                merchChargeIntegration.updateById(createOrUpdateRequest);
            }else {
                merchChargeIntegration.create(createOrUpdateRequest);
            }
        }  catch (Exception e) {
            logger.error("merchcharge add orror", e);
            return new AjaxResult(false).setMessage(e.getMessage());
        }
        return new AjaxResult();
    }



}
