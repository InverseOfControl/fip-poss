package com.ipaylinks.poss.facade.abs;

import java.io.Serializable;

public abstract class AbstractResponse implements Serializable {

	/**  */
	private static final long serialVersionUID = 4210915420775706316L;

	/** 返回状态 */
	private String status;
	/** 返回码 */
	private String respCode;
	/** 返回信息 */
	private String respMessage;

	/**
	 * Getter method for property <tt>status</tt>.
	 * 
	 * @return property value of status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Setter method for property <tt>status</tt>.
	 * 
	 * @param status
	 *            value to be assigned to property status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Getter method for property <tt>respCode</tt>.
	 * 
	 * @return property value of respCode
	 */
	public String getRespCode() {
		return respCode;
	}

	/**
	 * Setter method for property <tt>respCode</tt>.
	 * 
	 * @param respCode
	 *            value to be assigned to property respCode
	 */
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	/**
	 * Getter method for property <tt>respMsg</tt>.
	 * 
	 * @return property value of respMsg
	 */
	public String getRespMessage() {
		return respMessage;
	}

	/**
	 * Setter method for property <tt>respMsg</tt>.
	 * 
	 * @param respMsg
	 *            value to be assigned to property respMsg
	 */
	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}

	@Override
	public String toString() {
		return "AbstractResponse [status=" + status + ", respCode=" + respCode + ", respMessage=" + respMessage + "]";
	}

}
