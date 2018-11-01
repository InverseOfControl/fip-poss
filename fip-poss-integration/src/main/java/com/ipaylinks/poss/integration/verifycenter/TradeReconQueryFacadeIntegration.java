package com.ipaylinks.poss.integration.verifycenter;


import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.enums.TradeTypeEnum;
import com.ipaylinks.common.exception.BaseExceptionCode;
import com.ipaylinks.common.rpc.BaseResponse;
import com.ipaylinks.common.rpc.response.PageQueryResponse;
import com.ipaylinks.poss.integration.oss.UFSClient;
import com.ipaylinks.poss.integration.verifycenter.dto.FileUploadRequest;
import com.ipaylinks.poss.util.DateUtils;
import com.ipaylinks.poss.util.RespUtils;
import com.ipaylinks.poss.util.ResponseUtil;
import com.ipaylinks.verify.facade.ArsChannelFileRecordFacade;
import com.ipaylinks.verify.facade.TradeReconQueryFacade;
import com.ipaylinks.verify.facade.VerifyFacade;
import com.ipaylinks.verify.facade.dto.VerifyDiffReqDTO;
import com.ipaylinks.verify.facade.enums.*;
import com.ipaylinks.verify.facade.req.ArsChannelFileRecordRequest;
import com.ipaylinks.verify.facade.req.TradeReconResultRequest;
import com.ipaylinks.verify.facade.req.TradeReconTotalRequest;
import com.ipaylinks.verify.facade.resp.ArsChannelFileRecordResp;
import com.ipaylinks.verify.facade.resp.ArsChannelFileRecordResponse;
import com.ipaylinks.verify.facade.resp.TradeReconResultResponse;
import com.ipaylinks.verify.facade.resp.TradeReconTotalResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * 对账中心服务集成
 *
 * @author alan
 * @date 20180818
 */
@Component("tradeReconQueryFacadeClient")
public class TradeReconQueryFacadeIntegration {

    private static Logger logger = LogManager.getLogger(TradeReconQueryFacadeIntegration.class);

    @Autowired(required = false)
    private TradeReconQueryFacade tradeReconQueryFacade;

    @Autowired(required = false)
    private VerifyFacade verifyFacade;

    @Autowired(required = false)
    private ArsChannelFileRecordFacade arsChannelFileRecordFacade;

    @Autowired(required = false)
    private UFSClient ufsClient;

    /**
     * 对账结果查询
     *
     * @param request 查询条件
     * @return 返回列表
     */
    public PageQueryResponse<TradeReconResultResponse> queryCorrectionResult(TradeReconResultRequest request) {
        PageQueryResponse<TradeReconResultResponse> resp = new PageQueryResponse<>();
        try {
            resp = tradeReconQueryFacade.queryCorrectionResult(request);
            if (ResponseUtil.isSuccess(resp) && null != resp.getPagedResult()) {
                List<TradeReconResultResponse> list = resp.getPagedResult().getDataList();
                list.forEach(item -> {
                    item.setAccountingStatus(AccountingStatusEnum.getMessageByCode(item.getAccountingStatus()));
                    item.setReconResult(ReconResultEnum.getMessageByCode(item.getReconResult()));
                    //item.setAccountingType(AccountingTypeEnum.getMessageByCode(item.getAccountingType()));
                    item.setCorrectionStatus(CorrectionStatusEnum.getMessageByCode(item.getCorrectionStatus()));
                    item.setAuditStatus(AuditStateEnum.getMessageByCode(item.getAuditStatus()));
                    if (item.getTransType() != null) {
                        item.setTransType(TradeTypeEnum.getDescByNumeric(item.getTransType()));
                    }
                    item.setCorrectionType(OperateTypeEnum.getMessageByCode(item.getCorrectionType()));
                });
            }
        } catch (Exception e) {
            resp.setResponseStatus(BaseRespStatusEnum.FAIL.getCode());
            resp.setResponseCode(BaseExceptionCode.DUBBO_NPE.getCode());
            resp.setResponseMsg(BaseExceptionCode.DUBBO_NPE.getMsg());
        }
        return resp;

    }

