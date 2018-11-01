package com.ipaylinks.poss.controller.charge;

import com.ipaylinks.ccs.facade.enums.*;
import com.ipaylinks.common.CardOrgEnum;
import com.ipaylinks.common.Country3166_1;
import com.ipaylinks.common.CurrencyEnum;
import com.ipaylinks.common.TransTypeEnum;
import com.ipaylinks.poss.integration.ccs.ChannelConfigFacadeRpcIntegration;
import com.ipaylinks.poss.integration.common.EnumConverter;
import com.pay.channel.faced.model.ChannelConfigBo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.plaf.synth.Region;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hubin.wei
 * @date 2018/8/27 17:38
 **/
@Controller
@RequestMapping("/charge/selectEnum")
public class ChargeSelectEnumController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired(required = false)
    private ChannelConfigFacadeRpcIntegration channelConfigFacadeRpcIntegration;

    @RequestMapping("/getData")
    @ResponseBody
    public List<Map<String,Object>> getSelectData(String type, boolean selAll) {
        List<Map<String,Object>> list=null;
        switch (type){
            case "currency":
                list = getCurrency();
                break;
            case "chargeScene":
                list = getChargeScene();
                break;
            case "chargeStatus":
                list = getChargeStatus();
                break;
            case "cardOrg":
                list = getCardOrg(selAll);
                break;
            case "countryCode":
                list = getCountryCode(selAll);
                break;
            case "transMethod":
                list = transMethod();
                break;
            case "chargeMode":
                list = getChargeMode();
                break;
            case "chargeRule":
                list = getChargeRule();
                break;
            case "chargeWay":
                list = getChargeWay();
                break;
            case "payMode":
                list = getPayMode(selAll);
                break;
            case "region":
                list = getRegion(selAll);
                break;
            case "cardType":
                list = getCardType(selAll);
                break;
            case "tradeModel":
                list = getTradeModel(selAll);
                break;
            case "transactionMode":
                list = getTransactionMode(selAll);
                break;
            case "remitTimeliness":
                list = getRemitTimeliness(selAll);
                break;
            case "accountType":
                list = getAccountType(selAll);
                break;
            case "settlementCycle":
                list = getSettlementCycle();
                break;
            case "tradeStatus":
                list = getSradeStatus(selAll);
                break;
            default:
                list = new ArrayList<>();
                break;
        }
        return list;
    }



    @RequestMapping("/getChannelCodeList")
    @ResponseBody
    public List<Map<String,Object>> getChannelCodeList() {
        return  EnumConverter.getChannelList();
    }



    @RequestMapping("/secondaryAccountListByCode")
    @ResponseBody
    public List<Map<String,Object>> secondaryAccountListByCode(String channelCode) {
        List<Map<String,Object>> mapArrayList = new ArrayList<>();
        List<ChannelConfigBo> list = null;
        try{
            ChannelConfigBo configBo = new ChannelConfigBo();
            configBo.setOrgCode(channelCode);
            configBo.setStatus(1);
            list = channelConfigFacadeRpcIntegration.channelConfigQuery(configBo);
        }catch (Exception e){
            logger.error("getChannelCodeList error" ,e);
        }
        if(list !=null){
            for(ChannelConfigBo response:list){
                mapArrayList.add(buildNode(response.getOrgMerchantCode(), response.getOrgMerchantCode()));
            }
        }
        return mapArrayList;
    }



    private List<Map<String,Object>> getChargeMode() {
        List<Map<String,Object>> list = new ArrayList<>();
        for (ChargeModeEnum tmp : ChargeModeEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }

    private List<Map<String,Object>> getChargeRule() {
        List<Map<String,Object>> list = new ArrayList<>();
        for (ChargeRuleEnum tmp : ChargeRuleEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }

    private List<Map<String,Object>> getChargeWay() {
        List<Map<String,Object>> list = new ArrayList<>();
        for (ChargeWayEnum tmp : ChargeWayEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }

    private List<Map<String,Object>> getRegion(boolean selAll) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(selAll){
            list.add(buildAllSelectNode("*","全选"));
        }
        for (RegionEnum tmp : RegionEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }

    private List<Map<String,Object>> getPayMode(boolean selAll) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(selAll){
            list.add(buildAllSelectNode("*","全选"));
        }
        for (PayModeEnum tmp : PayModeEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }


    private List<Map<String,Object>> getSradeStatus(boolean selAll) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(selAll){
            list.add(buildAllSelectNode("*","全选"));
        }
        for (TradeStatusEnum tmp : TradeStatusEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }


    private List<Map<String,Object>> getRemitTimeliness(boolean selAll) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(selAll){
            list.add(buildAllSelectNode("*","全选"));
        }
        for (RemitTimelinessEnum tmp : RemitTimelinessEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }

    private List<Map<String,Object>> getTradeModel(boolean selAll) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(selAll){
            list.add(buildAllSelectNode("*","全选"));
        }
        for (TradeModelEnum tmp : TradeModelEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }

    private List<Map<String,Object>> getTransactionMode(boolean selAll) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(selAll){
            list.add(buildAllSelectNode("*","全选"));
        }
        for (TransTypeEnum tmp : TransTypeEnum.values()){
            list.add(buildNode(tmp.getName(),tmp.getName()));
        }
        return list;
    }


    private List<Map<String,Object>> getAccountType(boolean selAll) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(selAll){
            list.add(buildAllSelectNode("*","全选"));
        }
        for (AccountTypeEnum tmp : AccountTypeEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }

    private List<Map<String,Object>> getCardType(boolean selAll) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(selAll){
            list.add(buildAllSelectNode("*","全选"));
        }
        for (CardTypeEnum tmp : CardTypeEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
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

    /**
     * 交易方式
     * @return
     */
    private List<Map<String, Object>> transMethod() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TransTypeEnum temp : TransTypeEnum.values()) {
            list.add(buildNode(temp.getName(), temp.getName()));
        }
        return list;
    }


    private List<Map<String, Object>> getChargeScene(){
        List<Map<String,Object>> list = new ArrayList<>();
        for (ChargeSceneEnum tmp : ChargeSceneEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }


    private List<Map<String,Object>> getChargeStatus() {
        List<Map<String,Object>> list = new ArrayList<>();
        for (ChargeStrategyStatusEnum tmp : ChargeStrategyStatusEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }


    private List<Map<String,Object>> getCardOrg(boolean selAll) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(selAll){
            list.add(buildNode("*","全选"));
        }
        for (CardOrgEnum tmp : CardOrgEnum.values()){
            if(StringUtils.isNotBlank(tmp.getOrgName()) && !"LOCAL".equalsIgnoreCase(tmp.getOrgName())){
                list.add(buildNode(tmp.getOrgName(),tmp.getOrgName()));
            }
        }
        return list;
    }

    /**
     *  取英文二字码
     * @return
     */
    private List<Map<String,Object>> getCountryCode(boolean selAll) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(selAll){
            list.add(buildNode("*","全选"));
        }
        for (Country3166_1 tmp : Country3166_1.values()){
            list.add(buildNode(tmp.getLetter3(),tmp.getCountryEN()+" "+tmp.getCountryCN()));
        }
        return list;
    }


    private List<Map<String,Object>> getSettlementCycle() {
        List<Map<String,Object>> list = new ArrayList<>();
        for (ChargeSettleCycleEnum tmp : ChargeSettleCycleEnum.values()){
            list.add(buildNode(tmp.getCode(),tmp.getMsg()));
        }
        return list;
    }



    private Map<String,Object> buildNode(String id, String text){
        Map<String,Object> map = new HashMap<>(2);
        map.put("id",id);
        map.put("text",text);
        return map;
    }



    private Map<String,Object> buildAllSelectNode(String id, String text){
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("text", text);
        map.put("selected",true);
        return map;
    }
}
