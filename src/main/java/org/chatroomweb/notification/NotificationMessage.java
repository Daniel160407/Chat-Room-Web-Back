package org.chatroomweb.notification;

import lombok.Getter;

public class NotificationMessage {
    @Getter
    private static final String onUserEnter = "User joined the chat";
    private static final String onUserLeave = "User left the chat";

    public static String onUserLeaveNotification() {
        return "User" + onUserLeave;
    }
}