    /**
     * 对账结果汇总查询
     *
     * @param request 请求对象
     * @return 查询结果
     */
    public PageQueryResponse<TradeReconTotalResponse> queryCorrectionResultTotal(TradeReconTotalRequest request) {
        PageQueryResponse<TradeReconTotalResponse> resp = new PageQueryResponse<>();
        try {
            resp = tradeReconQueryFacade.queryTradeReconResultTotal(request);
        } catch (Exception e) {
            logger.error("对账汇总查询异常,异常信息:", e);
            resp.setResponseStatus(BaseRespStatusEnum.FAIL.getCode());
            resp.setResponseCode(BaseExceptionCode.DUBBO_NPE.getCode());
            resp.setResponseMsg(BaseExceptionCode.DUBBO_NPE.getMsg());
        }
        return resp;
    }

    /**
     * 系统交易查询
     *
     * @param request 请求对象
     * @return 查询结果
     */
    public PageQueryResponse<TradeReconResultResponse> querySettlementDetail(TradeReconResultRequest request) {
        PageQueryResponse<TradeReconResultResponse> resp = new PageQueryResponse<>();
        try {
            resp = tradeReconQueryFacade.querySettlementDetail(request);
            if (ResponseUtil.isSuccess(resp) && null != resp.getPagedResult()) {
                List<TradeReconResultResponse> list = resp.getPagedResult().getDataList();
                for (TradeReconResultResponse response : list) {
                    response.setReconResult(ReconHandleResultEnum.getMessageByCode(response.getReconResult()));
                    response.setTransType(TradeTypeEnum.getDescByNumeric(response.getTransType()));
                }
                resp.getPagedResult().setDataList(list);
            }

        } catch (Exception e) {
            logger.error("系统交易查询异常,异常信息:", e);
            resp.setResponseStatus(BaseRespStatusEnum.FAIL.getCode());
            resp.setResponseCode(BaseExceptionCode.DUBBO_NPE.getCode());
            resp.setResponseMsg(BaseExceptionCode.DUBBO_NPE.getMsg());
        }
        return resp;
    }


    /**
     * 渠道文件导入记录查询
     *
     * @param request 查询条件
     * @return 返回记录列表
     */
    public PageQueryResponse<ArsChannelFileRecordResponse> queryChannelFileRecord(ArsChannelFileRecordRequest request) {
        logger.info("渠道文件导入记录查询 request {}", request);
        PageQueryResponse<ArsChannelFileRecordResponse> resp = new PageQueryResponse<>();
        try {
            resp = tradeReconQueryFacade.queryReconFileRecord(request);
            List<ArsChannelFileRecordResponse> list;
            if (ResponseUtil.isSuccess(resp) && null != resp.getPagedResult()) {
                list = resp.getPagedResult().getDataList();
                list.parallelStream().forEach(dto -> {
                    dto.setHandleStatus(ArsChannelFileHandleStatusEnum.getMessageByCode(dto.getHandleStatus()));
                    dto.setReconStatus(ReconHandleResultEnum.getMessageByCode(dto.getReconStatus()));
                    dto.setAccountingStatus(AccountingStatusEnum.getMessageByCode(dto.getAccountingStatus()));
                    dto.setFileType(ReconciliationTypeEnum.getMessageByCode(dto.getFileType()));
                });
                resp.getPagedResult().setDataList(list);
            }
        } catch (Exception e) {
            logger.error("渠道文件记录查询异常:" + e);
            resp.setResponseStatus(BaseRespStatusEnum.FAIL.getCode());
            resp.setResponseCode(BaseExceptionCode.DUBBO_NPE.getCode());
            resp.setResponseMsg(BaseExceptionCode.DUBBO_NPE.getMsg());
        }
        return resp;
    }


