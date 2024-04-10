package org.chatroomweb.socket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.chatroomweb.dao.MySQLController;
import org.chatroomweb.message.Message;
import org.chatroomweb.notification.Notification;
import org.chatroomweb.notification.NotificationMessage;
import org.chatroomweb.notification.NotificationType;
import org.chatroomweb.message.PrivateMessage;
import org.chatroomweb.request.AddNewUserRequest;
import org.chatroomweb.request.GetUserBySidRequest;
import org.chatroomweb.util.MessageDecoder;
import org.chatroomweb.util.MessageEncoder;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(
        value = "/chatEndpoint",
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class}
)
public class ChatEndpoint {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private final MySQLController mySQLController = new MySQLController();

    private void sendMessageToAll(Session session, Message message) {
        synchronized (sessions) {
            for (Session value : sessions) {
                if (!value.equals(session) && value.isOpen()) {
                    sendMessage(value, message);
                }
            }
        }
    }


    private void sendMessage(Session session, Message message) {
        try {
            session.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket connected: " + session.getId());
        sessions.add(session);

        System.out.println(session.isOpen());
        Message message = new Notification(NotificationType.USERENTERED, NotificationMessage.getOnUserEnter(), sessions.size());
        sendMessageToAll(session, message);

    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("WebSocket disconnected: " + session.getId());
        sessions.remove(session);
        System.out.println("Size: " + sessions.size());
    }

    @OnMessage
    public void onMessage(Message message, Session session) {
        System.out.println("Received message from " + session.getId() + ": " + message);
        sendMessageToAll(session, message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + throwable.getMessage());
    }
}
