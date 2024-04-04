package org.chatroomweb.socket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.chatroomweb.dao.MySQLController;
import org.chatroomweb.message.Message;
import org.chatroomweb.notification.Notification;
import org.chatroomweb.notification.NotificationMessage;
import org.chatroomweb.notification.NotificationType;
import org.chatroomweb.message.PrivateMessage;
import org.chatroomweb.request.GetUserBySidRequest;
import org.chatroomweb.util.MessageDecoder;
import org.chatroomweb.util.MessageEncoder;
import org.chatroomweb.util.NotificationEncoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(
        value = "/chatEndpoint",
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class}
)
public class ChatEndpoint {
    private static final Map<String, Session> sessions = new HashMap<>();
    private final MySQLController mySQLController = new MySQLController();

    private void sendMessage(Message message) throws EncodeException, IOException {
        if (message instanceof PrivateMessage privateMessage) {
            sessions.get(privateMessage.getReceiver()).getBasicRemote().sendObject(message);
        } else {
            sessions.forEach((key, value) -> {
                try {
                    value.getBasicRemote().sendObject(message); //TODO: This requires some tests
                } catch (IOException | EncodeException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @OnOpen
    public void onOpen(Session session) throws EncodeException, IOException {
        System.out.println("Opened");
        sessions.put(session.getId(), session);

        Message message = new Notification(NotificationType.USERENTERED, NotificationMessage.getOnUserEnter(), sessions.size());
        sendMessage(message);
    }

    @OnClose
    public void onClose(Session session) throws EncodeException, IOException {
        sessions.remove(session.getId());

        sendMessage(new Notification(NotificationType.USERLEFT, NotificationMessage.onUserLeaveNotification(
                mySQLController.getUserName(new GetUserBySidRequest(session.getId()))), sessions.size())
        );
    }

    @OnMessage
    public void onMessage(Message message) throws EncodeException, IOException {
        sendMessage(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("MyError: " + throwable.getMessage());
    }
}
