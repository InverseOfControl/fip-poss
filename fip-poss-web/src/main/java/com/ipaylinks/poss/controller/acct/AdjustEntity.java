package com.ipaylinks.poss.controller.acct;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AdjustEntity {
	private String batchId;
	private BigDecimal accountNo;
	private String accountNoStr;
	private String dcDirection;
	private BigDecimal amount;
	private String amountStr;
	private String orgCode;
	private String tradeType;
    private String financeType;
    private String sysTraceNo;
    private String adjustSummary;
    public AdjustEntity () {}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getDcDirection() {
		return dcDirection;
	}

	public void setDcDirection(String dcDirection) {
		this.dcDirection = dcDirection;
	}

	

	public BigDecimal getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(BigDecimal accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountNoStr() {
		return accountNoStr;
	}

	public void setAccountNoStr(String accountNoStr) {
		this.accountNoStr = accountNoStr;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAmountStr() {
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getFinanceType() {
		return financeType;
	}

	public void setFinanceType(String financeType) {
		this.financeType = financeType;
	}

	public String getSysTraceNo() {
		return sysTraceNo;
	}

	public void setSysTraceNo(String sysTraceNo) {
		this.sysTraceNo = sysTraceNo;
	}
    
	public String getAdjustSummary() {
		return adjustSummary;
	}

	public void setAdjustSummary(String adjustSummary) {
		this.adjustSummary = adjustSummary;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
}
