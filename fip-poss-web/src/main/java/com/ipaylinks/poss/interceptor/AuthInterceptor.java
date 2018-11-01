package com.ipaylinks.poss.interceptor;

import com.ipaylinks.poss.controller.AppController;
import com.ipaylinks.poss.controller.AttachmentController;
import com.ipaylinks.poss.dal.domain.crm.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * 权限URL拦截器
 *
 * @author gson
 */
public class AuthInterceptor implements HandlerInterceptor {

    Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        Member member = (Member) session.getAttribute("s_member");
        boolean isSuper = (boolean) session.getAttribute("isSuper");

        HandlerMethod hm = (HandlerMethod) handler;
        if (hm.getBean() instanceof AppController
                || hm.getBean() instanceof AttachmentController
                || isSuper) {
            //App里面的就不做权限控制了，相当于公共的，省的写一个就要配置一个例外
            //超管也直接跳过权限
            return true;
        }

        // 获取用户所有可以访问的请求
        Set<String> urls = (Set<String>) session.getAttribute("urls");
        String uri = request.getRequestURI();

        if (!urls.contains(uri)) {
            log.info("用户[{}]无访问[{}]的权限", member.getUserName(),uri);
            if (isAjax(request)) {
                response.setStatus(401);
            } else {
                response.setContentType("text/html;charset=utf-8");
                // 非ajax请求，没有权限，转到提示页面
                request.getRequestDispatcher("/reject").forward(request, response);
            }
            // 没有权限
            return false;
        }

        return true;
    }

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {

    }

}
