package com.ipaylinks.poss.controller.acct;

import com.ipaylinks.acct.facade.enums.AcctAdjustAuditStatusEnum;
import com.ipaylinks.acct.facade.enums.AcctBookTypeEnum;
import com.ipaylinks.acct.facade.enums.AcctCollectBatchStatusEnum;
import com.ipaylinks.acct.facade.enums.AcctCollectBatchTypeEnum;
import com.ipaylinks.acct.facade.enums.AcctFreezeStatusEnum;
import com.ipaylinks.acct.facade.enums.AcctFreezeTypeEnum;
import com.ipaylinks.acct.facade.enums.AcctPipeLogStatusTypeEnum;
import com.ipaylinks.acct.facade.enums.AcctRuleStatusEnum;
import com.ipaylinks.acct.facade.enums.AcctSceneCodeEnum;
import com.ipaylinks.acct.facade.enums.AcctUserTypeEnum;
import com.ipaylinks.common.CurrencyEnum;
import com.ipaylinks.common.enums.FinanceTypeEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.rpc.ClientEnum;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 查询页面下拉单枚举值
 * @author Jerry Chen
 * @date 2018年9月7日
 */
@Controller
@RequestMapping("/acct/selectEnum")
public class AcctSelectEnumController {
    @RequestMapping("/getData")
    @ResponseBody
    public List<Map<String,Object>> getSelectData(String type) {
    	
    	List<Map<String,Object>> list = new ArrayList<>();
        if("userType".equals(type)){
        	list = getUserType();
        }
        if("currency".equals(type)){
        	list = getCurrency();
        }
        if("freezingType".equals(type)){
        	list = getFreezingType();
        }
        if("bookType".equals(type)){
        	list = getBookType();
        }
        if("orderType".equals(type)){
        	list = getOrderType();
        }
        if("financeType".equals(type)){
        	list = getFinanceType();
        }
        if("freezingStatus".equals(type)){
        	list = getFreezingStatus();
        }
        if("adjustAuditStatus".equals(type)){
        	list = getAdjustAuditStatus();
        }
        if("ruleStatus".equals(type)){
        	list = getRuleStatus();
        }
        if("sceneCode".equals(type)){
        	list = getSceneCode();
        }
        if("tradeType".equals(type)){
        	list = getTradeType();
        }
        if("sysCode".equals(type)){
        	list = getSysCode();
        }
        if("pipeLogStatus".equals(type)){
        	list = getPipeLogStatus();
        }
        if("collectType".equals(type)){
        	list = getCollectType();
        }
        if("collectStatus".equals(type)){
        	list = getCollectStatus();
        }
        return list;
    }
    private List<Map<String,Object>> getCollectStatus(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (AcctCollectBatchStatusEnum tmp : AcctCollectBatchStatusEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMessage()));
        }
        return list;
    }
    private List<Map<String,Object>> getCollectType(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (AcctCollectBatchTypeEnum tmp : AcctCollectBatchTypeEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMessage()));
        }
        return list;
    }
    private List<Map<String,Object>> getPipeLogStatus(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (AcctPipeLogStatusTypeEnum tmp : AcctPipeLogStatusTypeEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMessage()));
        }
        return list;
    }
    private List<Map<String,Object>> getSysCode(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (ClientEnum tmp : ClientEnum.values()){
            list.add(buildNode(tmp.getClientCode(),tmp.getClientCode()));
        }
        return list;
    }
    private List<Map<String,Object>> getTradeType(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (TradeTypeEnum tmp : TradeTypeEnum.values()){
            list.add(buildNode(tmp.getNumeric(),tmp.getDesc()));
        }
        return list;
    }
    private List<Map<String,Object>> getSceneCode(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (AcctSceneCodeEnum tmp : AcctSceneCodeEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMessage()));
        }
        return list;
    }
    private List<Map<String,Object>> getRuleStatus(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (AcctRuleStatusEnum tmp : AcctRuleStatusEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMessage()));
        }
        return list;
    }
    private List<Map<String,Object>> getAdjustAuditStatus(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (AcctAdjustAuditStatusEnum tmp : AcctAdjustAuditStatusEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMessage()));
        }
        return list;
    }
    private List<Map<String,Object>> getFreezingStatus(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (AcctFreezeStatusEnum tmp : AcctFreezeStatusEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMessage()));
        }
        return list;
    }
    private List<Map<String,Object>> getFinanceType(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (FinanceTypeEnum tmp : FinanceTypeEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMessage()));
        }
        return list;
    }
    private List<Map<String,Object>> getOrderType(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (TradeTypeEnum tmp : TradeTypeEnum.values()){
            list.add(buildNode(tmp.getNumeric(),tmp.getDesc()));
        }
        return list;
    }
    private List<Map<String,Object>> getBookType(){
    	List<Map<String,Object>> list = new ArrayList<>();
        for (AcctBookTypeEnum tmp : AcctBookTypeEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMessage()));
        }
        return list;
    }
    private List<Map<String,Object>> getFreezingType(){
        List<Map<String,Object>> list = new ArrayList<>();
        for (AcctFreezeTypeEnum tmp : AcctFreezeTypeEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMessage()));
        }
        return list;
    }
    private List<Map<String,Object>> getCurrency(){
        List<Map<String,Object>> list = new ArrayList<>();
        for (CurrencyEnum tmp : CurrencyEnum.values()){
            list.add(buildNode(tmp.getAlphaCode(),tmp.getAlphaCode()));
        }
        return list;
    }
    private List<Map<String,Object>> getUserType(){
        List<Map<String,Object>> list = new ArrayList<>();
        for (AcctUserTypeEnum tmp : AcctUserTypeEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMessage()));
        }
        return list;
    }
    private Map<String,Object> buildNode(String id, String text){
        Map<String,Object> map = new HashMap<>(2);
        map.put("id",id);
        map.put("text",text);
        return map;
    }
}
