package org.example.chatroom.exception;

import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.chatroom.dto.Message;
import org.example.chatroom.dto.User;
import org.example.chatroom.service.GroupChatService;
import org.example.chatroom.service.MessageService;
import org.example.chatroom.service.UserSessionService;
import org.example.chatroom.service.WebSocketService;
import org.example.chatroom.utils.FastJsonUtil;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class DefaultWebSocketHandler implements WebSocketHandler {

    @Resource
    private WebSocketService webSocket;
    @Resource
    private UserSessionService userSessionService;
    @Resource
    private MessageService messageService;
    @Resource
    private GroupChatService group;

    /**
     * 建立连接
     *
     * @param session Session
     */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        try {
            String sessionId = session.getId();
            String userId = session.getAttributes().get("userid").toString();
            userSessionService.addUserSession(userId, sessionId);
            log.info(userId + ":" + sessionId);
            webSocket.handleOpen(session);
        } catch (Exception e) {
            log.error("连接异常", e);
        }
    }

    /**
     * 接收消息
     *
     * @param session Session
     * @param message 消息
     */
    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) {
        try {
            if (message instanceof TextMessage textMessage) {
                handleTextMessage(session, textMessage);
            } else if (message instanceof BinaryMessage binaryMessage) {
                log.info("接收到二进制消息");
            }
        } catch (IOException e) {
            log.info("消息发送异常");
        }
    }

    /**
     * 发生错误
     *
     * @param session   Session
     * @param exception 异常
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        webSocket.handleError(session, exception);
    }

    /**
     * 关闭连接
     *
     * @param session     Session
     * @param closeStatus 关闭状态
     */
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus) {
        String sessionId = session.getId();
        userSessionService.removeUserSessionBySessionId(sessionId);
        webSocket.handleClose(session);
    }

    /**
     * 是否支持发送部分消息
     *
     * @return false
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 处理文本消息
     *
     * @param session Session
     * @param message 消息
     */
    private void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        try {
            //设置时间格式
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //解析消息
            String text = message.getPayload();
            //将消息转为Message对象
            Message msg = FastJsonUtil.parseObject(text, Message.class);
            log.info("接收到消息：" + msg.toString());
            if (msg.getGroupid() == null) { //私聊
                //接收到的时间格式"2024/7/2 14:37:16",转为"2024-07-02 14:37:16"
                String date = msg.getDate().replace("/", "-");
                //保存消息
                messageService.addFriendChatHistory(msg.getSender(), msg.getReceiver(), msg.getContent(), formatter.parse(date), msg.getType());
                //发送消息
                webSocket.sendMessage(userSessionService.getSessionId(msg.getReceiver().toString()), message.getPayload());
            } else {    //群聊
                //接收到的时间格式"2024/7/2 14:37:16",转为"2024-07-02 14:37:16"
                String date = msg.getDate().replace("/", "-");
                //保存消息
                messageService.addGroupChatHistory(msg.getSender(), msg.getGroupid(), msg.getContent(), formatter.parse(date), msg.getType(), msg.getUsername(), msg.getHeadimg());
                User[] users = group.getGroupMembers(msg.getGroupid()); //获取群聊成员
                //获取群聊成员的session
                String[] sessionIds = new String[users.length];
                for (int i = 0; i < users.length; i++) {
                    log.info(users[i].getId().toString());
                    if (Objects.equals(users[i].getId(), msg.getSender())) {
                        sessionIds[i] = null;
                        continue;
                    }
                    sessionIds[i] = userSessionService.getSessionId(users[i].getId().toString());
                }
                log.info(Arrays.toString(sessionIds));
                //发送消息
                webSocket.broadCast(sessionIds, message.getPayload());
            }
        } catch (ParseException e) {
            log.error("时间解析异常", e);
        }
    }

    /**
     * 处理二进制消息
     *
     * @param session Session
     * @param message 消息
     */
    private void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        log.info("接收到二进制消息");
    }
}

