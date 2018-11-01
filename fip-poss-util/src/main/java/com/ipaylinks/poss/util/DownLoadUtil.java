package com.ipaylinks.poss.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * 下载文件名称编码
 * 适用于 ie, edge, firefox, chrome 等主流浏览器
 *
 * @author Alan
 * @date 2018/8/23 20:57
 */
public class DownLoadUtil {

    private static final String IE_MSIE = "MSIE";
    private static final String IE_TRIDENT = "Trident";
    private static final String FIRE_FOX = "Firefox";
    private static final String EDGE_ONE = "Edge";
    private static final String EDGE_TWO = "like gecko";

    /**
     * 文件名称编码
     *
     * @param title     文件名
     * @param userAgent 浏览器请求头标识
     * @return 返回
     * @throws UnsupportedEncodingException 异常
     */
    public static String encodeFileName(String title, String userAgent) throws UnsupportedEncodingException {
        String fileName;
        if (userAgent.contains(IE_MSIE) || userAgent.contains(IE_TRIDENT) || userAgent.contains(EDGE_ONE) || userAgent.contains(EDGE_TWO)) {
            fileName = java.net.URLEncoder.encode(title, "UTF-8");
        } else if (userAgent.contains(FIRE_FOX)) {
            //火狐浏览器
            fileName = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(title.getBytes(StandardCharsets.UTF_8)))) + "?=";
        } else {
            fileName = new String(title.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        return fileName;
    }

}
