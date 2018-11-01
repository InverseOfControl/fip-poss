package com.ipaylinks.poss.controller.liquidation;

import com.ipaylinks.poss.util.DateUtils;
import com.ipaylinks.poss.util.DownLoadUtil;
import com.ipaylinks.poss.util.SimpleExcelGenerator;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * 下载管理
 *
 * @author hongxu.gao
 * @date 2018/9/5 10:00
 */
@Component
public class DownloadController {

    private Logger logger = LoggerFactory.getLogger(DownloadController.class);

    public void downLoad(List list, HttpServletResponse response, HttpServletRequest request, String type) {
        String title = buildExcelTitle(type);
        try {
            Workbook grid = SimpleExcelGenerator.generateGrid(title, buildFieldDesc(type), buildFieldCode(type), list);
            String fileName = DownLoadUtil.encodeFileName(title + DateUtils.getCurrentDate(), request.getHeader("User-Agent"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xls");
            OutputStream os = response.getOutputStream();
            grid.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error(title + "下载失败，异常：{}", e);
        }
    }

    private String buildExcelTitle(String type) {
        String title = null;
        if(type.equals("channelOrder")) title = "渠道流水单";
        if(type.equals("settlementSummary")) title = "结算汇总单";
        if(type.equals("mertFee")) title = "商户手续费";
        if(type.equals("channelCost")) title = "渠道成本";
        if(type.equals("liquidationOrder")) title = "清算订单";
        if(type.equals("settlementDetail")) title = "结算明细";
        if(type.equals("settlementOrder")) title = "结算单";
        return title;
    }

    private String[] buildFieldCode(String type) {
        String[] str = null;
        if (type.equals("channelOrder")) {
            str = new String[]{"id", "merchantId", "merchantName", "transType", "transStatus", "payMethod", "channelType", "orgCode", "orgSubCode", "payCurrency",
                    "payAmount", "merchantOrderId", "orderId", "paymentOrderId", "channelOrderId", "channelCompleteTime", "channelReturnId", "chargeCostType",
                    "chargeCostFlag", "channelReferenceNo", "channelAccessCode", "oriPayCurrency", "oriPayAmount", "gmtCreateTime", "gmtUpdateTime"
            };
        } else if (type.equals("settlementSummary")) {
            str = new String[]{"id", "merchantId", "summaryDate", "summaryCurrency", "summaryAmount", "beginDate", "endDate", "autoWithdrawFlag", "withdrawStatus",
                    "withdrawId", "withdrawRequestTime", "withdrawCompleteTime", "gmtCreateTime", "gmtUpdateTime", "merchantName", "summaryOrderId"
            };
        } else if (type.equals("mertFee")) {
            str = new String[]{"id", "merchantId", "merchantName", "orderId", "transType", "amountType", "transCurrency", "transAmount", "feeCurrency", "feeAmount",
                    "feeSettleCurrency", "feeSettleAmount", "orderDate", "settleDate", "settleRate", "feeSettleMethod", "settleStatus", "accountingStatus"
            };
        } else if (type.equals("channelCost")) {
            str = new String[]{"orgCode", "orgSubCode", "channelOrderId", "payMethod", "transType", "transStatus", "payCurrency", "payAmount", "percentFeeAmount",
                    "fixedFeeCurrency", "fixedFeeAmount", "feeCurrency", "feeAmount", "chargeCostMethod", "costSettleCurrency", "costSettleDate", "channelCompleteTime"
            };
        } else if (type.equals("liquidationOrder")) {
            str = new String[]{"merchantId", "merchantName", "productCode", "transType", "transStatus", "transCurrency", "transAmount", "settleCurrency", "settleDate",
                    "subAccountData", "status", "feeType", "feeId", "feeFlag", "merchantOrderId", "orderId", "payOrderId", "oriMerchantOrderId", "oriOrderId",
                    "oriPayOrderId", "payCompleteTime", "gmtCreateTime", "gmtUpdateTime", "payMethod", "transMethod", "payOrgCode", "cardOrg", "cardCountry", "cardType",
                    "transModel", "cardNo", "region"
            };
        } else if (type.equals("settlementDetail")) {
            str = new String[]{"liquidationOrderId", "merchantOrderId", "orderId", "clearingDetailId", "merchantId", "merchantName", "transType", "amountType", "transCurrency",
                    "transAmount", "settleType", "settleCurrency", "settleAmount", "settleRate", "settleDate", "settlementId", "settleBatchId", "feeSettleMethod", "settleStatus",
                    "settleTime", "gmtCreateTime", "gmtUpdateTime", "payMethod", "accountingStatus", "accountingId", "accountingTime"
            };
        } else if (type.equals("settlementOrder")) {
            str = new String[]{"merchantId", "merchantName", "settleType", "settleCurrency", "settleAmount", "feeSettleMethod", "settlementDate", "settlementSummaryId",
                    "summaryStatus", "accountingStatus", "accountingId", "accountingTime", "gmtCreateTime", "gmtUpdateTime", "transType", "amountType", "transCurrency",
                    "transAmount", "settlementOrderId"
            };
        }
        return str;
    }

    private String[] buildFieldDesc(String type) {
        String[] str = null;
        if (type.equals("channelOrder")) {
            str = new String[]{"主键", "商户号", "商户名称", "交易类型", "交易状态", "支付方式", "渠道类型", "渠道号", "子渠道号", "交易币种",
                    "交易金额", "商户订单号", "收单订单号", "支付订单号", "渠道流水号", "交易完成时间", "渠道返回流水号", "成本收取类型",
                    "成本收取标志", "渠道参考号", "渠道授权号", "原渠道交易币种", "原渠道交易金额", "创建时间", "更新时间"
            };
        } else if (type.equals("settlementSummary")) {
            str = new String[]{"主键", "商户号", "结算汇总日期", "结算汇总币种", "结算汇总金额", "开始日期", "结束日期", "是否自动出款", "出款状态",
                    "出款流水号", "出款请求时间", "出款完成时间", "创建时间", "修改时间", "商户名称", "结算汇总单号"
            };
        } else if (type.equals("mertFee")) {
            str = new String[]{"主键", "商户号", "商户名称", "支付订单号", "订单类型", "金额类型", "订单币种", "订单金额", "费用币种", "费用金额",
                    "费用结算币种", "费用结算金额", "订单日期", "结算日期", "结算汇率", "收费方式", "结算状态", "记账状态"
            };
        } else if (type.equals("channelCost")) {
            str = new String[]{"渠道号", "子渠道号", "渠道流水号", "支付方式", "交易类型", "交易状态", "交易币种", "交易金额", "比例费金额",
                    "固定费币种", "固定费金额", "处理费币种", "处理费金额", "费用收取方式", "费用结算币种", "费用结算日期", "交易完成时间"
            };
        } else if (type.equals("liquidationOrder")) {
            str = new String[]{"商户号", "商户名称", "产品编码", "交易类型", "交易状态", "交易币种", "交易金额", "结算币种", "结算日期",
                    "分账信息", "清算状态", "费用收取类型", "费用规则ID", "费用收取标志", "商户订单号", "收单订单号", "支付订单号", "原商户订单号", "原收单订单号",
                    "原支付订单号", "支付完成时间", "创建时间", "更新时间", "支付方式", "交易方式", "支付渠道", "卡组织", "信用卡支付", "卡类型",
                    "交易模型", "卡号", "地区"
            };
        } else if (type.equals("settlementDetail")) {
            str = new String[]{"清算主订单", "商户订单号", "收单订单号", "清分明细ID", "商户ID", "商户名称", "交易类型", "金额类型", "交易币种",
                    "交易金额", "结算类型", "结算币种", "结算金额", "结算汇率", "结算日期", "结算单号", "结算批次号", "费用结算方式", "结算状态",
                    "结算时间", "创建时间", "更新时间", "支付方式", "记账状态", "记账流水号", "记账时间"
            };
        } else if (type.equals("settlementOrder")) {
            str = new String[]{"商户ID", "商户名称", "结算类型", "结算币种", "结算金额", "费用结算方式", "结算单日期", "结算汇总单编号",
                    "汇总状态", "记账状态", "记账流水号", "记账时间", "创建时间", "更新时间", "交易类型", "金额类型", "交易币种",
                    "交易金额", "结算单编号"
            };
        }
        return str;
    }
}
