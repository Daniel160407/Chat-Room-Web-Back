package org.chatroomweb.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PrivateMessage implements Message{
    private String sid;
    private String sender;
    private String receiver;
    private String message;

    @Override
    public String toString() {
        return "PrivateMessage{" +
                "sid='" + sid + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}