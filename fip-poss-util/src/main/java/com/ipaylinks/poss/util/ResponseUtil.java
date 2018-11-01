package com.ipaylinks.poss.util;

import com.ipaylinks.common.enums.BaseRespStatusEnum;
import com.ipaylinks.common.rpc.BaseResponse;
/**
 * 处理Response的工具类
 * @author Jerry Chen
 * @date 2018年8月28日
 */
public class ResponseUtil {

	private ResponseUtil(){}
	/**
	 * 是否是成功响应
	 * @param e
	 * @return
	 */
	public static <E extends BaseResponse> boolean isSuccess(E e){
		if(e == null){
			return false;
		}
		return BaseRespStatusEnum.SUCCESS.getCode().equals(e.getResponseStatus());
	}
	/**
	 * 是否是失败响应
	 * @param e
	 * @return
	 */
	public static <E extends BaseResponse> boolean isFail(E e){
		return !isSuccess(e);
	}
	/**
	 * 描述response
	 * @param e
	 * @return
	 */
	public static <E extends BaseResponse> String getDesc(E e){
		StringBuilder sb = new StringBuilder();
		if( e == null ){
			sb.append("response==null");
		}else{
			sb.append("{responseStatus=").append(e.getResponseStatus());
			sb.append(",responseCode=").append(e.getResponseCode());
			sb.append(", responseMsg=").append(e.getResponseMsg()).append("}");
		}
		return sb.toString();
	}
}
