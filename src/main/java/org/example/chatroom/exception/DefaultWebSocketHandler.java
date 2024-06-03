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
import org.springframework.beans.factory.annotation.Autowired;
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
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) throws IOException, ParseException {
        try {
            if (message instanceof TextMessage textMessage) {
                //设置时间格式
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //解析消息
                String text = textMessage.getPayload();
                //将消息转为Message对象
                Message msg = FastJsonUtil.parseObject(text, Message.class);
                log.info("接收到消息：" + msg.toString());
                if (msg.getGroupid() == null) { //私聊
                    //保存消息
                    messageService.addFriendChatHistory(msg.getSender(), msg.getReceiver(), msg.getContent(), formatter.parse(msg.getDate()));
                    //发送消息
                    webSocket.sendMessage(userSessionService.getSessionId(msg.getReceiver().toString()), msg.getContent());
                } else {    //群聊
                    //保存消息
                    messageService.addGroupChatHistory(msg.getSender(), msg.getGroupid(), msg.getContent(), formatter.parse(msg.getDate()));
                    User[] users = group.getGroupMembers(msg.getGroupid()); //获取群聊成员
                    //获取群聊成员的session
                    String[] sessionIds = new String[users.length];
                    for (int i = 0; i < users.length; i++) {
                        if (Objects.equals(users[i].getId(), msg.getSender())) {
                            sessionIds[i] = null;
                            continue;
                        }
                        sessionIds[i] = userSessionService.getSessionId(users[i].getId().toString());
                    }
                    log.info(Arrays.toString(sessionIds));
                    //发送消息
                    webSocket.broadCast(sessionIds, msg.getContent());
                }
            }
        } catch (ParseException e) {
            log.info("时间解析错误");
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
}

