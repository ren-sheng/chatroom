package org.example.chatroom.interceptors;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.chatroom.utils.JWTUtil;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
            // 模拟用户（通常利用JWT令牌解析用户信息）
            String user = servletServerHttpRequest.getServletRequest().getParameter("token");
            log.info("用户令牌：" + user);
            // TODO 判断用户是否存在
            try {
                Map<String, Object> M = JWTUtil.ParseTokens(user);
                log.info("账号:" + M.toString() + "申请连接");
                attributes.put("userid", M.get("id").toString());
                return true;
            } catch (Exception e) {
                response.setStatusCode(HttpStatusCode.valueOf(401));
                log.info("建立连接令牌验证失败");
                return false;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, Exception exception) {
        log.info("握手成功");
    }

}

