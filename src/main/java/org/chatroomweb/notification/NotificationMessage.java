package org.chatroomweb.notification;

import lombok.Getter;

public class NotificationMessage {
    @Getter
    private static final String onUserEnter = "User joined the chat";
    private static final String onUserLeave = " left the chat";

    public static String onUserLeaveNotification(String userName) {
        return userName + onUserLeave;
    }
}
