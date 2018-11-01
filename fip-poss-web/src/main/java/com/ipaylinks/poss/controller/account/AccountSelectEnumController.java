package com.ipaylinks.poss.controller.account;

import com.ipaylinks.accounting.facade.enums.CheckEntryResultEnum;
import com.ipaylinks.acct.facade.enums.AcctFreezeTypeEnum;
import com.ipaylinks.acct.facade.enums.AcctUserTypeEnum;
import com.ipaylinks.common.CurrencyEnum;
import com.ipaylinks.common.enums.FinanceTypeEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/account/selectEnum")
public class AccountSelectEnumController {
    @RequestMapping("/getData")
    @ResponseBody
    public List<Map<String,Object>> getSelectData(String type) {
    	
    	List<Map<String,Object>> list = new ArrayList<>();
        if("orderType".equals(type)){
        	list = getOrderType();
        }else if("currency".equals(type)){
        	list = getCurrency();
        }else if("financeType".equals(type)){
        	list = getFinanceType();
        }else if("checkEntryResult".equals(type)){
            list = getCheckEntryResult();
        }
        return list;
    }
    
    private List<Map<String,Object>> getCheckEntryResult(){
        List<Map<String,Object>> list = new ArrayList<>();
         
        for (CheckEntryResultEnum tmp : CheckEntryResultEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getName()));
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
    private List<Map<String,Object>> getCurrency(){
        List<Map<String,Object>> list = new ArrayList<>();
         
        for (CurrencyEnum tmp : CurrencyEnum.values()){
            list.add(buildNode(tmp.getAlphaCode(),tmp.getAlphaCode()));
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
    private Map<String,Object> buildNode(String id, String text){
        Map<String,Object> map = new HashMap<>(2);
        map.put("id",id);
        map.put("text",text);
        return map;
    }
}
