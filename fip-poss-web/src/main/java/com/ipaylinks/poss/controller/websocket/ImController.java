package com.ipaylinks.poss.controller.websocket;

import com.ipaylinks.poss.common.AjaxResult;
import com.ipaylinks.poss.common.SocketMessage;
import com.ipaylinks.poss.dal.domain.crm.Member;
import com.ipaylinks.poss.handler.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * IM控制器
 * @author zhaoyang
 * @date 2018年8月16日
 */
@Controller
public class ImController {

    @Autowired
    WebSocketHandler webSocketHandler;

    /**
     * 发送信息给指定用户
     *
     * @param uid
     * @param content
     * @param member
     */
    @RequestMapping("/im/send")
    @ResponseBody
    public void send(Long uid, String content, @SessionAttribute("s_member") Member member) {
        Map<String, Object> message = new HashMap<>();
        message.put("fromUid", member.getId());
        message.put("realName", member.getRealName());
        message.put("message", content);
        webSocketHandler.sendMessageToUser(uid, new SocketMessage("message", message).toTextMessage());
    }

    /**
     * 获取在线用户列表
     *
     * @return
     */
    @RequestMapping("/im/user/list")
    @ResponseBody
    public AjaxResult userList() {
        return new AjaxResult().setData(webSocketHandler.getUsers());
    }
}
