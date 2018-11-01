package com.ipaylinks.poss.controller.recon;

import com.ipaylinks.verify.facade.dto.GlobalDTO;
import com.ipaylinks.verify.facade.enums.AuditStateEnum;
import com.ipaylinks.verify.facade.enums.ChannelsTradeRecon;
import com.ipaylinks.verify.facade.enums.CorrectionStatusEnum;
import com.ipaylinks.verify.facade.enums.OperateTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 对账中相关的全局餐宿控制类
 */
@Controller
@RequestMapping("/recon/global")
public class ReconGlobalController {
    /**
     * 获取对账渠道列表
     *
     * @return
     */
    @RequestMapping("/channelList")
    @ResponseBody
    public java.util.List<GlobalDTO> channelList() {
        java.util.List<GlobalDTO> dtoList = new java.util.ArrayList<GlobalDTO>();
        ChannelsTradeRecon[] values = ChannelsTradeRecon.values();
        for (ChannelsTradeRecon en : values) {
            GlobalDTO dto = new GlobalDTO();
            dto.setCode(en.getCode());
            dto.setMessage(en.getName());
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 处理处理方式列表
     *
     * @return
     */
    @RequestMapping("/correctionTypeList")
    @ResponseBody
    public java.util.List<GlobalDTO> correctionTypeList() {
        java.util.List<GlobalDTO> dtoList = new java.util.ArrayList<GlobalDTO>();
        OperateTypeEnum[] values = OperateTypeEnum.values();
        for (OperateTypeEnum en : values) {
            GlobalDTO dto = new GlobalDTO();
            dto.setCode(en.getCode());
            dto.setMessage(en.getDesc());
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 差错处理结果状态列表
     *
     * @return
     */
    @RequestMapping("/correctionStatusList")
    @ResponseBody
    public java.util.List<GlobalDTO> correctionStatusList() {
        java.util.List<GlobalDTO> dtoList = new java.util.ArrayList<GlobalDTO>();
        CorrectionStatusEnum[] values = CorrectionStatusEnum.values();
        for (CorrectionStatusEnum en : values) {
            GlobalDTO dto = new GlobalDTO();
            dto.setCode(en.getCode());
            dto.setMessage(en.getMessage());
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 审核状态列表
     *
     * @return
     */
    @RequestMapping("/auditStatusList")
    @ResponseBody
    public java.util.List<GlobalDTO> auditStatusList() {
        java.util.List<GlobalDTO> dtoList = new java.util.ArrayList<>();
        AuditStateEnum[] values = AuditStateEnum.values();
        for (AuditStateEnum en : values) {
            GlobalDTO dto = new GlobalDTO();
            dto.setCode(en.getCode());
            dto.setMessage(en.getDesc());
            dtoList.add(dto);
        }
        return dtoList;
    }
}
