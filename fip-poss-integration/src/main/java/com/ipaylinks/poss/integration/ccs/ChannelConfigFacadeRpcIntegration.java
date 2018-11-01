package com.ipaylinks.poss.integration.ccs;

import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.poss.exception.InvokeException;
import com.pay.channel.faced.model.ChannelConfigBo;
import com.pay.channel.faced.model.ChannelConfigResponse;
import com.pay.channel.rpc.channelconfig.ChannelConfigFacadeRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hubin.wei
 * @date 2018/8/30 19:16
 **/
@Service
public class ChannelConfigFacadeRpcIntegration {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private ChannelConfigFacadeRpc channelConfigFacadeRpc;


    /**
     * 根据渠道号查询二级商户号
     * @return
     */
    public List<ChannelConfigBo> channelConfigQuery(ChannelConfigBo configBo) throws Exception{
        log.info("查询二级商户号, request={}", configBo);
        ChannelConfigResponse response= null;
        try {
            response =  channelConfigFacadeRpc.channelConfigQuery(configBo);
        } catch (Exception e) {
            log.error("查询二级商户号异常----", e);
            throw new InvokeException("调用channel异常");
        }
        log.info("channelConfigQuery, response={}",response);
        if(!BaseRespStatusEnum.SUCCESS.getCode().equals(response.getResponseStatus())){
            throw new InvokeException("channel返回:"+ response.getResponseDesc());
        }
        return response.getResponseList();
    };

}



