package com.ipaylinks.poss.interceptor;

import com.ipaylinks.poss.common.Constants;
import com.ipaylinks.poss.common.ImUser;
import com.ipaylinks.poss.dal.domain.crm.Member;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 
 * 用于WebSocket记录用户信息
 * @author zhaoyang
 * @date 2018年8月14日
 */
public class WebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession();
            if (session != null) {
                Member member = (Member) session.getAttribute(Constants.SESSION_MEMBER_KEY);
                attributes.put(Constants.WEB_SOCKET_USERNAME, new ImUser(member.getId(), member.getRealName()));
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
