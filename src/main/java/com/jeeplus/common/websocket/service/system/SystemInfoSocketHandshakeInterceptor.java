package com.jeeplus.common.websocket.service.system;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.jeeplus.common.websocket.utils.Constants;
import com.jeeplus.modules.sys.utils.UserUtils;

public class SystemInfoSocketHandshakeInterceptor implements HandshakeInterceptor {

    private static Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object
                > attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                //使用loginName区分WebSocketHandler，以便定向发送消息
                String loginName = UserUtils.getUser().getLoginName();//获取当前用户登录名
                attributes.put(Constants.WEBSOCKET_LOGINNAME,loginName);
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}