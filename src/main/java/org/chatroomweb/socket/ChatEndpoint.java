package org.chatroomweb.socket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.chatroomweb.dao.MySQLController;
import org.chatroomweb.message.ChangeUsernameMessage;
import org.chatroomweb.message.Message;
import org.chatroomweb.message.PublicMessage;
import org.chatroomweb.notification.Notification;
import org.chatroomweb.notification.NotificationMessage;
import org.chatroomweb.notification.NotificationType;
import org.chatroomweb.request.AddNewUserRequest;
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

        Message message = new Notification(NotificationType.USERENTERED, NotificationMessage.getOnUserEnter(), sessions.size());
        sendMessageToAll(session, message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("WebSocket disconnected: " + session.getId());
        sessions.remove(session);
        session.close();

        Message message = new Notification(NotificationType.USERLEFT, NotificationMessage.onUserLeaveNotification(), sessions.size());
        sendMessageToAll(session, message);
    }

    @OnMessage
    public void onMessage(Message message, Session session) {
        System.out.println("Received message from " + session.getId() + ": " + message);
        if (message instanceof ChangeUsernameMessage changeUsernameMessage) {
            mySQLController.addUser(new AddNewUserRequest(session.getId(), changeUsernameMessage.getUsername()));
            System.err.println("Came");
        } else {
            sendMessageToAll(session, message);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + throwable.getMessage());
    }
}
