package com.ipaylinks.poss.integration.common;

import com.ipay.commonService.channel.model.POrganization;
import com.ipaylinks.cmp.css.facade.enums.*;
import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.enums.BizStatusEnum;
import com.ipaylinks.common.enums.FinanceTypeEnum;
import com.ipaylinks.common.rpc.response.SingleResponse;
import com.ipaylinks.mcs.facade.enums.SettlementCommonEnum;
import com.ipaylinks.poss.integration.common.dto.EnumEntity;
import com.ipaylinks.poss.integration.css.liquidation.MertSettlementConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 枚举值转换
 *
 * @author hongxu.gao
 * @date 2018/8/22 10:06
 */
@Component
public class EnumConverter {

    private static final Map<String, Enum[]> enumValuesMap = new HashMap<>();
    private static final List<Map<String,Object>> channelList = new ArrayList<>();

    @Autowired
    private MertSettlementConfigClient mertSettlementConfigClient;

    // 下列枚举是EnumEntity不能兼容（单独处理）
    public static final String TRANS_TYPE = "transType"; // 交易类型 TradeTypeEnum(numeric-desc)
    public static final String CURRENCY_TYPE = "currencyType"; // 币种类型 CurrencyEnum(alphaCode-zhCnMessage)
    public static final String TRANS_METHOD = "transMethod"; // 交易方式 TransTypeEnum(index-name)
    public static final String COUNTRY_TYPE = "countryType"; // 国家 Country3166_1
    public static final String CHANNEL_TYPE = "channelType"; // 渠道 通过渠道服务获取


    // 下列枚举是EnumEntity能兼容
    public static final String TRANS_STATUS = "transStatus"; // 交易状态
    public static final String PAY_METHOD = "payMethod"; // 支付类型
    public static final String SETTLEMENT_CYCLE = "settlementCycle"; // 结算周期
    public static final String SETTLEMENT_DAY_TYPE = "settlementDayType"; // 结算日周期
    public static final String AUTO_WITHDRAWAL = "autoWithdrawal"; // 自动提现
    public static final String FINANCE_TYPE = "financeType"; // 费用类型 （code-message）
    public static final String SETTLE_STATUS = "settleStatus"; // 结算状态
    public static final String ACCOUNT_STATUS = "accountStatus"; // 记账状态
    public static final String SUMMARY_STATUS = "summaryStatus"; // 汇总状态
    public static final String CHARGE_METHOD = "chargeMethod"; // 收费方式
    public static final String PRIORITY = "priority"; // 优先级
    public static final String WITHDRAW_STATUS = "withdrawStatus"; // 出款状态
    public static final String BIZ_STATUS = "bizStatus"; // 支付状态

    @PostConstruct
    private void initEnumData() {
        enumValuesMap.put(TRANS_STATUS, BaseRespStatusEnum.values());
        enumValuesMap.put(PAY_METHOD, PayTypeEnum.values());
        enumValuesMap.put(SETTLEMENT_CYCLE, SettlementCommonEnum.SettlementCycleType.values());
        enumValuesMap.put(SETTLEMENT_DAY_TYPE, SettlementCommonEnum.SettlementDayType.values());
        enumValuesMap.put(AUTO_WITHDRAWAL, SettlementCommonEnum.AutoWithdrawal.values());
        enumValuesMap.put(FINANCE_TYPE, FinanceTypeEnum.values());
        enumValuesMap.put(SETTLE_STATUS, SettleEnum.values());
        enumValuesMap.put(ACCOUNT_STATUS, AccountStatusEnum.values());
        enumValuesMap.put(SUMMARY_STATUS, SummaryStatusEnums.values());
        enumValuesMap.put(CHARGE_METHOD, ChargeMethodEnum.values());
        enumValuesMap.put(PRIORITY, SettlementCommonEnum.Priority.values());
        enumValuesMap.put(WITHDRAW_STATUS, WithdrawStatusEnum.values());
        enumValuesMap.put(BIZ_STATUS, BizStatusEnum.values());
        loadChannelList();
    }

    public static Enum[] getEnumValues(String type) {
        if (enumValuesMap.containsKey(type)) {
            return enumValuesMap.get(type);
        }
        return null;
    }

    public static List<Map<String,Object>> getChannelList() {
        return channelList;
    }

    /**
     * 公共转换（code、desc类型的转换）
     */
    public static String convertEnumCode(String type, String code) {
        List<EnumEntity> entityList = EnumEntity.convert(enumValuesMap.get(type));
        for (EnumEntity enumEntity : entityList) {
            if (enumEntity.getCode().equals(code)) {
                return enumEntity.getDesc();
            }
        }
        return null;
    }

    /**
     * 转换渠道code
     */
    public static String convertChannelCode(String code) {
        for (Map<String,Object> temp : channelList) {
            if (temp.get("id").equals(code)) {
                return temp.get("text").toString();
            }
        }
        return null;
    }

    /**
     * 加载渠道数据
     */
    private void loadChannelList() {
        SingleResponse response = mertSettlementConfigClient.queryChannelInfo();
        List<POrganization> list = (List<POrganization>) response.getResponseObj();
        for (POrganization temp : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",temp.getOrgCode());
            map.put("text",temp.getOrgName());
            channelList.add(map);
        }
    }
}
