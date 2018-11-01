package com.ipaylinks.poss.listener;

import com.ipaylinks.poss.common.Constants;
import com.ipaylinks.poss.dal.domain.crm.Member;
import com.ipaylinks.poss.handler.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * session监听器
 * @author zhaoyang
 * @date 2018年8月14日
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    @Autowired
    WebSocketHandler webSocketHandler;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println(httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        Member member = (Member) session.getAttribute(Constants.SESSION_MEMBER_KEY);
        if (member != null) {
            webSocketHandler.offLine(member.getId());
        }
    }
}
