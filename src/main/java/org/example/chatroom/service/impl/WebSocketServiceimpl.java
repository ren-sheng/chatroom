package org.example.chatroom.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.chatroom.service.WebSocketService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Service
public class WebSocketServiceimpl implements WebSocketService {
    /**
     * 在线连接数（线程安全）
     */
    private static final AtomicInteger connectionCount = new AtomicInteger(0);

    /**
     * 线程安全的无序集合（存储会话）
     */
    private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void handleOpen(WebSocketSession session) {
        sessions.add(session);
        int count = connectionCount.incrementAndGet();
        log.info("a new connection opened，current online count：{}", count);
    }

    @Override
    public void handleClose(WebSocketSession session) {
        sessions.remove(session);
        int count = connectionCount.decrementAndGet();
        log.info("a new connection closed，current online count：{}", count);
    }

    @Override
    public void handleMessage(WebSocketSession session, String message) throws IOException {
        // 只处理前端传来的文本消息，并且直接丢弃了客户端传来的消息
        log.info("received a message：{}", message);
        log.info(getSessions().toString());
        sendMessage(session, "received：" + message);    //测试用，作用是收到消息就直接返回
    }

    @Override
    public void sendMessage(WebSocketSession session, String message) throws IOException {
        this.sendMessage(session, new TextMessage(message));
    }

    @Override
    public void sendMessage(String sessionId, TextMessage message) throws IOException {
//        Optional<WebSocketSession> userSession = sessions.stream().filter(session -> {
//            if (!session.isOpen()) {
//                return false;
//            }
//            return sessionId.equals(session.getId());
//        }).findFirst();
//        if (userSession.isPresent()) {
//            userSession.get().sendMessage(message);
//        }
        WebSocketSession session = getSessionById(sessionId);
        //log.info("session id: " + sessionId);
        if (session != null) {
            session.sendMessage(message);
        }
        else {
            log.info("离线消息"+ message.getPayload());
        }
    }

    @Override
    public void sendMessage(String userId, String message) throws IOException {
        this.sendMessage(userId, new TextMessage(message));
    }

    @Override
    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException {
        session.sendMessage(message);
    }

    @Override
    public void broadCast(String[] sessionIds, String message) throws IOException {
        for (String sessionId : sessionIds) {
            if (sessionId != null) sendMessage(sessionId, message);
        }
    }

    @Override
    public void broadCast(String[] sessionIds, TextMessage message) throws IOException {
        for (String sessionId : sessionIds) {
            if (sessionId != null) sendMessage(sessionId, message);
        }
    }

    @Override
    public void handleError(WebSocketSession session, Throwable error) {
        log.error("websocket error：{}，session id：{}", error.getMessage(), session.getId());
        log.error("", error);
    }

    @Override
    public Set<WebSocketSession> getSessions() {
        return sessions;
    }

    @Override
    public int getConnectionCount() {
        return connectionCount.get();
    }

    @Override
    public WebSocketSession getSessionById(String sessionId) {
        try {
            return sessions.stream()
                    .filter(session -> sessionId.equals(session.getId()))
                    .findFirst()
                    .orElse(null);
        } catch (NullPointerException e) {
            return null;
        }
    }

    //中断连接
    @Override
    public void closeSession(String sessionId) {
        WebSocketSession session = getSessionById(sessionId);
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
                log.error("关闭会话异常", e);
            }
        }
    }
}
