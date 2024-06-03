package org.example.chatroom.config;

import lombok.NonNull;
import org.example.chatroom.exception.DefaultWebSocketHandler;
import org.example.chatroom.interceptors.WebSocketInterceptor;
import org.example.chatroom.service.WebSocketService;
import org.example.chatroom.service.impl.WebSocketServiceimpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {


    @Bean
    public DefaultWebSocketHandler defaultWebSocketHandler() {
        return new DefaultWebSocketHandler();
    }

    @Bean
    public WebSocketService webSocket() {
        return new WebSocketServiceimpl();
    }

    @Bean
    public WebSocketInterceptor webSocketInterceptor() {
        return new WebSocketInterceptor();
    }

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(defaultWebSocketHandler(), "ws/message")
                .addInterceptors(webSocketInterceptor())
                .setAllowedOrigins("*");
    }
}

