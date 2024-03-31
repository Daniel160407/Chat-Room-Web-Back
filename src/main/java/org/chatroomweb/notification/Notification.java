package org.chatroomweb.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private NotificationType notificationType;
    private String notification;
    private int membersAmount;
}
