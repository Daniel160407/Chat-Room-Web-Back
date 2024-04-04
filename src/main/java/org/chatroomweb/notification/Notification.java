package org.chatroomweb.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chatroomweb.message.Message;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Message {
    private NotificationType notificationType;
    private String notification;
    private int membersAmount;
}
