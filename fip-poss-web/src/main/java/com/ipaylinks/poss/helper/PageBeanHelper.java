package com.ipaylinks.poss.helper;

import javax.servlet.http.HttpServletRequest;

import com.ipaylinks.common.page.PageBean;

/**
 * 获取pageBean
 * @author Jerry Chen
 * @date 2018年8月28日
 */
public class PageBeanHelper {

	private PageBeanHelper () {}
	/**
	 * 从HttpServletRequest中获取page,rows，并构建PageBean
	 * @param request
	 * @return
	 */
	public static PageBean build(HttpServletRequest request){
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		PageBean pageBean = new PageBean();
		pageBean.setPageNumber(Integer.valueOf(page));
		pageBean.setPageSize(Integer.valueOf(rows));
		return pageBean;
	}
}
