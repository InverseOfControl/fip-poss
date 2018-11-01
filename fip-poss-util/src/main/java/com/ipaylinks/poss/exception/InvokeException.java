package com.ipaylinks.poss.exception;

/**
 * 调用接口异常
 * @author Jerry Chen
 * @date 2018年8月28日
 */
public class InvokeException extends Exception{
	private static final long serialVersionUID = 5773808771142048334L;
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public InvokeException() {
        super();
    }
    public InvokeException(String code,String message) {
    	super(message);
    	this.code = code;
    }
    public InvokeException(String message) {
        super(message);
    }

    public InvokeException(String message, Throwable t) {
        super(message, t);
    }
}
