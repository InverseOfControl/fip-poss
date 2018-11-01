package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.common.Country3166_1;
import com.ipaylinks.common.CurrencyEnum;
import com.ipaylinks.common.TransTypeEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.poss.integration.common.EnumConverter;
import com.ipaylinks.poss.integration.common.dto.EnumEntity;
import com.ipaylinks.poss.integration.css.liquidation.MertSettlementConfigClient;
import com.ipaylinks.poss.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 清算管理-枚举值下拉框
 *
 * @author hongxu.gao
 * @date 2018/8/21 15:19
 */
@Controller
@RequestMapping("/select/")
public class LiquidationEnumDataController {

    @Autowired
    private MertSettlementConfigClient mertSettlementConfigClient;

    @RequestMapping("/getData")
    @ResponseBody
    public List<Map<String, Object>> selectData(String type) {
        // 特殊处理
        if(type.equals(EnumConverter.CHANNEL_TYPE)) return EnumConverter.getChannelList();
        if(type.equals(EnumConverter.COUNTRY_TYPE)) return countyrType();
        if (type.startsWith(EnumConverter.CURRENCY_TYPE)) return currencyType();
        if (type.equals(EnumConverter.TRANS_TYPE)) return transType();
        if (type.equals(EnumConverter.TRANS_METHOD)) return transMethod();
        // 公共处理
        if(type.startsWith(EnumConverter.SETTLEMENT_CYCLE)) type = EnumConverter.SETTLEMENT_CYCLE;
        if(type.startsWith(EnumConverter.SETTLEMENT_DAY_TYPE)) type = EnumConverter.SETTLEMENT_DAY_TYPE;
        return doSelectData(EnumConverter.getEnumValues(type), type);
    }

    private List<Map<String, Object>> doSelectData(Enum[] enumValues, String type) {
        List<EnumEntity> entityList = EnumEntity.convert(enumValues);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (EnumEntity enumEntity : entityList) {
            if (type.equals(EnumConverter.FINANCE_TYPE)) {
                resultList.add(buildSelectNode(enumEntity.getCode(), enumEntity.getMessage()));
            } else {
                resultList.add(buildSelectNode(enumEntity.getCode(), enumEntity.getDesc()));
            }
        }
        return resultList;
    }

    /**
     * 交易类型
     */
    private List<Map<String, Object>> transType() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TradeTypeEnum temp : TradeTypeEnum.values()) {
            list.add(buildSelectNode(temp.getNumeric(), temp.getDesc()));
        }
        return list;
    }

    /**
     * 币种类型
     */
    private List<Map<String, Object>> currencyType() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CurrencyEnum temp : CurrencyEnum.values()) {
            list.add(buildSelectNode(temp.getAlphaCode(), temp.getAlphaCode()));
        }
        return list;
    }

    /**
     * 国家
     */
    private List<Map<String, Object>> countyrType() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Country3166_1 temp : Country3166_1.values()) {
            list.add(buildSelectNode(temp.getLetter2(), temp.getCountryCN()));
        }
        return list;
    }

    /**
     * 交易方式
     * @return
     */
    private List<Map<String, Object>> transMethod() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TransTypeEnum temp : TransTypeEnum.values()) {
            list.add(buildSelectNode(temp.getName(), temp.getName()));
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

    /**
     * 日期计算（用于页面日期初始化）
     *
     * @author hongxu.gao
     * @date 2018/8/30 11:22
     * @return java.lang.String
     */
    @RequestMapping("/calDate")
    @ResponseBody
    public String calDate(int month) {
        return DateUtils.initFormDate(month);
    }

}
