/*
 * *
 *  * 启赟金融信息服务（上海）有限公司
 *  * Copyright (c) 2015-2017 iPayLinks.All Rights Reserved.
 *
 */

package com.ipaylinks.poss.util;

/**
 * poss 异常包装
 */
public class PossException extends Exception {

	private static final long serialVersionUID = -1L;

	private String responseCode;

	private String responseMsg;

	public PossException(String responseCode, String responseMsg) {
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}
}

