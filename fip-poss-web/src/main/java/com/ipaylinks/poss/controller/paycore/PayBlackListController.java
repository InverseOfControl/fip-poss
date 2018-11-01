package com.ipaylinks.poss.controller.paycore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipaylinks.common.BeanUtil;
import com.ipaylinks.common.CurrencyEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.page.PagedResult;
import com.ipaylinks.paycore.facade.bean.BlackListDTO;
import com.ipaylinks.paycore.facade.enums.CommonEnum;
import com.ipaylinks.paycore.facade.request.QueryBlackListReq;
import com.ipaylinks.poss.common.AjaxResult;
import com.ipaylinks.poss.common.DataGrid;
import com.ipaylinks.poss.helper.PageBeanHelper;
import com.ipaylinks.poss.integration.common.EnumConverter;
import com.ipaylinks.poss.integration.paycore.PayBlackListIntegration;
import com.ipaylinks.poss.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 黑名单管理controller
 * @author zyj
 * @date 2018年09月01日
 */
@Controller
@RequestMapping("/paycore/blacklist")
public class PayBlackListController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PayBlackListIntegration payBlackListIntegration;
	@Autowired
    private ObjectMapper objectMapper;

	@RequestMapping
	public String index() {
		return "/paycore/blacklist";
	}

	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Map<String, String>> list(HttpServletRequest request) {
		logger.info("查询黑名单配置列表");
		QueryBlackListReq req = this.buildQueryBlackListReq(request);
		req.setPageBean(PageBeanHelper.build(request));
		DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
		try {
			PagedResult<BlackListDTO> pagedResult = payBlackListIntegration.queryBlackList(req);
			if (pagedResult != null) {
				dataGrid.setTotal(pagedResult.getTotal());
				dataGrid.setRows(convertList(pagedResult.getDataList()));
			}
			logger.info("查询黑名单配置列表，记录数:{}", dataGrid.getTotal());
		} catch (Exception e) {
			dataGrid.setErrorMsg(e.getMessage());
		}
		return dataGrid;
	}

	@RequestMapping("/form")
	public void form(Long id, Model model) {
		if (id != null) {
			try {
                QueryBlackListReq req = new QueryBlackListReq();
                req.setId(id);
                PagedResult<BlackListDTO> pagedResult = payBlackListIntegration.queryBlackList(req);
                if (null != pagedResult && !CollectionUtils.isEmpty(pagedResult.getDataList())){
                    model.addAttribute("blacklist", objectMapper.writeValueAsString(pagedResult.getDataList().get(0)));
                }
			} catch (Exception e) {
				logger.error("查询黑名单配置异常", e);
			}
		}
	}

    @RequestMapping({"/save", "/update"})
    @Transactional
    @ResponseBody
    public AjaxResult save(@Valid BlackListDTO blackListDTO, Long[] roles, BindingResult br) {
        logger.info("新增或修改黑名单配置");
        if (br.hasErrors()) {
            logger.error("对象校验失败：" + br.getAllErrors());
            return new AjaxResult(false).setData(br.getAllErrors());
        }
        boolean result = false;
        try {
            if (StringUtils.isBlank(blackListDTO.getStatus())){
                blackListDTO.setStatus(CommonEnum.NO.getCode());
            }
            if (blackListDTO.getId() != null) {
                payBlackListIntegration.updateBlackList(blackListDTO);
            }else {
                payBlackListIntegration.addBlackList(blackListDTO);
            }
            result = true;
        } catch (Exception e) {
            logger.error("黑名单配置{}更新异常", blackListDTO.getId(), e);
        }
        return new AjaxResult(result, "配置" + (result ? "成功！" : "失败！"));
    }

    @RequestMapping("/delete")
    @Transactional
    @ResponseBody
    public AjaxResult delete(Long id) {
        logger.info("删除黑名单配置");
        try {
            payBlackListIntegration.deleteBlackList(id);
        } catch (Exception e) {
            return new AjaxResult(false).setMessage(e.getMessage());
        }
        return new AjaxResult();
    }

    @RequestMapping("/getData")
    @ResponseBody
    public List<Map<String, Object>> selectData(String type) {
        if (type.equals(EnumConverter.CURRENCY_TYPE)){
            return currencyType();
        } else if (type.equals(EnumConverter.TRANS_TYPE)){
            return transType();
        }else {
            return null;
        }
    }

    /**
     * 币种类型
     */
    private List<Map<String, Object>> currencyType() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", "*");
        map.put("text", "*");
        list.add(map);
        for (CurrencyEnum temp : CurrencyEnum.values()) {
            list.add(buildSelectNode(temp.getAlphaCode(), temp.getAlphaCode()));
        }
        return list;
    }

    /**
     * 交易类型
     */
    private List<Map<String, Object>> transType() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TradeTypeEnum temp : TradeTypeEnum.values()) {
            //黑名单配置只针对系统出钱的交易类型
            if (TradeTypeEnum.REFUND.getNumeric().equals(temp.getNumeric())){
                list.add(buildSelectNode(temp.getNumeric(), temp.getDesc()));
            }
        }
        return list;
    }

    /**
     * 构建下拉框节点数据
     */
    private Map<String, Object> buildSelectNode(String id, String text) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("text", text);
        return map;
    }

	private QueryBlackListReq buildQueryBlackListReq(HttpServletRequest request){
		QueryBlackListReq req = new QueryBlackListReq();
		req.setMerchantId(request.getParameter("merchantId"));
		req.setCurrency(request.getParameter("currency"));
		req.setTradeType(request.getParameter("tradeType"));
		req.setStatus(request.getParameter("status"));
		return req;
	}

	private List<Map<String, String>> convertList(List<BlackListDTO> directiveList) {
		List<Map<String, String>> list = new ArrayList<>();
		for (BlackListDTO blacklist : directiveList) {
			Map<String, String> map = BeanUtil.beanToStringMap(blacklist);
			map.put("tradeType", TradeTypeEnum.getDescByNumeric(blacklist.getTradeType()));
			map.put("createTime", DateUtil.formatTime(blacklist.getCreateTime()));
			map.put("updateTime", DateUtil.formatTime(blacklist.getUpdateTime()));
			list.add(map);
		}
		return list;
	}

}
