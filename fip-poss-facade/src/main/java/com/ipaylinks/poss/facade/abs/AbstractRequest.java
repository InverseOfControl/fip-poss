package com.ipaylinks.poss.facade.abs;

import java.io.Serializable;

public abstract class AbstractRequest implements Serializable {

	/**  */
	private static final long serialVersionUID = 2608972743092939107L;

	/** 请求号 */
	private String reqId;
	/** 系统编号 */
	private String sysId;
	/** 版本号 */
	private String version;
	/** 扩展域 */
	private String trace;

	/**
	 * Getter method for property <tt>version</tt>.
	 * 
	 * @return property value of version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Setter method for property <tt>version</tt>.
	 * 
	 * @param version
	 *            value to be assigned to property version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Getter method for property <tt>trace</tt>.
	 * 
	 * @return property value of trace
	 */
	public String getTrace() {
		return trace;
	}

	/**
	 * Setter method for property <tt>trace</tt>.
	 * 
	 * @param trace
	 *            value to be assigned to property trace
	 */
	public void setTrace(String trace) {
		this.trace = trace;
	}

	/**
	 * Getter method for property <tt>sysId</tt>.
	 * 
	 * @return property value of sysId
	 */
	public String getSysId() {
		return sysId;
	}

	/**
	 * Setter method for property <tt>sysId</tt>.
	 * 
	 * @param sysId
	 *            value to be assigned to property sysId
	 */
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	@Override
	public String toString() {
		return "AbstractRequest [reqId=" + reqId + ", sysId=" + sysId + ", version=" + version + ", trace=" + trace
				+ "]";
	}

}