    /**
     * 处理差错处理方式
     *
     * @param id             订单号
     * @param correctionType 差错处理方式
     * @return 返回处理结果
     */
    public BaseResponse dealInitialAudit(BigDecimal id, String correctionType, String userName, String auditMode) {
        BaseResponse resp = new BaseResponse();
        try {
            VerifyDiffReqDTO verifyDiffReqDTO = new VerifyDiffReqDTO();
            verifyDiffReqDTO.setId(id);
            OperateTypeEnum operateTypeEnum = null;
            if (OperateTypeEnum.BALANCE_ORDER.getCode().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.BALANCE_ORDER;
            } else if (OperateTypeEnum.VERIFY_REFUND.getCode().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_REFUND;
            } else if (OperateTypeEnum.VERIFY_FILL.getCode().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_FILL;
            } else if (OperateTypeEnum.VERIFY_SYSTEM.getCode().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_SYSTEM;
            } else if (OperateTypeEnum.VERIFY_CHANNEL.getCode().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_CHANNEL;
            } else if (OperateTypeEnum.VERIFY_RECOVER.getCode().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_RECOVER;
            } else if (OperateTypeEnum.VERIFY_BAD_DEBT.getCode().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_BAD_DEBT;
            }
            verifyDiffReqDTO.setOperateType(operateTypeEnum);
            this.completingVerifyReqDTO(verifyDiffReqDTO, userName, auditMode);
            resp = verifyFacade.auditDifference(verifyDiffReqDTO);
        } catch (Exception e) {
            resp.setResponseStatus(BaseRespStatusEnum.FAIL.getCode());
            resp.setResponseCode(BaseExceptionCode.DUBBO_NPE.getCode());
            resp.setResponseMsg(BaseExceptionCode.DUBBO_NPE.getMsg());
        }
        return resp;
    }

    /**
     * 处理差错处理方式
     *
     * @param id             订单号
     * @param correctionType 差错处理方式
     * @return 返回处理结果
     */
    public BaseResponse dealRepeatAudit(BigDecimal id, String correctionType, String userName, String auditMode) {
        BaseResponse resp = new BaseResponse();
        try {
            VerifyDiffReqDTO verifyDiffReqDTO = new VerifyDiffReqDTO();
            verifyDiffReqDTO.setId(id);
            OperateTypeEnum operateTypeEnum = null;
            if (OperateTypeEnum.BALANCE_ORDER.getDesc().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.BALANCE_ORDER;
            } else if (OperateTypeEnum.VERIFY_REFUND.getDesc().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_REFUND;
            } else if (OperateTypeEnum.VERIFY_FILL.getDesc().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_FILL;
            } else if (OperateTypeEnum.VERIFY_SYSTEM.getDesc().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_SYSTEM;
            } else if (OperateTypeEnum.VERIFY_CHANNEL.getDesc().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_CHANNEL;
            } else if (OperateTypeEnum.VERIFY_RECOVER.getDesc().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_RECOVER;
            } else if (OperateTypeEnum.VERIFY_BAD_DEBT.getDesc().equals(correctionType)) {
                operateTypeEnum = OperateTypeEnum.VERIFY_BAD_DEBT;
            }
            verifyDiffReqDTO.setOperateType(operateTypeEnum);
            this.completingVerifyReqDTO(verifyDiffReqDTO, userName, auditMode);
            resp = verifyFacade.auditDifference(verifyDiffReqDTO);
        } catch (Exception e) {
            resp.setResponseStatus(BaseRespStatusEnum.FAIL.getCode());
            resp.setResponseCode(BaseExceptionCode.DUBBO_NPE.getCode());
            resp.setResponseMsg(BaseExceptionCode.DUBBO_NPE.getMsg());
        }
        return resp;
    }

    /**
     * 渠道文件上传
     *
     * @param request 请求
     * @return 返回
     */
    public BaseResponse createChannelFileRecord(FileUploadRequest request) {
        ArsChannelFileRecordResp resp = new ArsChannelFileRecordResp();
        try {
            if (request == null || request.getFile() == null || StringUtils.isEmpty(request.getOrgCode()) || StringUtils.isEmpty(request.getUploadType())) {
                RespUtils.setToFail(resp, BaseExceptionCode.VALIDATOR_ERROR, "必传参数为空");
                return resp;
            }
            initUploadParams(request);
            boolean uploadFlag = ufsClient.putFile(request.getSavePath(), request.getFile().getInputStream());
            if (!uploadFlag) {
                RespUtils.setToFail(resp, BaseExceptionCode.VALIDATOR_ERROR, "上传失败, 请检查oss配置");
            }
            ArsChannelFileRecordRequest fileRecordRequest = new ArsChannelFileRecordRequest();
            buildFileRecordRequest(request, fileRecordRequest);
            fileRecordRequest.setOrgName(ChannelsTradeRecon.getNameByCode(fileRecordRequest.getOrgCode()));
            resp = arsChannelFileRecordFacade.addarsChannelFileRecord(fileRecordRequest);
        } catch (IOException e) {
            logger.error("渠道文件上传异常:" + e);
            resp.setResponseStatus(BaseRespStatusEnum.FAIL.getCode());
            resp.setResponseCode(BaseExceptionCode.DUBBO_NPE.getCode());
            resp.setResponseMsg(BaseExceptionCode.DUBBO_NPE.getMsg());
        }
        return resp;
    }

    /**
     * 初始化上传参数
     *
     * @param request 请求
     */
    private void initUploadParams(FileUploadRequest request) {
        String originalFileName = request.getFile().getOriginalFilename();
        request.setOriginFileName(originalFileName.substring(0, originalFileName.lastIndexOf(".")));
        request.setSuffix(originalFileName.substring(originalFileName.lastIndexOf(".")));
        String savePath = "verify-center" + "/" + request.getOrgCode() + "/" + request.getUploadType() + "/" + DateUtils.getCurrentDate() + "/" + UUID.randomUUID().toString().replaceAll("-", "") + request.getSuffix();
        request.setSavePath(savePath);
        request.setOriginFileName(originalFileName);
    }

    /**
     * 构建渠道文件记录请求参数
     *
     * @param uploadRequest     文件上传请求
     * @param fileRecordRequest 文件记录请求
     */
    private void buildFileRecordRequest(FileUploadRequest uploadRequest, ArsChannelFileRecordRequest fileRecordRequest) {
        fileRecordRequest.setFilePath(uploadRequest.getSavePath());
        fileRecordRequest.setOrgCode(uploadRequest.getOrgCode());
        fileRecordRequest.setFileExtensionName(uploadRequest.getSuffix());
        fileRecordRequest.setFileType(uploadRequest.getUploadType());
        fileRecordRequest.setFileName(uploadRequest.getOriginFileName());
    }

    /**
     * 初审复审赋值
     *
     * @param verifyDiffReqDTO 请求对象
     * @param userName         登录用户
     */
    private void completingVerifyReqDTO(VerifyDiffReqDTO verifyDiffReqDTO, String userName, String auditMode) {
        if (AuditModeEnum.INITIAL_AUDIT.getCode().equals(auditMode)) {
            verifyDiffReqDTO.setInitialAuditor(userName);
            verifyDiffReqDTO.setAuditModeEnum(AuditModeEnum.INITIAL_AUDIT);
        } else if (AuditModeEnum.REPEAT_AUDIT.getCode().equals(auditMode)) {
            verifyDiffReqDTO.setRepeatAuditor(userName);
            verifyDiffReqDTO.setAuditModeEnum(AuditModeEnum.REPEAT_AUDIT);
        }
    }
}
